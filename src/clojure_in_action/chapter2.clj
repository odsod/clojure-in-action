(ns clojure-in-action.chapter2)

(+ 1 2)

(defn my-addition
  "Test"
  [operand1 operand2]
  (+ operand1 operand2))

(my-addition 1 2)

(println "Hello world!")

(def users {"kyle"   {:password "secretk" :number-pets 2}
            "siva"   {:password "secrets" :number-pets 4}
            "rob"    {:password "secretr" :number-pets 6}
            "george" {:password "secretg" :number-pets 8}})

(defn check-login [username password]
  (let [actual-password ((users username) :password)]
    (= actual-password password)))

(check-login "siva" "secrets")

(check-login "rob" "bleh")

(+)

(defn average-pets []
  (/ (apply + (map :number-pets (vals users))) (count users)))

(average-pets)

(defn average-pets2 []
  (let [user-data (vals users)
        number-pets (map :number-pets user-data)
        total (apply + number-pets)]
    (/ total (count users))))

(average-pets2)

(defn average-pets3 [users]
  (try
    (let [user-data (vals users)
          number-pets (map :number-pets user-data)
          total (apply + number-pets)]
      (/ total (count users)))
    (catch Exception e
      (println (.toString e))
      0)))

(average-pets3 [])

(+ 1 2 3 4 5)

(quote (+ 1 2 3 4 5))

'(+ 1 2 3 4 5)

(defn fact-loop [n]
  (loop [current n
         fact 1]
    (if (= current 1)
      fact
      (recur (dec current) (* fact current)))))

(fact-loop 5)

(defn run-report [user]
  (println "Running report for" user))

(defn dispatch-reporting-jobs [all-users]
  (doseq [user all-users]
    (run-report user)))

(dispatch-reporting-jobs ["bob" "bob2"])

(dotimes [x 5]
  (println "Hello" x))

(defn factorial [n]
  (let [numbers (range 1 (+ n 1))]
    (reduce * numbers)))

(factorial 3)

(defn chessboard-labels []
  (for [alpha "abcdefgh"
        num (range 1 9)]
    (str alpha num)))

(chessboard-labels)

(defn prime? [x]
  (let [divisors (range 2 (inc (int (Math/sqrt x))))
        remainders (map #(rem x %) divisors)]
    (not (some zero? remainders))))

(prime? 5)
(prime? 6)

(defn primes-less-than [n]
  (for [x (range 2 (inc n))
        :when (prime? x)]
    x))

(primes-less-than 50)

(defn pairs-for-primes [n]
  (let [z (range 2 (inc n))]
    (for [x z
          y z
          :when (prime? (+ x y))]
      (list x y))))

(pairs-for-primes 5)

(defn final-amount [principle rate time-periods]
  (* (Math/pow (+ 1 (/ rate 100)) time-periods) principle))

(final-amount 100 20 1)

(final-amount 100 20 2)

(defn final-amount2 [principle rate time-periods]
  (-> rate
      (/ 100)
      (+ 1)
      (Math/pow time-periods)
      (* principle)))

(final-amount2 100 20 1)
(final-amount2 100 20 2)

(defn factorial2 [n]
  (->> n
       (+ 1)
       (range 1)
       (apply *)))

(factorial2 5)

(.split "clojure-in-action" "-")

(.endsWith "program.clj" ".clj")

(/ 4 9)

(/ 4 9.0)

(first [1 2 3 4 5])

(rest [1 2 3 4 5])

(cons 1 [2 3 4 5])

(list 1 2 3 4 5)

(conj (list 1 2 3 4 5) 6)

(let [a-list (list 1 2 3 4 5)]
  (list? a-list))

(def three-numbers '(1 2 3))

(first three-numbers)

(vector 1 2 3 4 5)

(def the-vector [1 2 3 4 5])

(get the-vector 2)

(assoc the-vector 5 60)

(the-vector 4)

(def the-map {:a 1 :b 2 :c 3})

(hash-map :a 1 :b 2 :c 3)

(the-map :b)

(:b the-map)

(assoc the-map :d 4)

(dissoc the-map :a)

(def users {:kyle {
                   :date-joined "2009-01-01"
                   :summary {
                             :average {
                                       :monthly 1000
                                       :yearly 12000}}}})

(defn set-average-in [users-map user type amount]
  (let [user-map (users-map user)
        summary-map (:summary user-map)
        averages-map (:average summary-map)]
    (assoc users-map user
           (assoc user-map :summary
                  (assoc summary-map :average
                        (assoc averages-map type amount))))))

(set-average-in users :kyle :monthly 2000)

(defn average-for [user type]
  (type (:average (:summary (user users)))))

(average-for :kyle :summary)

(assoc-in users [:kyle :summary :average :monthly] 3000)

(get-in users [:kyle :summary :average :monthly])

(update-in users [:kyle :summary :average :monthly] + 500)
