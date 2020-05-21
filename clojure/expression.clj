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
(def evaluate (method :evaluate))
(def diff (method :diff))
(def args (field :arguments))
(def firstArg #(first (args %1)))
(def secondArg #(second (args %1)))

(defn Constant [val]
  {:toString (fn [_] (format "%.1f" val))
   :evaluate (constantly val)
   :diff     (fn [_ _] (Constant 0))
   })

(def ZERO (Constant 0))
(def ONE (Constant 1))
(def TWO (Constant 2))

(defn Variable [name]
  {:toString (constantly name)
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

(def variables {'x (Variable "x") 'y (Variable "y") 'z (Variable "z")})
(def ops {'+ Add '- Subtract '* Multiply '/ Divide 'negate Negate 'square Square 'sqrt Sqrt})

(defn parseExpression [x]
  (cond
    (list? x) (apply (ops (first x)) (mapv parseExpression (rest x)))
    (number? x) (Constant x)
    (contains? variables x) (variables x)))

(defn parseObject [expr]
  (parseExpression (read-string expr)))
