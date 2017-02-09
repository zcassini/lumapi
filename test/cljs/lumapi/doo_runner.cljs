(ns lumapi.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [lumapi.core-test]))

(doo-tests 'lumapi.core-test)

