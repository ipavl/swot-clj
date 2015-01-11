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

(deftest test-domains
  (testing "blacklisted email"
    (is (= (is-academic? "mail@australia.edu") false))))
