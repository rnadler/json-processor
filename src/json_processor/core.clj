(ns json-processor.core
  (:require [cheshire.core :refer :all])
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:require [json-processor.process-json :as p])
  (:gen-class))

(def ^:private usage "usage: json-processor/-main input_file [output_file]")

(defn- error-message [message]
  (println message)
  (println usage)
  message)

(defn- args-ok
  "Check if arguments are OK
  Returns: {:status t/f [:error error-message]}"
  [args]
  (let [arg-count (count args)
        ok (<= 1 arg-count 2)
        status {:status ok}]
    (if (not ok)
      (assoc status :error (error-message (format "ERROR: Invalid number of arguments (%d)!" arg-count)))
      status)))

(defn- write-json
  "Write object to a file or stdout"
  [obj out-file]
  (let [out-string (generate-string obj {:pretty true})]
    (if out-file
      (do 
        (spit out-file out-string)
        (println "Wrote" out-file))
      (println out-string))))

(defn -main
  "Process a JSON file"
  [& args]
  (let [args-check (args-ok args)]
    (if (:status args-check)
      (try 
        (let [input-file-path (first args)
              input-file (io/file input-file-path)
              base-dir (.getParent input-file)
              file-name (.getName input-file)
              out-file (if (= 2 (count args)) (second args) nil)
              object (p/process-json-file base-dir file-name)]
          (write-json object out-file))
        (catch Exception ex
          (let [message (.getMessage ex)]
            (println message)
            message))))
      (:error args-check)))

;; (-main)
;; (-main "./test/resources/base")
;; (-main "./test/resources/base.json" "test.json")
