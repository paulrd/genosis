(ns main)

;; State (using a simple JS object/atom pattern)
(def state #js {:count 0})

(defn render []
  (let [app (.getElementById js/document "app")]
    (set! (.-innerHTML app)
          (str "<h1>Hello from Paul!</h1>
                <p>Count: " (.-count state) "</p>
                <button id='btn'>Increment</button>")))

  ;; Add event listener after rendering
  (let [btn (.getElementById js/document "btn")]
    (.addEventListener btn "click"
                       (fn []
                         (set! (.-count state) (inc (.-count state)))
                         (render)))))

;; Initial call
(render)
