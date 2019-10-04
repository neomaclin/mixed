package org.demo.core.multithreads;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class FizzBuzz {

    private  Semaphore numberS = new Semaphore(1);
    private  Semaphore fizzS = new Semaphore(0);
    private  Semaphore buzzS = new Semaphore(0);
    private  Semaphore fizzBuzzS = new Semaphore(0);
    private int n;
    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        int fizz = 3;
        while(fizz<=n) {
            fizzS.acquire();
            printFizz.run();
            numberS.release();
            if ((fizz + 3) % 15 == 0) {
                fizz += 3;
            }
            fizz += 3;
        }

    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {

        int buzz = 5;
        while(buzz<=n) {
            buzzS.acquire();
            printBuzz.run();
            numberS.release();
            if ((buzz + 5) % 15 == 0) {
                buzz += 5;
            }
            buzz += 5;
        }

    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        int i = 15;
        while(i <= n) {
            fizzBuzzS.acquire();
            printFizzBuzz.run();
            numberS.release();
            i += 15;
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {

        int i = 1;
        while(i <= n) {
            numberS.acquire();
            if( i % 15 == 0) {
                fizzBuzzS.release();
            } else if(i % 5 ==0 ) {
                buzzS.release();
            } else if( i %3 == 0) {
                fizzS.release();
            } else {
                printNumber.accept(i);
                numberS.release();
            }
            i++;
        }
    }

}
