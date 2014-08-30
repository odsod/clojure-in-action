(ns clojure-in-action.chapter7)

(def a-ref (ref 0))

(dosync
  (ref-set a-ref 1))

(defn sync-set-fn [r v]
  (dosync
    (ref-set r v)))

(sync-set-fn a-ref 2)

(defmacro sync-set [r v]
  (list 'dosync
    (list 'ref-set r v)))

(sync-set a-ref 1)

(defn exhibits-oddity? [x]
  (if (odd? x)
    (println "Very odd!")))

(exhibits-oddity? 5)

(defn unless-fn [test then]
  (if (not test)
    then))

(defn exhibits-oddity?-fn [x]
  (unless-fn (even? x)
    (println "Very odd, indeed.")))

(exhibits-oddity?-fn 5)
(exhibits-oddity?-fn 4)

(defn unless-thunk [test then-thunk]
  (if (not test)
    (then-thunk)))

(defn exhibits-oddity?-thunk [x]
  (unless-thunk (even? x)
    #(println "Rather odd.")))

(exhibits-oddity?-thunk 5)
(exhibits-oddity?-thunk 4)

(defmacro unless [test then]
  (list 'if (list 'not test)
    then))

(defn exhibits-oddity? [x]
  (unless (even? x)
    (println "Very odd, indeed.")))

(exhibits-oddity? 4)
(exhibits-oddity? 5)

(macroexpand
  '(unless (even? x) (println "Very odd, indeed.")))

(macroexpand
  '(if-not (even? x) (println "Macro club!")))

(defmacro unless [test then]
  `(if (not ~test)
     ~then))

(macroexpand '(defn foo [x] (println x) (println x)))

(defmacro unless [test & exprs]
  `(if (not ~test)
     (do ~@exprs)))

(defmacro def-logged-fn [fn-name args & body]
  `(defn ~fn-name ~args
     (let [now# (System/currentTimeMillis)]
       (println "[" now# "] Call to" (str (var ~fn-name)))
       ~@body)))

(def-logged-fn printname [name]
  (println "hi" name))

(printname "bob")

(defn check-credentials [username password]
  true)

(defn login-user [request]
  (let [username (:username request)
        password (:password request)]
    (if (check-credentials username password)
      (str "Hi " username ", " password " is correct")
      (str "Login failed!"))))

(def request {:username "amit" :password "hunter12"})

(login-user request)

(defmacro defwebmethod [name args & exprs]
  `(defn ~name [{:keys ~args}]
     ~@exprs))

(defwebmethod login-user [username password]
  (if (check-credentials username password)
    (str "Welcome, " username ", " password " is still correct!")
    (str "Login failed!")))

(login-user request)

(defmacro assert-true [test-expr]
  (let [[operator lhs rhs] test-expr]
    `(let [lhsv# ~lhs
           rhsv# ~rhs
           ret#  ~test-expr]
       (if-not ret#
         (throw (AssertionError.
                  (str '~lhs " is not " '~operator " " rhsv#)))))))

(assert-true (>= (* 2 4) (/ 18 2)))
