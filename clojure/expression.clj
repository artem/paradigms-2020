(defn constant [x] (fn [_] x))

(defn variable [x]
  (fn [m]
    (get m x 0)))

(defn operation [f]
  (fn [& args]
    (fn [values]
      (apply f (map #(%1 values) args)))))

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation #(/ %1 (double %2))))
(def negate (operation -))

(def variables {'x (variable "x")
                'y (variable "y")
                'z (variable "z")})
(def ops {'+      add
          '-      subtract
          '*      multiply
          '/      divide
          'negate negate})

(defn parseExpression [x]
  (cond
    (list? x) (apply (ops (first x)) (map parseExpression (rest x)))
    (number? x) (constant x)
    (contains? variables x) (variables x)))

(defn parseFunction [expr]
  (parseExpression (read-string expr)))
