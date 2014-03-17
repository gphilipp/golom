(ns golom.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [clojure.set :as set]))

(enable-console-print!)

(println "Hello world!")


(defn count-neighbors [cell game]
  (let [up (update-in cell [:y] dec)
        down (update-in cell [:y] inc)
        left (update-in cell [:x] dec)
        right (update-in cell [:x] inc)
        up-left (-> cell
                    (update-in [:x] dec)
                    (update-in [:y] dec))
        up-right (-> cell
                    (update-in [:x] inc)
                    (update-in [:y] dec))
        down-right (-> cell
                    (update-in [:x] inc)
                    (update-in [:y] inc))
        down-left (-> cell
                    (update-in [:x] dec)
                    (update-in [:y] inc))
        neighbouring-cells #{up up-right right down-right down down-left left up-left}]
    (count (set/intersection neighbouring-cells (into #{} game)))
  ;;  (not-any? #(some % game) [up down left right]))
  ))


(def game-isolated [{:x 6 :y 0} {:x 2 :y 4} {:x 4 :y 5}])
(def game-packed [{:x 0 :y 0} {:x -1 :y 0} {:x 1 :y 0}])


(count-neighbors {:x 3 :y 4} game-isolated)

(count-neighbors (first game-packed) game-packed)
(map #(count-neighbors % game-packed) game-packed)


(def whole-game (for [x (range -2 2) y (range -2 2)] {:x x :y y}))


(defn grow-cells [new-game dead-cells old-game]
    (if (empty? dead-cells)
      (set/union new-game old-game)
      (let [cell (first dead-cells)]
        (if (and (= 3 (count-neighbors cell old-game))
                           (not-any? #{cell} old-game))
          (recur old-game (conj new-game cell) (rest dead-cells))
          (recur old-game new-game (rest dead-cells))))))


(grow-cells game-packed [] whole-game)


(defn next-game [game]
  (let [dead-cells (set/difference (into #{} whole-game) (into #{} game))]
     (->> game
          (remove #(< (count-neighbors % game) 1) ,,,) ;; underpopulated
          (remove #(> (count-neighbors % game) 3) ,,,) ;; overpopulated
          (grow-cells game dead-cells ,,,)
          ))) ;; new cells


(next-game game-isolated)
(->> game-packed
     next-game
     next-game
     next-game
     next-game
     next-game)

(next-game(next-game game-packed))
