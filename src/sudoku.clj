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

(defn block-values 
  "Get block coords, get values for each coord (reduce into a set)."
  [board [row col]]
  (let [top-left (fn [ind] (- ind (mod ind 3)))
        block-rows (range (top-left row) (+ (top-left row) 3))
        block-cols (range (top-left col) (+ (top-left col) 3))
        block-coords (for [a-row block-rows
                           a-col block-cols]
                       [a-row a-col])]
    (reduce (fn [acc coord] (conj acc (value-at board coord))) #{} block-coords)))

(defn valid-values-for 
  "Returns the set of valid possible values in coords."
  [board coord]
  (if (has-value? board coord)
    #{}
    (set/difference all-values
                    (set/union (block-values board coord)
                               (row-values board coord)
                               (col-values board coord)))))

(defn filled? 
  "Checks whether the entire board is filled or not."
  [board]
  (let [board-coords (coord-pairs (range 0 9))]
    (not (some false? (map #(has-value? board %) board-coords)))))

(defn rows
  "Returns a list of the set of numbers in each row."
  [board]
  (map #(row-values board [% 0]) (range 0 9)))

(defn valid? [values]
  (empty? (set/difference all-values values)))

(defn valid-rows? [board]
  (every? valid? (rows board)))

(defn cols 
  "Returns a list of the set of numbers in each col."
  [board]
  (map #(col-values board [0 %]) (range 0 9)))

(defn valid-cols? [board]
  (every? valid? (cols board)))

(defn blocks 
  "Returns a list of the set of numbers in each block."
  [board]
  (let [block-coords (coord-pairs [0 3 6])]
    (map #(block-values board %) block-coords)))

(defn valid-blocks? [board]
  (every? valid? (cols board)))

(defn valid-solution?
  "Returns true of the board is a filled and valid solution, false otherwise."
  [board]
  (and (valid-rows? board)
       (valid-cols? board)
       (valid-blocks? board)))

(defn set-value-at [board coord new-value]
  (assoc-in board coord new-value))

(defn find-empty-point [board]
  (let [board-coords (coord-pairs (range 0 9))
        board-map (zipmap board-coords (map #(value-at board %) board-coords))]
    (->> board-map
      (group-by val)
      (#(get % 0))
      (map key)
      (first))))

(defn solve-helper [board]
  (if (valid-solution? board)
    [board]
    (let [empty-point (find-empty-point board)
          valid-point-values (valid-values-for board empty-point)]
      (for [value valid-point-values
            solution (solve-helper (set-value-at board empty-point value))]
        solution))))

(defn solve [board]
  (first (solve-helper board)))
