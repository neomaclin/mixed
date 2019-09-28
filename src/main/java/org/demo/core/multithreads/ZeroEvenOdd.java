package org.demo.core.multithreads;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class ZeroEvenOdd {
    private final Semaphore zero = new Semaphore(1);
    private final Semaphore even = new Semaphore(0);
    private final Semaphore odd = new Semaphore(0);

    private int n;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        int i = 0;
        while (i < n) {
            zero.acquire();
            printNumber.accept(0);
            if (i % 2 == 1) {
                even.release();
            } else {
                odd.release();
            }
            i += 1;
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        int i = 2;
        while (i <= n) {
            even.acquire();
            printNumber.accept(i);
            zero.release();
            i += 2;
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        int i = 1;
        while (i <= n) {
            odd.acquire();
            printNumber.accept(i);
            zero.release();
            i += 2;
        }
    }
}
