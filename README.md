# matrix-market

A Clojure library for reading and writing sparse matrices from/to gzipped files in the MatrixMarket format.

## Usage

```clojure
(require '[healthunlocked.matrix-market :as mm])
(require '[core.matrix :as m])

(m/set-current-implementation :vectorz)

;; reading
(def m (mm/read-matrix "my-matrix.mm.gz"))

;; writing
(mm/write-matrix m "my-matrix.mm.gz")
;; or
(mm/write-matrix m "my-matrix.mm.gz" "Description" "Other comment")
```

## Matrix implementations

The library has `core.matrix` dependency but in `provided` scope, so you will need to provide your own in the projects that use the library. You'll also need to make sure you're using an implementation that supports sparse matrices. We use `vectorz` for testing.

## Edge cases

This library is brand new, and has been tested on the MatrixMarket files we use, but there will undoubtedly be edge cases that we haven't considered - even for a simple format like MatrixMarket. If you run into an edge case, pull requests and/or issues are welcome.

## License

Copyright © 2017 HealthUnlocked

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
