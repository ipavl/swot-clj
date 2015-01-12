(ns swot-clj.core
  (require [clojure.java.io :refer [reader resource]]
           [clojure.string :refer [blank? lower-case split split-lines trim]]
           [inet.data.format.psl :as psl]))

(defn- read-file
  "Reads each line in a file and returns a vector containing each line."
  [file]
  (split-lines (slurp file)))

(def ^:private blacklist
  "Returns a vector of blacklisted domains, such as those that snuck into the
  .edu registry."
  (read-file (reader (resource "blacklist.txt"))))

(def ^:private whitelist
  "A list of TLDs which are known to belong only to academic institutions.

  The file should be in the Mozilla Public Suffix List (PSL) format, and can be
  queried with (psl/lookup whitelist domain)."
  (psl/load (reader (resource "academic_tlds.dat"))))

(def ^:private tld-list
  "A list of valid TLDs used to determine the base domain of a string
  (e.g. get ox.ac.uk from mail@cs.ox.ac.uk).

  The file should be in the Mozilla Public Suffix List (PSL) format. It is
  saved locally to speed up the first lookup and to save bandwidth."
  (psl/load (reader (resource "effective_tld_names.dat"))))

(defn- in?
  "Determines if an element is in a given sequence."
  [seq elm]
  (some #(= elm %) seq))

(defn- get-domain
  "Strips a string, such as a URL or e-mail address, down to its domain."
  [text]
  (apply
    str
    (first
      (split
        (trim (lower-case (re-find #"[^@\/:]+[:\d]*$" text)))
        #":"))))

(defn- get-domain-hierarchy
  "Returns the domain hierarchy delimited by slashes (akin to a file path) for
  a given domain."
  [domain]
  (apply str (interpose "/" (reverse (split domain #"\.")))))

(defn- get-domain-file
  "Returns the resource path representing a given domain, or nil if not found."
  [domain]
  (resource (str "domains/" (get-domain-hierarchy domain) ".txt")))

(defn is-academic?
  "Determines if the passed string is an email or domain belonging to an
  academic institution."
  [text]
  (if (not (blank? text))
    (let [domain (get-domain text)]
      (if (nil? (in? blacklist (str (psl/lookup tld-list domain))))
        (if (nil? (psl/lookup whitelist domain))
          (not (nil? (get-domain-file (str (psl/lookup tld-list domain)))))
          true)
        false))
    false))

(defn get-institution-name
  "Returns a vector of an institution's name(s) based on the passed email or
  domain, or nil if the domain was not recognized (i.e. is-academic? returns
  false or the institution was validated by heuristics)."
  [text]
  (if (not (blank? text))
    (let [domain (get-domain text)]
      (if (is-academic? domain)
        (try
          (read-file (get-domain-file (str (psl/lookup tld-list domain))))
          (catch Exception e))
        nil))))
