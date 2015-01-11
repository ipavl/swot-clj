(ns swot-clj.core
  (require [clojure.java.io :refer [resource]]
           [clojure.string :refer (split-lines lower-case)]))

(defn- read-file
  "Reads each line in a file and returns a vector containing each line."
  [file]
  (split-lines (slurp file)))

(def ^:private blacklist (read-file (resource "blacklist.txt")))
(def ^:private whitelist (read-file (resource "whitelist.txt")))

(defn- in?
  "Determines if an element is in a given sequence."
  [seq elm]
  (some #(= elm %) seq))

(defn- get-domain
  "Strips a string, such as a URL or e-mail address, down to its domain."
  [text]
  (lower-case (re-find #"[^@\/:]+[:\d]*$" text)))

(defn- get-domain-hierarchy
  "Returns the domain hierarchy delimited by slashes (akin to a file path) for a given domain."
  [domain]
  (apply str (interpose "/" (reverse (.split domain "\\.")))))

(defn- get-domain-file
  "Returns the resource path representing a given domain."
  [domain]
  (resource (str "domains/" (get-domain-hierarchy domain) ".txt")))

(defn is-academic?
  "Determines if the passed string is an email or domain belonging to an academic institution."
  [text]
  (let [domain (lower-case (get-domain text))]
    (if (nil? (in? blacklist domain))
      (if (nil? (in? whitelist domain))
        (not (nil? (get-domain-file domain)))
        false)
      false)))

(defn get-institution-name
  "Determines the name of an institution based on the passed email or domain."
  [text]
  (let [domain (lower-case (get-domain text))]
    (if (is-academic? domain)
      (read-file (get-domain-file domain))
      nil)))
