package org.demo.core

class Fib {

    tailrec fun fib(n: Long, a: Long, b: Long): Long = if (n == 0L) a else fib(n - 1, b, a + b)

}