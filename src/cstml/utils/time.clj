(ns cstml.utils.time
  (:require [java-time.api :as jt]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [cstml.utils.time.cheshire])
  (:import [java.time Instant LocalDate]))

(defn instant []
  (Instant/now))

(defn parse-instant [instant-str]
  (Instant/parse instant-str))

(defmethod print-method Instant
  [inst writer]
  (.write writer (str "#time/instant \"" (.toString inst) "\"")))

(defmethod print-dup Instant
  [inst writer]
  (.write writer (str "#time/instant \"" (.toString inst) "\"")))

(alter-var-root #'*data-readers* assoc 'time/instant #'parse-instant)

(s/def ::instant
  (s/with-gen jt/instant?
    (fn [] (gen/return
            (-> (jt/instant)
                (jt/+ (jt/days (rand-int 99999)))
                (jt/- (jt/days (rand-int 99999))))))))

(defn local-date []
  (LocalDate/now))

(defn parse-local-date
  ([local-date-str]
   (LocalDate/parse local-date-str))
  ([local-date-str pattern]
   (LocalDate/parse local-date-str
                    (java.time.format.DateTimeFormatter/ofPattern pattern))))

(defmethod print-method LocalDate
  [dt writer]
  (.write writer (str "#time/local-date \"" (.toString dt) "\"")))

(defmethod print-dup LocalDate
  [dt writer]
  (.write writer (str "#time/local-date \"" (.toString dt) "\"")))

(alter-var-root #'*data-readers* assoc 'time/local-date parse-local-date)

(s/def ::local-date
  (s/with-gen jt/local-date?
    (fn [] (gen/return
            (-> (jt/local-date)
                (jt/+ (jt/days (rand-int 99999)))
                (jt/- (jt/days (rand-int 99999))))))))

(defn instant->local-date [instant]
  (-> instant
      (.atZone (java.time.ZoneId/systemDefault))
      (.toLocalDate)))

(defn local-date->instant ([local-date]
   (local-date->instant local-date (java.time.ZoneId/systemDefault)))
  ([local-date zone-id]
   (-> local-date
       (.atStartOfDay zone-id)
       (.toInstant))))
