;
; HW11 - Delay fix
;

; See https://clojure.atlassian.net/browse/CLJ-2244 for details
(defn divideCljWorkaround [& args]
  (reduce #(/ %1 (double %2)) args))

(defn proto-get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :prototype) (proto-get (obj :prototype) key)))

(defn proto-call [this key & args]
  (apply (proto-get this key) this args))

(defn method [key]
  (fn [this & args] (apply proto-call this key args)))

(defn constructor [ctor prototype]
  (fn [& args] (apply ctor {:prototype prototype} args)))

(def toString (method :toString))
(def evaluate (method :evaluate))
(def diff (method :diff))


(def Constant)
(def Variable)
(def Add)
(def Subtract)
(def Multiply)
(def Divide)
(def Negate)
(def Square)
(def Sqrt)

(def ConstantProto
  {
   :toString (fn [this] (format "%.1f" (proto-get this :data)))
   :evaluate (fn [this m] (proto-get this :data))
   :diff     (fn [_ _] (Constant 0))
   })

(defn _Constant [this a]
  (assoc this
    :data a))

(def Constant (constructor _Constant ConstantProto))

(def VariableProto
  {
   :toString (fn [this] (proto-get this :name))
   :evaluate (fn [this m]
               (get m (proto-get this :name) 0))
   :diff     (fn [this var]
               (if (= (proto-get this :name) var)
                 (Constant 1)
                 (Constant 0)))
   })

(defn _Variable [this a]
  (assoc this
    :name a))

(def Variable (constructor _Variable VariableProto))

(def BinaryProto
  {
   :toString (fn [this] (format "(%s %s %s)" (proto-get this :op)
                                (toString (proto-get this :first))
                                (toString (proto-get this :second))
                                ))
   :evaluate (fn [this m]
               ((proto-get this :func)
                (proto-call (proto-get this :first) :evaluate m)
                (proto-call (proto-get this :second) :evaluate m)))
   })

(defn _Binary [this a b f s]
  (assoc this
    :first a
    :second b
    :func f
    :op s))

(def UnaryProto
  {
   :toString (fn [this] (format "(%s %s)" (proto-get this :op)
                                (toString (proto-get this :first))
                                ))
   :evaluate (fn [this m]
               ((proto-get this :func)
                (proto-call (proto-get this :first) :evaluate m)))
   })

(defn _Unary [this a f s]
  (assoc this
    :first a
    :func f
    :op s))

(defn _Add [this x y] (_Binary this x y + "+"))
(defn _Sub [this x y] (_Binary this x y - "-"))
(defn _Mul [this x y] (_Binary this x y * "*"))
(defn _Div [this x y] (_Binary this x y divideCljWorkaround "/"))
(defn _Neg [this x] (_Unary this x - "negate"))
(defn _Square [this x] (_Unary this x #(* %1 %1) "square"))
(defn _Sqrt [this x] (_Unary this x #(Math/sqrt %1) "sqrt"))

(def AddProto
  (assoc BinaryProto
    :diff (fn [this var]
            (Add (diff (proto-get this :first) var)
                 (diff (proto-get this :second) var)))))
(def SubProto (assoc BinaryProto
                :diff (fn [this var]
                        (Subtract (diff (proto-get this :first) var)
                                  (diff (proto-get this :second) var)))))
(def MulProto (assoc BinaryProto
                :diff (fn [this var]
                        (Add
                          (Multiply (diff (proto-get this :first) var) (proto-get this :second))
                          (Multiply (proto-get this :first) (diff (proto-get this :second) var))
                          ))))
(def DivProto (assoc BinaryProto
                :diff (fn [this var]
                        (Divide
                          (Subtract
                            (Multiply (diff (proto-get this :first) var) (proto-get this :second))
                            (Multiply (proto-get this :first) (diff (proto-get this :second) var))
                            )
                          (Square (proto-get this :second))))))
(def NegProto (assoc UnaryProto
                :diff (fn [this var]
                        (Negate (diff (proto-get this :first) var)))))
(def SquareProto (assoc UnaryProto
                   :diff (fn [this var]
                           (Multiply (Constant 2)
                                     (Multiply (diff (proto-get this :first) var) (proto-get this :first))))
                   ))
(def SqrtProto (assoc UnaryProto
                 :diff (fn [this var]
                         (Divide (Negate (diff (proto-get this :first) var))
                                 (Multiply this (Constant 2))))
                 ))


(def Add (constructor _Add AddProto))
(def Subtract (constructor _Sub SubProto))
(def Multiply (constructor _Mul MulProto))
(def Divide (constructor _Div DivProto))
(def Negate (constructor _Neg NegProto))
(def Square (constructor _Square SquareProto))
(def Sqrt (constructor _Sqrt SqrtProto))








(def variables {'x (Variable "x")
                'y (Variable "y")
                'z (Variable "z")})

(def ops {'+      Add
          '-      Subtract
          '*      Multiply
          '/      Divide
          'negate Negate
          'square Square
          'sqrt   Sqrt})

(defn parseExpression [x]
  (cond
    (list? x) (apply (ops (first x)) (mapv parseExpression (rest x)))
    (number? x) (Constant x)
    (contains? variables x) (variables x)))

(defn parseObject [expr]
  (parseExpression (read-string expr)))
