package org.demo.core

import clojure.java.api.Clojure


class CallingClojure  {

    fun apply(): Long = Clojure.`var`("clojure.core","+").invoke(1,1,3) as Long

}

