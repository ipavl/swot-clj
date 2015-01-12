(ns swot-clj.core-test
  (:require [clojure.test :refer :all]
            [swot-clj.core :refer :all]))

(deftest test-academic
  (testing "recognize academic email addresses and domains"
    (do
      (is (= true (is-academic? "lreilly@stanford.edu")))
      (is (= true (is-academic? "LREILLY@STANFORD.EDU")))
      (is (= true (is-academic? "Lreilly@Stanford.Edu")))
      (is (= true (is-academic? "lreilly@slac.stanford.edu")))
      (is (= true (is-academic? "lreilly@strath.ac.uk")))
      (is (= true (is-academic? "lreilly@soft-eng.strath.ac.uk")))
      (is (= true (is-academic? "lee@ugr.es") true))
      (is (= true (is-academic? "lee@uottawa.ca")))
      (is (= true (is-academic? "lee@mother.edu.ru")))
      (is (= true (is-academic? "lee@ucy.ac.cy")))

      (is (= false (is-academic? "lee@leerilly.net")))
      (is (= false (is-academic? "lee@gmail.com")))
      (is (= false (is-academic? "lee@stanford.edu.com")))
      (is (= false (is-academic? "lee@strath.ac.uk.com")))

      (is (= true (is-academic? "lee@ucy.ac.cy")))
      (is (= true (is-academic? "stanford.edu")))
      (is (= true (is-academic? "slac.stanford.edu")))
      (is (= true (is-academic? "www.stanford.edu")))
      (is (= true (is-academic? "http://www.stanford.edu")))
      (is (= true (is-academic? "http://www.stanford.edu:9393")))
      (is (= true (is-academic? "strath.ac.uk")))
      (is (= true (is-academic? "soft-eng.strath.ac.uk")))
      (is (= true (is-academic? "ugr.es")))
      (is (= true (is-academic? "uottawa.ca")))
      (is (= true (is-academic? "mother.edu.ru")))
      (is (= true (is-academic? "ucy.ac.cy")))

      (is (= false (is-academic? "leerilly.net")))
      (is (= false (is-academic? "gmail.com")))
      (is (= false (is-academic? "stanford.edu.com")))
      (is (= false (is-academic? "strath.ac.uk.com")))

      (is (= false (is-academic? nil)))
      (is (= false (is-academic? "")))
      (is (= false (is-academic? "the")))

      (is (= true (is-academic? " stanford.edu")))
      (is (= true (is-academic? "lee@strath.ac.uk ")))
      (is (= false (is-academic? " gmail.com ")))

      (is (= true (is-academic? "lee@stud.uni-corvinus.hu")))))

  (testing "fail blacklisted domains"
    (do
      (is (= false (is-academic? "si.edu")))
      (is (= false (is-academic? " si.edu ")))
      (is (= false (is-academic? "imposter@si.edu")))
      (is (= false (is-academic? "foo.si.edu")))
      (is (= false (is-academic? "america.edu")))))

  (testing "don't error on TLD-only domains"
    (do
      (is (= false (is-academic? ".com")))))

  (testing "don't error on invalid domains"
    (do
      (is (= false (is-academic? "foo@bar.invalid")))))

      ;; overkill
      (is (= true (is-academic? "lee@harvard.edu")))
      (is (= true (is-academic? "lee@mail.harvard.edu"))))

(deftest test-institution-name
  (testing "return name of valid institutions"
    (do
      (is (= (get-institution-name "lreilly@cs.strath.ac.uk") ["University of Strathclyde"]))
      (is (= (get-institution-name "lreilly@fadi.at") ["BRG Fadingerstraße Linz, Austria"]))))

  (testing "return nil when institution invalid"
    (do
      (is (= (get-institution-name "foo@shop.com") nil)))))
