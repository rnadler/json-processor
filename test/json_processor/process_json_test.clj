(ns json-processor.process-json-test
  (:require [clojure.test :refer :all]
            [clojure.string :as s]
            [json-processor.process-json :refer :all]))

(deftest test-get-json
  (testing "Get JSON content"
    (is (= (:baseString (get-json "./test/resources" "base")) "zero"))
    (is (= (:level1Boolean (:level1 (get-json "./test/resources" "level1.json"))) true)))
  (testing "Non-existent file"
    (is (thrown? Exception (get-json "./test/resources" "baseX")))))

(deftest test-process-json-file
  (testing "Good parse"
    (is (= (:level2Float (:level2 (:level1 (process-json-file "./test/resources" "base")))) 77.65)))
  (testing "Multiple includes"
    (let [result (process-json-file "./test/resources" "multi-includes")]
      (is (= (:level2Float (:level2 result)) 77.65))
      (is (= (:level2Integer result) 222)))))

;; (run-tests)
