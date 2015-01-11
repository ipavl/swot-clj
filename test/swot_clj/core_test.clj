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
