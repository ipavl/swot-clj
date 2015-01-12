(defproject swot-clj "0.6.0"
  :description "Clojure port of Lee Reilly's 'Swot' library to validate academic institution email addresses and domains."
  :url "https://github.com/ipavl/swot-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [inet.data "0.5.5"]]
  :repl-options {:init-ns swot-clj.core}
  :plugins [[codox "0.8.10"]]
  :codox {:src-dir-uri "https://github.com/ipavl/swot-clj/blob/master/"
          :src-linenum-anchor-prefix "L"}
  :scm {:name "git"
        :url "https://github.com/ipavl/swot-clj"})
