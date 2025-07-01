(ns cstml.utils.time.cheshire
  (:require [cheshire.generate]))

(cheshire.generate/add-encoder
 java.time.Instant
 (fn [instant jsonGenerator]
   (.writeString jsonGenerator (.toString instant))))

(cheshire.generate/add-encoder
 java.time.LocalDate
 (fn [local-date jsonGenerator]
   (.writeString jsonGenerator (.toString local-date))))
