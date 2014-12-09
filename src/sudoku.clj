(ns sudoku
  (:require [clojure.set :as set]))

(def board identity)

(def all-values #{1 2 3 4 5 6 7 8 9})

(defn value-at
  "Takes coordinates and returns the value there."
  [board coord]
  (get-in board coord))

(defn has-value?
  "Returns true if coords contain a value."
  [board coord]
  (not= 0 (value-at board coord)))

(defn row-values
  "Returns the set of values in coords' row."
  [board [row _]]
  (set (get board row)))

(defn col-values
  "Returns the set of values in coords' col."
  [board [_ col]]
  (set (map (fn [x] (get x col)) board)))

(defn coord-pairs 
  "Returns the set of coordinates permutated from input." 
  [coords]
  (for [row coords
        col coords]
    [row col]))

(def sudoku-board
  (board [[5 3 0 0 7 0 0 0 0]
          [6 0 0 1 9 5 0 0 0]
          [0 9 8 0 0 0 0 6 0]
          [8 0 0 0 6 0 0 0 3]
          [4 0 0 8 0 3 0 0 1]
          [7 0 0 0 2 0 0 0 6]
          [0 6 0 0 0 0 2 8 0]
          [0 0 0 4 1 9 0 0 5]
          [0 0 0 0 8 0 0 7 9]]))

(defn block-values
  "get top left, get coord pairs, get values"
  [board coord]
  (let [top-left (fn [ind] )]
    (top-left-coords coord)))

(defn valid-values-for [board coord]
  )

(defn filled? [board]
  )

(defn rows [board]
  )

(defn valid-rows? [board]
  )

(defn cols [board]
  )

(defn valid-cols? [board]
  )

(defn blocks [board]
  )

(defn valid-blocks? [board]
  )

(defn valid-solution? [board]
  )

(defn set-value-at [board coord new-value]
  )

(defn find-empty-point [board]
  )

(defn solve [board]
  )
