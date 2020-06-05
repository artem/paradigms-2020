(def Constant)
(def Variable)
(def Add)
(def Subtract)
(def Multiply)
(def Divide)
(def Negate)
(def Square)
(def Sqrt)

; See https://clojure.atlassian.net/browse/CLJ-2244 for details
(defn divideCljWorkaround [& args]
  (reduce #(/ %1 (double %2)) args))

(defn proto-get [obj key]
  (cond (contains? obj key) (obj key)
        (contains? obj :prototype) (proto-get (obj :prototype) key)))

(defn proto-call [this key & args]
  (apply (proto-get this key) this args))

(defn field [key]
  (fn [this] (proto-get this key)))

(defn method [key]
  (fn [this & args] (apply proto-call this key args)))

(def toString (method :toString))
(def toStringSuffix (method :toStringSuffix))
(def evaluate (method :evaluate))
(def diff (method :diff))
(def args (field :arguments))
(def firstArg #(first (args %1)))
(def secondArg #(second (args %1)))

(defn Constant [val]
  {:toString       (fn [_] (format "%.1f" val))
   :toStringSuffix (fn [_] (format "%.1f" val))
   :evaluate       (constantly val)
   :diff           (fn [_ _] (Constant 0))
   })

(def ZERO (Constant 0))
(def ONE (Constant 1))
(def TWO (Constant 2))

(defn Variable [name]
  {:toString       (constantly name)
   :toStringSuffix (constantly name)
   :evaluate       (fn [_ m]
                     (get m name 0))
   :diff           (fn [_ var]
                     (if (= name var) ONE ZERO))
   })

(defn _Operation [f s]
  (fn [this & arguments]
    (assoc this
      :func f
      :op s
      :arguments arguments)))

(def OperationProto
  {:toString       (fn [this] (format "(%s %s)" (proto-get this :op)
                                      (clojure.string/join " " (mapv (partial toString) (args this)))))
   :toStringSuffix (fn [this] (format "(%s %s)"
                                      (clojure.string/join " " (mapv (partial toStringSuffix) (args this)))
                                      (proto-get this :op)))
   :evaluate       (fn [this m]
                     (apply (proto-get this :func)
                            (mapv #(evaluate %1 m) (args this))))
   })

(defn applyDiffer [f]
  (assoc OperationProto
    :diff (fn [this var]
            (f this var
               (mapv #(diff %1 var) (args this))))))

(defn constructor [f s differ]
  (fn [& args] (apply (_Operation f s) {:prototype (applyDiffer differ)} args)))

(def Add (constructor + "+" #(Add (first %3) (second %3))))

(def Subtract (constructor - "-" #(Subtract (first %3) (second %3))))

(def Multiply (constructor * "*" #(Add (Multiply (first %3) (secondArg %1))
                                       (Multiply (firstArg %1) (second %3)))))

(def Divide (constructor divideCljWorkaround "/"
                         #(Divide (Subtract (Multiply (first %3) (secondArg %1))
                                            (Multiply (firstArg %1) (second %3)))
                                  (Square (secondArg %1)))))

(def Negate (constructor - "negate" #(Negate (first %3))))

(def Square (constructor #(* %1 %1) "square" #(Multiply TWO (Multiply (first %3) (firstArg %1)))))

(def Sqrt (constructor #(Math/sqrt (Math/abs %1)) "sqrt"
                       #(Multiply (first %3)
                                  (Divide (Sqrt (Square (firstArg %1)))
                                          (Multiply TWO (Multiply %1 (firstArg %1)))))))

(def Pow (constructor #(Math/pow %1 %2) "**" (constantly ZERO)))
(def Log (constructor #(/ (Math/log (Math/abs %2)) (Math/log (Math/abs %1))) "//" (constantly ZERO)))

(def variables {'x (Variable "x") 'y (Variable "y") 'z (Variable "z")})
(def ops {'+ Add '- Subtract '* Multiply '/ Divide 'negate Negate 'square Square 'sqrt Sqrt '** Pow (symbol "//") Log})

(defn parseExpression [x]
  (cond
    (list? x) (apply (ops (first x)) (mapv parseExpression (rest x)))
    (number? x) (Constant x)
    (contains? variables x) (variables x)))

(defn parseObject [expr]
  (parseExpression (read-string expr)))


;
; HW 12
;
(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)

(defn _empty [value] (partial -return value))
(defn _char [p]
  (fn [[c & cs]]
    (if (and c (p c)) (-return c cs))))
(defn _map [f result]
  (if (-valid? result)
    (-return (f (-value result)) (-tail result))))
(defn _combine [f a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar)
        (_map (partial f (-value ar))
              ((force b) (-tail ar)))))))

(defn _either [a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar) ar ((force b) str)))))

(defn _parser [p]
  (fn [input]
    (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))

(defn +char [chars] (_char (set chars)))
(defn +char-not [chars] (_char (comp not (set chars))))

(defn +map [f parser] (comp (partial _map f) parser))

(def +parser _parser)

(def +ignore (partial +map (constantly 'ignore)))

(defn iconj [coll value]
  (if (= value 'ignore) coll (conj coll value)))

(defn +seq [& ps]
  (reduce (partial _combine iconj) (_empty []) ps))

(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))

(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))

(defn +or [p & ps]
  (reduce _either p ps))
(defn +opt [p]
  (+or p (_empty nil)))

(defn +star [p]
  (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))

(defn +plus [p] (+seqf cons p (+star p)))
(defn +str [p] (+map (partial apply str) p))
(defn +symbol [p] (+map (partial symbol) p))

(def *digit (+char "0123456789"))
(def *number (+map read-string (+str (+seqf #(flatten %&)
                                            (+opt (+char "-"))
                                            (+plus *digit)
                                            (+opt (+char "."))
                                            (+opt (+plus *digit))))))
(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))
(def smallLetters (apply str (mapv char (range 97 123))))

; ---------------------------------------------------

(def *opName
  (+symbol (+str (+star (+char (str smallLetters "+-/*"))))))

(def *const (+map Constant *number))
(def *var (+map (comp variables symbol str) (+char "xyz")))
(def *value)
(def *operation
  (+seqf
    (fn [args op]
      (apply op args))
    (+plus (delay *value)) (+map (partial ops) *opName)))
(def *term (+seqn 1 (+char "(") *ws *operation *ws (+char ")")))



(def *value (+seqn 0 *ws (+or *const *var *term) *ws))

(def parseObjectSuffix (+parser *value))
