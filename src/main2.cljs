(ns my-app.main
  (:require ["genosdb" :as gdb]
            ["squint-cljs/core.js" :refer [str]]))

(defn render-graph [nodes]
  (let [display (js/document.getElementById "graph-display")]
    (set! (.-innerHTML display) "")
    (doseq [[id node] (js/Object.entries nodes)]
      (let [div (js/document.createElement "div")]
        (set! (.-innerText div) (str "Node: " id " - Val: " (.-val node)))
        (.appendChild display div)))))

(defn ^:async init! []
  (let [db (js/await (gdb/init "my-mesh"))
        btn (js/document.getElementById "add-node")]

    ;; Show the button once connected
    (set! (.-display (.-style btn)) "block")
    (set! (.-innerText (js/document.querySelector "h1")) "Sovereign Mesh Active")

    ;; Listen for P2P updates
    (.on db "update" #(render-graph %))

    ;; Add node logic
    (.addEventListener btn "click"
                       #(-> db (.get (str (js/Date.now))) (.put #js {:val (js/Math.random)})))))

(init!)

;; Tells Vite that this module can handle its own updates
(when js/import.meta.hot
  (.accept js/import.meta.hot (fn [new-module]
                                ;; Optional: logic to run after the code updates
                                (js/console.log "Hot reload complete!"))))
