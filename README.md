# swot-clj
[![Build Status](https://travis-ci.org/ipavl/swot-clj.svg?branch=master)](https://travis-ci.org/ipavl/swot-clj)

A Clojure port of [Lee Reilly](https://github.com/leereilly)'s [Swot library](https://github.com/leereilly/swot)
to validate email addresses and domains of academic institutions.

## Installation

If using Leiningen, add the following your `:dependencies` in `project.clj`:

[![Clojars Project](http://clojars.org/swot-clj/latest-version.svg)](http://clojars.org/swot-clj)

## Usage

The two main functions of swot-clj are `is-academic?` (to verify an email address or domain as belonging to a legitimate
academic institution) and `get-institution-name` (to associate a domain with an institution name). They both take a
single argument, which is the domain name or an email address. Below is sample usage for a program, however both functions
work fine from the REPL as well.

    (ns your.project
      (:require [swot-clj.core :refer :all]))

    (defn -main [& args]
      (println (is-academic? "example@mit.edu"))
      ;; => true
      (println (get-institution-name "mit.edu"))
      ;; => [Massachusetts Institute of Technology]
      (println (is-academic? "australia.edu"))  ;; blacklisted domain
      ;; => false
      (println (get-institution-name "australia.edu"))
      ;; => [This domain does not belong to a valid institution, is blacklisted, or is not yet in the database.]
      (println (is-academic? "github.com"))
      ;; => false
      (println (get-institution-name "github.com")))
      ;; => [This domain does not belong to a valid institution, is blacklisted, or is not yet in the database.]

## Documentation

### `is-academic?`

Takes one string argument (a domain or email address) and returns:

* `true` if the domain is a valid academic institution
* `false` if the domain is blacklisted*

*Notes:* Currently, a null-pointer will be thrown if the institution is not known or blacklisted (e.g. google.com), but will return
`false` in a future release. There is a whitelist of known academic TLDs to allow for some leeway in the database being out of date,
however the program only detects the TLD as being valid, and not domains under that TLD so those domains will also be treated as invalid.

### `get-institution-name`

Takes one string argument (a domain or email address) and returns a vector containing:

* the institution's name(s) for valid domains
* the string `This domain does not belong to a valid institution, is blacklisted, or is not yet in the database.` for blacklisted domains*

*Notes:* Currently, a null-pointer will be thrown if the institution is not known or blacklisted (e.g. google.com), but will return
the same message as blacklisted domains in a future release for unrecognized domains.

## License

Copyright © 2015 ipavl

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
