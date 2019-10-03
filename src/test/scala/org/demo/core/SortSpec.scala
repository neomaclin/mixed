package org.demo.core

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.scalacheck.Properties
import org.scalacheck.Prop._
import cats.implicits._
import algos.sort._


object SortSpec extends Properties("SortingAlgos") {


  property("heap-sort") = forAll { arr: Array[Int] =>

    List.from(arr).sorted === List.from(HeapSort.on(arr, arr.length))
  }


  property("insertion-sort") = forAll { arr: Array[Int] =>

    List.from(arr).sorted === List.from(InsertionSort.on(arr, arr.length))
  }

  //@Benchmark
  property("merge-sort") = forAll { arr: Array[Int] =>

    List.from(arr).sorted === List.from(MergeSort.on(arr, arr.length))
  }

  //@Benchmark
  property("quick-sort") = forAll { arr: Array[Int] =>

    List.from(arr).sorted === List.from(QuickSort.on(arr, arr.length))
  }

}