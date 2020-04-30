;
; HW10 - hard (med/avg)
;

(defn average [& args]
  (let [len (count args)
        sum (reduce + args)]
    (/ sum len)))

(defn median [& args]
  (let [len (count args)
        sorted (sort args)]
    (nth sorted (quot len 2))))

; See https://clojure.atlassian.net/browse/CLJ-2244 for details
(defn divideCljWorkaround [& args]
  (reduce #(/ %1 (double %2)) args))

(defn constant [x] (fn [_] x))

(defn variable [x]
  (fn [m]
    (get m x 0)))

(defn operation [f]
  (fn [& args]
    (fn [values]
      (apply f (mapv #(%1 values) args)))))

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation divideCljWorkaround))
(def negate (operation -))
(def avg (operation average))
(def med (operation median))

(def variables {'x (variable "x")
                'y (variable "y")
                'z (variable "z")})

(def ops {'+      add
          '-      subtract
          '*      multiply
          '/      divide
          'negate negate
          'med    med
          'avg    avg})

(defn parseExpression [x]
  (cond
    (list? x) (apply (ops (first x)) (mapv parseExpression (rest x)))
    (number? x) (constant x)
    (contains? variables x) (variables x)))

(defn parseFunction [expr]
  (parseExpression (read-string expr)))
