(ns ldnclj-dojo-2014-05.core
  (:use overtone.live))

(def kerouac-haiku "Snow in my shoe
A-ban-doned
Spar-row's nest")

(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (env-lin attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

(definst sin-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (env-lin attack sustain release) 1 1 0 1 FREE)
     (sin-osc freq)
     vol))

(def syllables (clojure.string/split kerouac-haiku #"\s|-"))

(def letters (map vec syllables))

(def freqs (map #(map int %) letters))

(map-indexed (fn [index chord]
               (println chord index)
               (at (+ (now) (+ 1000 (* index 100 (count chord))))
                   (doseq [freq chord] (saw-wave (midi->hz (- freq 30)))))) freqs)

(comment ;; the same, but with sin-wave instead
  (map-indexed (fn [index chord]
                 (println chord index)
                 (at (+ (now) (+ 1000 (* index 100 (count chord))))
                     (doseq [freq chord] (sin-wave (midi->hz (- freq 30)))))) freqs))
