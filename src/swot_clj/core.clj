(ns swot-clj.core)

(defn- read-list
  "Reads each line in a file and returns a vector containing each line."
  [file]
  (clojure.string/split-lines (slurp file)))

(defn- in?
  "Determines if an element is in a given sequence."
  [seq elm]
  (some #(= elm %) seq))

(defn get-domain
  "Strips a string, such as a URL or e-mail address, down to its domain."
  [text]
  (clojure.string/lower-case (re-find #"[^@\/:]+[:\d]*$" text)))

(defn is-academic?
  "Determines if the passed string is an email or domain belonging to an academic institution."
  [text]
  (if (nil?
        (in?
          (read-list
            (.getPath (clojure.java.io/resource "blacklist.txt")))
          (clojure.string/lower-case (get-domain text))))
    (if nil?
      (in?
        (read-list
          (.getPath (clojure.java.io/resource "whitelist.txt")))
        (clojure.string/lower-case (get-domain text))))
    false))
