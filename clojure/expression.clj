;
; HW11 - Delay fix
;

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
(def firstArg (field :first))
(def secondArg (field :second))

(def ConstantProto
  {:toString (fn [this] (format "%.1f" (proto-get this :data)))
   :evaluate (fn [this _] (proto-get this :data))
   :diff     (fn [_ _] (Constant 0))
   })

(def Constant (constructor #(assoc %1 :data %2) ConstantProto))

(def TWO (Constant 2))

(def VariableProto
  {:toString (fn [this] (proto-get this :name))
   :evaluate (fn [this m]
               (get m (proto-get this :name) 0))
   :diff     (fn [this var]
               (if (= (proto-get this :name) var)
                 (Constant 1)
                 (Constant 0)))
   })

(def Variable (constructor #(assoc %1 :name %2) VariableProto))

(defn _Operation [this f s]
  (assoc this
    :func f
    :op s))

(def BinaryProto
  {:toString (fn [this] (format "(%s %s %s)" (proto-get this :op)
                                (toString (firstArg this))
                                (toString (secondArg this))))
   :evaluate (fn [this m]
               ((proto-get this :func)
                (evaluate (firstArg this) m)
                (evaluate (secondArg this) m)))
   })

(defn _Binary [this f s a b]
  (assoc (_Operation this f s)
    :first a
    :second b))

(def UnaryProto
  {
   :toString (fn [this] (format "(%s %s)" (proto-get this :op)
                                (toString (firstArg this))))
   :evaluate (fn [this m]
               ((proto-get this :func)
                (evaluate (firstArg this) m)))
   })

(defn _Unary [this f s a]
  (assoc (_Operation this f s) :first a))

(defn _Add [this x y] (_Binary this + "+" x y))
(defn _Sub [this x y] (_Binary this - "-" x y))
(defn _Mul [this x y] (_Binary this * "*" x y))
(defn _Div [this x y] (_Binary this divideCljWorkaround "/" x y))
(defn _Neg [this x] (_Unary this - "negate" x))
(defn _Square [this x] (_Unary this #(* %1 %1) "square" x))
(defn _Sqrt [this x] (_Unary this #(Math/sqrt (Math/abs %1)) "sqrt" x))

(def AddProto (assoc BinaryProto
                :diff (fn [this var] (Add (diff (firstArg this) var)
                                          (diff (secondArg this) var)))))
(def SubProto (assoc BinaryProto
                :diff (fn [this var] (Subtract (diff (firstArg this) var)
                                               (diff (secondArg this) var)))))
(def MulProto (assoc BinaryProto
                :diff (fn [this var] (Add (Multiply (diff (firstArg this) var) (secondArg this))
                                          (Multiply (firstArg this) (diff (secondArg this) var))))))
(def DivProto (assoc BinaryProto
                :diff (fn [this var] (Divide (Subtract
                                               (Multiply (diff (firstArg this) var) (secondArg this))
                                               (Multiply (firstArg this) (diff (secondArg this) var)))
                                             (Square (secondArg this))))))

(def NegProto (assoc UnaryProto
                :diff (fn [this var] (Negate (diff (firstArg this) var)))))

(def SquareProto (assoc UnaryProto
                   :diff (fn [this var] (Multiply TWO (Multiply (diff (firstArg this) var) (firstArg this))))))

(def SqrtProto (assoc UnaryProto
                 :diff (fn [this var] (Multiply (diff (firstArg this) var)
                                                (Divide (Sqrt (Square (firstArg this)))
                                                        (Multiply TWO (Multiply this (firstArg this))))))))

(def Add (constructor _Add AddProto))
(def Subtract (constructor _Sub SubProto))
(def Multiply (constructor _Mul MulProto))
(def Divide (constructor _Div DivProto))
(def Negate (constructor _Neg NegProto))
(def Square (constructor _Square SquareProto))
(def Sqrt (constructor _Sqrt SqrtProto))

(def variables {'x (Variable "x") 'y (Variable "y") 'z (Variable "z")})
(def ops {'+ Add '- Subtract '* Multiply '/ Divide 'negate Negate 'square Square 'sqrt Sqrt})

(defn parseExpression [x]
  (cond
    (list? x) (apply (ops (first x)) (mapv parseExpression (rest x)))
    (number? x) (Constant x)
    (contains? variables x) (variables x)))

(defn parseObject [expr]
  (parseExpression (read-string expr)))
