(ns clojure-in-action.chapter3
  (:require [clojure.data.json :as json]
            [clojure.xml :as xml]))

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

(def MAX-CONNECTIONS 10)

(def ^:dynamic RABBITMQ-CONNECTION)

(binding [RABBITMQ-CONNECTION "new-connection"]
  (
   println "yo"
   ))

(def ^:dynamic *db-host* "localhost")

(defn expense-report [start-date end-date]
  (println *db-host*))

(expense-report 1 1)

(binding [*db-host* "production"]
  (expense-report 1 1))

(def ^:dynamic *eval-me* 10)

(defn print-the-var [label]
  (println label *eval-me*))

(print-the-var "A:")

(binding [*eval-me* 20]
  (print-the-var "B:")
  (binding [*eval-me* 30]
    (print-the-var "C:"))
  (print-the-var "D:"))

(print-the-var "E:")

(defn ^:dynamic twice [x]
  (println "original function")
  (* 2 x))

(defn call-twice [y]
  (twice y))

(defn with-log [function-to-call log-statement]
  (fn [& args]
    (println log-statement)
    (apply function-to-call args)))

(call-twice 10)

(binding [twice (with-log twice "Calling the twice function")]
  (call-twice 20))

(call-twice 30)

(def ^:dynamic *factor* 10)

(defn multiply [x]
  (* x *factor*))

(map multiply [1 2 3 4 5])

(binding [*factor* 20]
  (map multiply [1 2 3 4 5]))

(binding [*factor* 20]
  (doall (map multiply [1 2 3 4 5])))

(let [*factor* 20]
  (println *factor*)
  (doall (map multiply [1 2 3 4 5])))

(defn create-scaler [scale]
  (fn [x]
    (* x scale)))

(def percent-scaler (create-scaler 100))

(percent-scaler 0.59)

(defn import-transactions-xml-from-bank [url]
  (let [xml-document (xml/parse url)]
    (println "yo")))

(defn totals-by-day [start-date end-date]
  (let [expenses-by-day {}]
    (json/write-str expenses-by-day)))

(defn describe-salary [person]
  (let [first (:first-name person)
        last (:last-name person)
        annual (:salary person)]
    (println first last "earns" annual)))

(defn describe-salary2 [{first :first-name
                         last :last-name
                         annual :salary}]
  (println first last "earns" annual))

(defn print-amounts [[amount-1 amount-2]]
  (println "amounts are:" amount-1 "and" amount-2))

(print-amounts [10.95 31.45])

(defn print-amounts-multiple [[amount-1 amount-2 & remaining]]
  (println "Amounts are:" amount-1 "," amount-2 "and" remaining))

(print-amounts-multiple [1 2 3 4])

(defn print-all-amounts [[amount-1 amount-2 & remaining :as all]]
  (println "Amounts are:" amount-1 amount-2 "and" remaining)
  (println "All the amounts are:" all))

(print-all-amounts [1 2 3 4])

(defn print-first-category [[[category amount]]]
  (println "First category was:" category)
  (println "First amount was:" amount))

(print-first-category [[:books 10] [:coffee 5] [:stuff 6]])

(defn describe-salary-2 [{first :first-name
                          last :last-name
                          annual :salary}]
  (println first last "earns" annual))

(defn describe-salary-3 [{first :first-name
                          last :last-name
                          annual :salary
                          bonus :bonus-percentage :or {bonus 5}}]
  (println first last "earns" annual "with a" bonus "percent bonus"))

(describe-salary-3 {:first-name "Foo"
                    :last-name "McBar"
                    :salary 1000
                    :bonus-percentage 20})

(describe-salary-3 {:first-name "Fred"
                    :last-name "McBaz"
                    :salary 4000})

(defn describe-person [{first :first-name
                        last :last-name
                        annual :salary
                        bonus :bonus-percentage :or {bonus 5}
                        :as person}]
  (println "Info about" first last "is:" person)
  (println "Bonus is:" bonus "percent"))

(describe-person {:first-name "Foo"
                    :last-name "McBar"
                    :salary 1000
                    :bonus-percentage 20})

(defn greet-user [{:keys [first-name last-name]}]
  (println "Welcome," first-name last-name))

(greet-user {:first-name "Roger"
             :last-name "Mann"
             :salary 200})

(def untrusted (with-meta {:command "clean-table" :subject "users"}
                          {:safe false :io true}))

(meta untrusted)

(def still-suspect (assoc untrusted :complete? false))

(meta still-suspect)

(defn
  testing-meta
  "testing metadata for functions"
  {:safe true :console true}
  []
  (println "Hello from meta!"))

(meta testing-meta)

(meta (var testing-meta))
