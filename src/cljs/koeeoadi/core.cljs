(ns koeeoadi.core
  (:require [goog.array :as garray]
            [goog.dom :refer [getElement]]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.pprint :as pprint]

            [devtools.core :as devtools]

            [koeeoadi.components.title :refer [title]]
            [koeeoadi.components.theme :refer [Theme theme]]
            [koeeoadi.components.language :refer [language]]
            [koeeoadi.components.faces :refer [Faces faces Face]]
            [koeeoadi.components.help :refer [help]]
            [koeeoadi.components.palette :refer [Palette palette]]
            [koeeoadi.components.colorpicker :refer [ColorPicker color-picker]]
            [koeeoadi.components.code :refer [Code code CodeChunk]]
            [koeeoadi.components.palette :refer [Color]]
            [koeeoadi.components.userfaces :refer [UserFaces user-faces UserFace]]
            [koeeoadi.reconciler :refer [reconciler]]))

;; TODO separate out this dev stuff
(enable-console-print!)
; this enables additional features, :custom-formatters is enabled by default
(devtools/enable-feature! :sanity-hints :dirac)
(devtools/install!)

(defui Root
  static om/IQuery
  (query [this]
    [:theme/name
     :theme/name-temp
     :theme/map
     {:theme            (om/get-query Theme)}

     {:code-chunks/list (om/get-query CodeChunk)}
     :code/map
     :code/name
     :code-background
     {:code             (om/get-query Code)}

     {:faces/list       (om/get-query Face)}
     {:faces            (om/get-query Faces)}

     {:colors/list      (om/get-query Color)}
     {:palette          (om/get-query Palette)}
     {:color-picker     (om/get-query ColorPicker)}

     {:user-faces/list  (om/get-query UserFace)}
     {:user-faces       (om/get-query UserFaces)}])

  Object
  (render [this]
    (let [{color-picker-data :color-picker
           theme-data        :theme
           code-picker-data  :code-picker
           code-data         :code
           palette-data      :palette
           faces-data        :faces
           user-faces-data   :user-faces :as props} (om/props this)]
      (dom/div nil
        (dom/div #js {:className "sidebar" :id "sidebar-left"}
          (title this)
          (theme theme-data)
          (language props))
        (code code-data)
        (dom/div #js {:className "sidebar" :id "sidebar-right"}
          (palette palette-data)
          (faces faces-data))
        (user-faces user-faces-data)
        (color-picker color-picker-data)
        (help this)))))


(om/add-root! reconciler
  Root (getElement "app"))
