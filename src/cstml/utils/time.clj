(ns cstml.utils.time
  (:require [java-time.api :as jt]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen])
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

(set! *data-readers*  (assoc *data-readers* 'time/instant #'parse-instant))

(s/def ::instant (s/with-gen jt/instant?
                   (fn [] (gen/return
                           (-> (jt/instant)
                               (jt/+ (jt/days (rand-int 99999)))
                               (jt/- (jt/days (rand-int 99999))))))))

(defn local-date []
  (LocalDate/now))

(defn parse-local-date [local-date-str]
  (LocalDate/parse local-date-str))

(defmethod print-method LocalDate
  [dt writer]
  (.write writer (str "#time/local-date \"" (.toString dt) "\"")))

(defmethod print-dup LocalDate
  [dt writer]
  (.write writer (str "#time/local-date \"" (.toString dt) "\"")))

(set! *data-readers* (assoc *data-readers* 'time/local-date parse-local-date))

(s/def ::local-date (s/with-gen jt/local-date?
                      (fn [] (gen/return
                             (-> (jt/local-date)
                                 (jt/+ (jt/days (rand-int 99999)))
                                 (jt/- (jt/days (rand-int 99999))))))))

(defn instant->local-date [instant]
  (-> instant
      (.atZone (java.time.ZoneId/systemDefault))
      (.toLocalDate)))

(defn local-date->instant
  ([local-date]
   (local-date->instant local-date (java.time.ZoneId/systemDefault)))
  ([local-date zone-id]
   (-> local-date
       (.atStartOfDay zone-id)
       (.toInstant))))

(comment
  (local-date->instant (instant->local-date (instant)))
  ;; Example for parsing
  (java.time.LocalDate/parse "2024/12/03" (java.time.format.DateTimeFormatter/ofPattern "yyyy/MM/dd")))
