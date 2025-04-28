(ns cstml.utils.time-test
  (:require [cstml.utils.time :as t]
            [clojure.test :refer [is deftest]]
            [clojure.spec.gen.alpha :as gen]
            [clojure.spec.alpha :as s]))

(deftest instantiation
  (is (t/instant))
  (is (t/local-date)))

(deftest reader
  (is #time/instant "2025-04-28T19:47:07.288456043Z")
  (is #time/local-date "2025-04-28"))

(deftest spec
  (mapv #(is (s/assert ::t/instant %)) (gen/sample (s/gen ::t/instant)))
  (mapv #(is (s/assert ::t/local-date %)) (gen/sample (s/gen ::t/local-date))))
