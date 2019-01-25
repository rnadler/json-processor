(defproject json-processor "0.1.0-SNAPSHOT"
  :description "Process JSON files"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [cheshire "5.8.1"]
                 [nrepl "0.5.3"]]
  :main ^:skip-aot json-processor.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
