(ns json-processor.process-json
  (:require [cheshire.core :refer :all])
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s])
  (:gen-class))


(def ^:private json-ext ".json")

(defn get-json
  "Return the JSON object from base_dir/json-file"
  [base-dir json-file]
  (let [file-name (if (s/ends-with? json-file json-ext) json-file (str json-file json-ext))
        file (io/file base-dir file-name)]
    (if (.exists file)
      (parse-string (slurp file) true)
      (throw (Exception. (format "ERROR: Failed to open file (%s)!" (.getPath file)))))))

(def get-json
  "Cache JSON from files"
  (memoize get-json))

(defmacro process-json-file
  "Process JSON object from a file's content"
  ([base-dir file-name]
   `(process-json ~base-dir (get-json ~base-dir ~file-name))))

(defmacro get-include-content
    "Get include JSON file content"
    [base-dir val]
    `(let [content# (process-json-file ~base-dir ~val)
           key# (first (keys content#))]
       (get content# key#)))

(defn is-a-map?
  "Is the value a map object"
  [v]
  (or (instance? clojure.lang.PersistentArrayMap v) (instance? clojure.lang.PersistentHashMap v)))

(defn process-json
  "Process a JSON object"
  ([base-dir obj]
   (reduce-kv
    (fn [m k v]
      (if (is-a-map? v)
        (assoc m k (process-json base-dir v))
        (if (s/starts-with? (name k) "include")
          (merge m (get-include-content base-dir v))
          (assoc m k v))))
    {} obj)))

;; (process-json-file "./test/resources" "base")
;; (process-json-file "./test/resources" "multi-includes")
