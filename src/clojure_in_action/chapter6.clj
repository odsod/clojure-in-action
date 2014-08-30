(ns clojure-in-action.chapter6)

(def all-users (ref {}))

(deref all-users)

(println @all-users)

(dosync (ref-set all-users {}))

(defn new-user [id login monthly-budget]
  {:id id
   :login login
   :monthly-budget monthly-budget
   :total-expenses 0})

(defn add-new-user [login budget-amount]
  (dosync
    (let [current-number (count @all-users)
          user (new-user (inc current-number) login budget-amount)]
      (alter all-users assoc login user))))

(defn add-new-user-commutative [login budget-amount]
  (dosync
    (let [current-number (count @all-users)
          user (new-user (inc current-number) login budget-amount)]
      (commute all-users assoc login user))))

(def total-cpu-time (agent 0))

(deref total-cpu-time)

(print @total-cpu-time)

(send total-cpu-time + 700)

(deref total-cpu-time)

(send-off total-cpu-time + 700)

(def bad-agent (agent 10))

(send bad-agent / 0)

(clear-agent-errors bad-agent)

(def total-rows (atom 0))

(deref total-rows)

(reset! total-rows 0)

(swap! total-rows + 100)

(def ^:dynamic *hbase-master* "localhost")
(println "Hbase-baster is:" *hbase-master*)

(def ^:dynamic *rabbitmq-host*)
(println "RabbitMQ host is:" *rabbitmq-host*)

(def ^:dynamic *mysql-host*)

(defn db-query [db]
  (binding [*mysql-host* db]
    (count *mysql-host*)))

(def mysql-hosts ["test-mysql" "dev-mysql" "staging-mysql"])

(pmap db-query mysql-hosts)

(def adi (atom 0))

(defn on-change [the-key the-ref old-value new-value]
  (println "Change from" old-value "to" new-value))

(add-watch adi :adi-changer on-change)

(deref adi)

(swap! adi inc)

(remove-watch adi :adi-changer)

(defn long-calculation [num1 num2]
  (Thread/sleep 1000)
  (* num1 num2))

(defn long-run []
  (let [x (long-calculation 11 13)
        y (long-calculation 13 17)
        z (long-calculation 17 19)]
    (* x y z)))

(time (long-run))

(defn fast-run []
  (let [x (future (long-calculation 11 13))
        y (future (long-calculation 13 17))
        z (future (long-calculation 17 19))]
    (* @x @y @z)))

(time (fast-run))

(def p (promise))
