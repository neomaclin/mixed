package org.demo.core.algos.dp;

public class Fib {

  public static long fib(Long n) {
    if (n == 0) return 0;
    long a, b, last;
    a = last = 0L;
    b = 1L;
    long count = 2L;
    while (count <= n) {
      last = a + b;
      a = b;
      b = last;
      count++;
    }
    return last;
  }
}
