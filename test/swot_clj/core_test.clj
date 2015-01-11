(ns swot-clj.core-test
  (:require [clojure.test :refer :all]
            [swot-clj.core :refer :all]))

(deftest test-regex
  (testing "basic URL"
    (is (= "stanford.edu" (get-domain "stanford.edu"))))
  (testing "full URL"
    (is (= "www.stanford.edu" (get-domain "http://www.stanford.edu"))))
  (testing "basic email"
    (is (= "example.com" (get-domain "webmaster@example.com"))))
  (testing "email with subdomain"
    (is (= "sub.example.com" (get-domain "webmaster@sub.example.com")))))

(deftest test-domain-hierarchy
  (testing "domain hierarchy"
    (is (= "edu/stanford" (get-domain-hierarchy "stanford.edu")))))

(deftest test-domains
  (testing "blacklisted email"
    (is (= (is-academic? "mail@australia.edu") false)))
  (testing "blacklisted domain"
    (is (= (is-academic? "australia.edu") false)))
  (testing "valid email"
    (is (= (is-academic? "mail@stanford.edu") true)))
  (testing "valid domain"
    (is (= (is-academic? "stanford.edu") true)))
  (testing "valid multi-level domain"
    (is (= (is-academic? "marcusoldham.vic.edu.au") true))))

(deftest test-institution-names
  (testing "blacklisted domain"
    (is (= (get-institution-name "si.edu")
           ["This domain does not belong to a valid institution, is blacklisted, or is not yet in the database."])))
  (testing "valid domain with one institution name"
    (is (= (get-institution-name "uoguelph.ca")
           ["University of Guelph"])))
  (testing "valid domain with multiple institution names"
    (is (= (get-institution-name "uwaterloo.ca")
           ["University of St. Jerome's College" "University of Waterloo"])))
  (testing "valid domain with hyphens"
    (is (= (get-institution-name "kyoto-u.ac.jp")
           ["Kyoto University"])))
  (testing "valid domain with accented characters"
    (is (= (get-institution-name "jyu.fi")
           ["University of Jyväskylä"])))
  (testing "valid domain with non-English characters"
    (is (= (get-institution-name "fadi.at")
           ["BRG Fadingerstraße Linz, Austria"]))))
