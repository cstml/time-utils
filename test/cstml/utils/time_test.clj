(ns cstml.utils.time-test
  (:require [cstml.utils.time :as t]
            [clojure.test :refer [is deftest testing]]
            [clojure.spec.alpha :as s]
            [clojure.test.check.properties :as prop]
            [clojure.test.check :as check]))

(deftest instantiation
  (is (t/instant))
  (is (t/local-date)))

(deftest reader
  (is #time/instant "2025-04-28T19:47:07.288456043Z")
  (is #time/local-date "2025-04-28"))

(deftest generator-spec-and-parser
  (let [can-parse (prop/for-all
                   [instant (s/gen ::t/instant)
                    local-date (s/gen ::t/local-date)]
                   (testing "can parse instant"
                     (is (t/parse-instant (str instant)) ))
                   (testing "can parse local-date"
                     (is (t/parse-local-date (str local-date)) )))]
    (check/quick-check 100 can-parse)))
