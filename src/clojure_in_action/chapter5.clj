(ns clojure-in-action.chapter5)

(import '(java.text SimpleDateFormat))

(def sdf (SimpleDateFormat. "yyyy-MM-dd"))

(defn date-from-date-string [date-string]
  (let [sdf (SimpleDateFormat. "yyyy-MM-dd")]
    (.parse sdf date-string)))

(Long/parseLong "12321")

(import '(java.util Calendar TimeZone))

(. System getenv "PATH")

(import '(java.util Random))

(def rnd (Random.))

(. rnd nextInt 10)

(. Calendar DECEMBER)

(. (. (Calendar/getInstance) getTimeZone) getDisplayName)

(.. (Calendar/getInstance) getTimeZone getDisplayName)

(..
  (Calendar/getInstance)
  (getTimeZone)
  (getDisplayName true TimeZone/SHORT))

(defn the-past-midnight-1 []
  (let [calendar-obj (Calendar/getInstance)]
    (.set calendar-obj Calendar/AM_PM Calendar/AM)
    (.set calendar-obj Calendar/HOUR 0)
    (.set calendar-obj Calendar/MINUTE 0)
    (.set calendar-obj Calendar/SECOND 0)
    (.set calendar-obj Calendar/MILLISECOND 0)
    (.getTime calendar-obj)))

(the-past-midnight-1)

(defn the-past-midnight-2 []
  (let [calendar-obj (Calendar/getInstance)]
    (doto calendar-obj
      (.set Calendar/AM_PM Calendar/AM)
      (.set Calendar/HOUR 0)
      (.set Calendar/MINUTE 0)
      (.set Calendar/SECOND 0)
      (.set Calendar/MILLISECOND 0))
      (.getTime calendar-obj)))

(the-past-midnight-2)

(map (fn [x] (.getBytes x)) ["amit" "rob" "kyle"])

(map #(.getBytes %) ["amit" "rob" "kyle"])

(map (memfn getBytes) ["amit" "rob" "kyle"])

(.subSequence "Clojure" 2 5)

((memfn subSequence start end) "Clojure" 2 5)

(bean (Calendar/getInstance))

(def tokens (.split "clojure.in.action" "\\."))

(alength tokens)

(aget tokens 2)

(aset tokens 2 "actionable")
