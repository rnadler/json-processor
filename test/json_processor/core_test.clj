(ns json-processor.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :as s]
            [json-processor.core :refer :all]))

(deftest test-wrong-args
  (testing "No args"
    (let [result (-main)]
      (is (s/starts-with? result "ERROR:"))
      (is (s/includes? result "0"))))
  (testing "Too many args")
    (let [result (-main "xxx" "yyy" "zzz")]
      (is (s/starts-with? result "ERROR:"))
      (is (s/includes? result "3"))))

(deftest test-good-parse
  (testing "Read base.json"
    (is (= (-main "./test/resources/base")) nil)))

;; (run-tests)
