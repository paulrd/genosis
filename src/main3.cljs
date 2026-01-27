(ns main
  (:require ["genosdb" :refer [gdb]])
  (:require-macros [macros :refer [defstate]]))

(declare render!)

;; 1. Global state (a simple JS object)
(defstate app-state #js {:count 0} render!)

;; 2. Logic to update the DOM
(defn render! []
  (let [db (js/await (gdb "demo-room" #js {:rtc true}))
        app (.getElementById js/document "app")
        count (.-count app-state)]
    (set! (.-innerHTML app)
          (str "<div style='text-align: center; font-family: sans-serif;'>
                  <h1>Paul's Squint SPA</h1>
                  <p>The count is: <strong>" count "</strong></p>
                  <button id='inc'>Increment</button>
                  <button id='dec'>Decrement</button
                </div>"))
    ;; Attach events after elements are created
    (let [inc-btn (.getElementById js/document "inc")
          dec-btn (.getElementById js/document "dec")]
      (.addEventListener inc-btn "click"
                         (fn []
                           (set! (.-count app-state) (inc (.-count app-state)))
                           (render!)))
      (.addEventListener dec-btn "click"
                         (fn []
                           (set! (.-count app-state) (dec (.-count app-state)))
                           (render!))))))
