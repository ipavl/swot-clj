(ns swot-clj.core)

(defn- read-file
  "Reads each line in a file and returns a vector containing each line."
  [file]
  (clojure.string/split-lines (slurp file)))

(def blacklist (read-file (.getPath (clojure.java.io/resource "blacklist.txt"))))
(def whitelist (read-file (.getPath (clojure.java.io/resource "whitelist.txt"))))

(defn- in?
  "Determines if an element is in a given sequence."
  [seq elm]
  (some #(= elm %) seq))

(defn get-domain
  "Strips a string, such as a URL or e-mail address, down to its domain."
  [text]
  (clojure.string/lower-case (re-find #"[^@\/:]+[:\d]*$" text)))

(defn get-domain-hierarchy
  "Returns the domain hierarchy delimited by slashes (akin to a file path) for a given domain."
  [domain]
  (apply str (interpose "/" (reverse (.split domain "\\.")))))

(defn- get-domain-file
  "Returns the file representing a given domain."
  [domain]
  (clojure.java.io/as-file
   (.getPath
    (clojure.java.io/resource
     (str
      "domains/"
      (get-domain-hierarchy domain)
      ".txt")))))

(defn is-academic?
  "Determines if the passed string is an email or domain belonging to an academic institution."
  [text]
  (let [domain (clojure.string/lower-case (get-domain text))]
    (if (nil? (in? blacklist domain))
      (if (nil? (in? whitelist domain))
        (.exists (get-domain-file domain)))
      false)))

(defn get-institution-name
  "Determines the name of an institution based on the passed email or domain."
  [text]
  (let [domain (clojure.string/lower-case (get-domain text))]
    (if (is-academic? domain)
      (read-file
       (get-domain-file domain))
      ["This domain does not belong to a valid institution, is blacklisted, or is not yet in the database."])))
