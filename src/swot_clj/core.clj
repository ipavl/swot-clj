(ns swot-clj.core
  (require [clojure.java.io :refer [resource]]
           [clojure.string :refer (split-lines lower-case trim)]))

(defn- read-file
  "Reads each line in a file and returns a vector containing each line."
  [file]
  (split-lines (slurp file)))

(def ^:private blacklist
  "Returns a vector of blacklisted domains, such as those that snuck into the .edu registry."
  (read-file (resource "blacklist.txt")))

(def ^:private whitelist
  "Returns a vector of whitelisted TLDs, which are known to belong only to academic institutions."
  (read-file (resource "whitelist.txt")))

(defn- in?
  "Determines if an element is in a given sequence."
  [seq elm]
  (some #(= elm %) seq))

(defn- get-domain
  "Strips a string, such as a URL or e-mail address, down to its domain."
  [text]
  (trim (lower-case (re-find #"[^@\/:]+[:\d]*$" text))))

(defn- get-domain-hierarchy
  "Returns the domain hierarchy delimited by slashes (akin to a file path) for a given domain."
  [domain]
  (apply str (interpose "/" (reverse (.split domain "\\.")))))

(defn- get-domain-file
  "Returns the resource path representing a given domain, or nil if not found."
  [domain]
  (resource (str "domains/" (get-domain-hierarchy domain) ".txt")))

(defn is-academic?
  "Determines if the passed string is an email or domain belonging to an academic institution."
  [text]
  (if (not (nil? text))
    (let [domain (get-domain text)]
      (if (nil? (in? blacklist domain))
        (if (nil? (in? whitelist domain))
          (not (nil? (get-domain-file domain)))
          false)
        false))
    false))

(defn get-institution-name
  "Returns a vector of an institution's name(s) based on the passed email or domain, or nil if
  the domain was not recognized (i.e. is-academic? returns false)."
  [text]
  (if (not (nil? text))
    (let [domain (get-domain text)]
      (if (is-academic? domain)
        (read-file (get-domain-file domain))
        nil))))
