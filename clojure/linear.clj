(defn vecop [op]
  (fn [& args] (apply mapv op args)))

(def v+ (vecop +))
(def v- (vecop -))
(def v* (vecop *))

(defn scalar [a b]
  (reduce + (v* a b)))

(defn vectcoord [a b c d]
  (- (* a b) (* c d)))

(defn vect [[xb yb zb] [xa ya za]]
  [(vectcoord za yb ya zb) (vectcoord xa zb za xb) (vectcoord ya xb xa yb)])

(defn v*s [a s]
  (mapv (partial * s) a))

(def m+ (vecop v+))
(def m- (vecop v-))
(def m* (vecop v*))

(defn m*s [a s]
  (mapv #(v*s %1 s) a))

(defn m*v [a b]
  (mapv (partial scalar b) a))

(defn transpose [a]
  (apply mapv vector a))

(defn m*m [a b]
  (let [transposed (transpose b)]
    (mapv
      #(mapv (partial scalar %1) transposed)
      a)))

(defn shapeless [op a b]
  (if (vector? a) (mapv #(shapeless op %1 %2) a b) (op a b)))


(defn s+ [a b] (shapeless + a b))
(defn s- [a b] (shapeless - a b))
(defn s* [a b] (shapeless * a b))