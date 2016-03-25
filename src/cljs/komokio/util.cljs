(ns komokio.util
  (:require [goog.dom :refer [getElementsByTagNameAndClass $$]]
            [goog.style :refer [setStyle]]
            [goog.array :refer [forEach]]))

(def code-class "code")

(defn code-face-class [face-name]
  (str code-class "-" (name face-name)))

(defn update-other-code-face-elements [face-name func]
  ;; TODO find the non deprecated $$ i should be using
  (let [code ($$ (str ".code:not(." (code-face-class face-name) ")"))]
    (forEach code #(func %))))

(defn update-code-face-elements [face-name func]
  (let [code (getElementsByTagNameAndClass nil (code-face-class face-name))]
    (forEach code #(func %))))

(defn update-code-elements [func]
  (let [code (getElementsByTagNameAndClass nil code-class)]
    (forEach code #(func %))))

;;  TODO Not using these.  Should probably delete these or move them into my own library
(comment
  (defn matching-props [m1 m2]
    "Returns a new map only including the key value pairs that have a
  key in the key sequence, ks, and that reference a value equal to v."
    (let [s1 (into #{} m1)
          s2 (into #{} m2)
          m1' (into {} (clojure.set/intersection s1 s2))]
      (when-not (empty? m1')
        m1')))

  ;; move to face related
  (defn face-matching-props [face m]
    "Returns a new face map that only contains the matching kv pairs in
  map m."
    (let [props (matching-props face m)]
      (when-not (empty? props)
        (merge
          {:face/id  (:db/id face)
           :face/name (:face/name face)}
          props))))

  (defn factory-with-computed [factory-func props props-computed]
    "Calls factory-func with the combined data of props and props-computed"
    (factory-func (om/computed props props-computed))))
