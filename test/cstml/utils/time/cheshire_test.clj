(ns cstml.utils.time.cheshire-test
  (:require
   [cheshire.core :as json]
   [clojure.test :refer [deftest is testing]]
   [cstml.utils.time :as t]))

(deftest can-encode
  (testing "instant"
    (is (json/encode (t/instant))))
  (testing "local-date"
    (is (json/encode (t/local-date)))))
