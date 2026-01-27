(ns macros)

(defmacro defstate [sym init-val render-fn]
  `(do
     (defonce ~sym
       (let [hot-data# (when js/import.meta.hot (.-data js/import.meta.hot))]
         ;; Use a string literal for the key lookup
         (if (and hot-data# (get hot-data# ~(str sym)))
           (get hot-data# ~(str sym))
           ~init-val)))

     ;; Call the render function
     (~render-fn ~sym)

     (when js/import.meta.hot
       (.dispose js/import.meta.hot
                 (fn [data#]
                   ;; aset needs the key to be a string: ~(str sym)
                   (aset data# ~(str sym) ~sym)))
       (.accept js/import.meta.hot))))
