(ns clojure-in-action.chapter3)

(defn count-down [n]
  (if-not (zero? n)
    (do
      (if (= 0 (rem n 100))
        (println "count-down:" n))
      (count-down (dec n)))))

(count-down 1000)

(defn count-down2 [n]
  (if-not (zero? n)
    (do
      (if (= 0 (rem n 100))
        (println "count-down:" n))
      (recur (dec n)))))

(count-down2 1000)

(declare hat)

(defn cat [n]
  (if-not (zero? n)
    (do
      (if (= 0 (rem n 100))
        (println "cat:" n))
      (hat (dec n)))))

(defn hat [n]
  (if-not (zero? n)
    (do
      (if (= 0 (rem n 100))
        (println "hat:" n))
      (cat (dec n)))))

(cat 1000)

(declare hat2)

(defn cat2 [n]
  (if-not (zero? n)
    (do
      (if (= 0 (rem n 100))
        (println "cat:" n))
      #(hat2 (dec n)))))

(defn hat2 [n]
  (if-not (zero? n)
    (do
      (if (= 0 (rem n 100))
        (println "hat:" n))
      #(cat2 (dec n)))))

(cat2 1000)

(trampoline cat2 1000)

(apply + [1 2 3 4 5])

(some (partial = "rob") ["bob" "rob"])
(some (partial = "throb") ["bob" "rob"])

((constantly 5) 3)

(def notnot (complement not))

(notnot true)

(defn slow-mul [n m]
  (Thread/sleep 100)
  (* n m))

(time (slow-mul 2 2))
(time (slow-mul 2 2))

(def fast-mul (memoize slow-mul))

(time (fast-mul 2 2))

(def users 
  [{:username "kyle"
    :balance 175.00
    :member-since "2009-04-16"}
   {:username "zak"
    :balance 12.95
    :member-since "2009-02-01"}
   {:username "rob"
    :balance 98.50
    :member-since "2009-03-30"}
   ])

(sort-by :username users)
(sort-by :balance users)

(map #(% :member-since) users)
(map :member-since users)

(map #(% :login :not-found) users)

(def expense {'name "Snow Leopard"
              'cost 29.95})

('name expense)
('vendor expense :absent)

(def names ["kyle" "zak"])

(names 1)
