(defproject swot-clj "1.0.1"
  :description "Clojure port of the 'swot' Ruby gem to validate academic email addresses and domains using crowd-sourced data."
  :url "https://github.com/ipavl/swot-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [inet.data "0.5.7"]]
  :repl-options {:init-ns swot-clj.core}
  :plugins [[codox "0.8.13"]]
  :codox {:src-dir-uri "https://github.com/ipavl/swot-clj/blob/master/"
          :src-linenum-anchor-prefix "L"}
  :scm {:name "git"
        :url "https://github.com/ipavl/swot-clj"})
