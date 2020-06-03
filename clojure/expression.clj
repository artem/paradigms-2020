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

(defn constructor [ctor prototype]
  (fn [& args] (apply ctor {:prototype prototype} args)))

(def toString (method :toString))
(def toStringSuffix (method :toStringSuffix))
(def evaluate (method :evaluate))
(def diff (method :diff))
(def args (field :arguments))
(def firstArg #(first (args %1)))
(def secondArg #(second (args %1)))

(defn Constant [val]
  {:toString (fn [_] (format "%.1f" val))
   :toStringSuffix (fn [_] (format "%.1f" val))                   ;TODO
   :evaluate (constantly val)
   :diff     (fn [_ _] (Constant 0))
   })

(def ZERO (Constant 0))
(def ONE (Constant 1))
(def TWO (Constant 2))

(defn Variable [name]
  {:toString (constantly name)
   :toStringSuffix (constantly name)                        ;TODO
   :evaluate (fn [_ m]
               (get m name 0))
   :diff     (fn [_ var]
               (if (= name var) ONE ZERO))
   })

(defn _Operation [f s]
  (fn [this & arguments]
    (assoc this
      :func f
      :op s
      :arguments arguments)))

(def OperationProto
  {:toString (fn [this] (format "(%s %s)" (proto-get this :op)
                                (clojure.string/join " " (mapv (partial toString) (args this)))))
   :toStringSuffix (fn [this] (format "(%s %s)"
                                (clojure.string/join " " (mapv (partial toStringSuffix) (args this)))
                                      (proto-get this :op)))
   :evaluate (fn [this m]
               (apply (proto-get this :func)
                      (mapv #(evaluate %1 m) (args this))))
   })

(defn applyDiffer [f]
  (assoc OperationProto
    :diff f))

(def Add (constructor (_Operation + "+")
                      (applyDiffer (fn [this var]
                                     (Add (diff (firstArg this) var)
                                          (diff (secondArg this) var))))))

(def Subtract (constructor (_Operation - "-")
                           (applyDiffer (fn [this var]
                                          (Subtract (diff (firstArg this) var)
                                                    (diff (secondArg this) var))))))

(def Multiply (constructor (_Operation * "*")
                           (applyDiffer (fn [this var]
                                          (Add (Multiply (diff (firstArg this) var) (secondArg this))
                                               (Multiply (firstArg this) (diff (secondArg this) var)))))))

(def Divide (constructor (_Operation divideCljWorkaround "/")
                         (applyDiffer (fn [this var]
                                        (Divide (Subtract
                                                  (Multiply (diff (firstArg this) var) (secondArg this))
                                                  (Multiply (firstArg this) (diff (secondArg this) var)))
                                                (Square (secondArg this)))))))

(def Negate (constructor (_Operation - "negate")
                         (applyDiffer (fn [this var]
                                        (Negate (diff (firstArg this) var))))))

(def Square (constructor (_Operation #(* %1 %1) "square")
                         (applyDiffer (fn [this var]
                                        (Multiply TWO (Multiply (diff (firstArg this) var) (firstArg this)))))))

(def Sqrt (constructor (_Operation #(Math/sqrt (Math/abs %1)) "sqrt")
                       (applyDiffer (fn [this var]
                                      (Multiply (diff (firstArg this) var)
                                                (Divide (Sqrt (Square (firstArg this)))
                                                        (Multiply TWO (Multiply this (firstArg this)))))))))

(def Pow (constructor (_Operation #(Math/pow %1 %2) "**")
                      (applyDiffer #(ZERO))))

(def Log (constructor (_Operation #(/ (Math/log (Math/abs %2)) (Math/log (Math/abs %1))) "//")
                      (applyDiffer #(ZERO))))

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

(def *digit (+char "0123456789"))
(def *number (+map read-string (+str (+seqf #(flatten %&)
                                            (+opt (+char "-"))
                                            (+plus *digit)
                                            (+opt (+char "."))
                                            (+opt (+plus *digit))))))
(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))

; ---------------------------------------------------
(defn *opName [name]
  (+map
    (constantly (symbol name))
    (apply +seq (mapv #(+char (str %)) name))))             ; FIXME
(def *add (*opName "+"))
(def *minus (*opName "-"))
(def *mul (*opName "*"))
(def *div (*opName "/"))
(def *negate (*opName "negate"))
(def *pow (*opName "**"))
(def *log (*opName "//"))


(def *const (+map Constant *number))
(def *var (+map (comp variables symbol str) (+char "xyz")))
(def *value)
(def *operation
  (+seqf
    (fn [args op]
      (apply op args))
    (+plus (delay *value)) (+map (partial ops) (+or *add *minus *pow *log *mul *div *negate))))
(def *term (+seqn 1 (+char "(") *ws *operation *ws (+char ")")))



(def *value (+seqn 0 *ws (+or *const *var *term) *ws))

(def parseObjectSuffix (+parser *value))
