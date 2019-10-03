package org.demo.core.algos;

import org.demo.core.algos.sort.MergeSort;
import org.demo.core.algos.sort.QuickSort;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
public class SortingJMH {

    List<Integer> arrayList;
    int[] array;
    Random random;

    @Setup(Level.Trial)
    public void init() {
        random = new Random();
        array = new int[25];
        arrayList = new ArrayList<Integer>();
        for (int i = 0; i < 25; i++) {
            int randomNumber = random.nextInt();
            array[i] = randomNumber;
            arrayList.add(randomNumber);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 3, time = 20)
    @Measurement(iterations = 3, time = 20)
     public void quickSort() {
        QuickSort.on(array, array.length);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 3, time = 20)
    @Measurement(iterations = 3, time = 20)
    public void insertoinSort() {
        MergeSort.on(array, array.length);
    }

    public static void main(String[] args) throws Exception {

        Options opt = new OptionsBuilder()
                .include(SortingJMH.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
