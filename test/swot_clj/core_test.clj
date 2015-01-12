(ns swot-clj.core-test
  (:require [clojure.test :refer :all]
            [swot-clj.core :refer :all]))

(deftest test-domains
  (testing "blacklisted emails and domains"
    (do
      (is (= (is-academic? "mail@australia.edu") false))
      (is (= (is-academic? "ExAmPLE@CaLifOrNiACOLLegES.eDu") false))
      (is (= (is-academic? "australia.edu") false))
      (is (= (is-academic? "foo.CET.edu") false))))

  (testing "valid emails and domains"
    (do
      (is (= (is-academic? "mail@stanford.edu") true))
      (is (= (is-academic? "TeST@ox.AC.Uk") true))
      (is (= (is-academic? "stanford.edu") true))
      (is (= (is-academic? "snu.ac.kr") true))
      (is (= (is-academic? "sabi.eu.com") true))
      (is (= (is-academic? "   mit.edu   ") true))
      (is (= (is-academic? "univ-douala.com") true))
      (is (= (is-academic? "usenghor-francophonie.org") true))
      (is (= (is-academic? "marcusoldham.vic.edu.au") true))))

  (testing "invalid emails, domains, and values"
    (do
      (is (= (is-academic? "harvard.edu.com") false))
      (is (= (is-academic? "no-reply@gmail.com") false))
      (is (= (is-academic? ".com") false))
      (is (= (is-academic? ".invalid") false))
      (is (= (is-academic? nil) false)))))

(deftest test-institution-names
  (testing "blacklisted domains"
    (do
      (is (= (get-institution-name "si.edu") nil))
      (is (= (get-institution-name "aMERICA.edU") nil))))

  (testing "invalid emails, domains, and values"
    (do
      (is (= (get-institution-name "no-reply@google.com") nil))
      (is (= (get-institution-name "google.edu") nil))
      (is (= (get-institution-name nil) nil))))

  (testing "valid domains"
    (do
      (is (= (get-institution-name "uoguelph.ca")
             ["University of Guelph"]))
      (is (= (get-institution-name "test@uwaterloo.ca")
             ["University of St. Jerome's College" "University of Waterloo"]))
      (is (= (get-institution-name "kyoto-u.ac.jp")
             ["Kyoto University"]))
      (is (= (get-institution-name "jyu.fi")
             ["University of Jyväskylä"]))
      (is (= (get-institution-name "fadi.at")
             ["BRG Fadingerstraße Linz, Austria"])))))
