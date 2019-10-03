package org.demo.algos

object Algos {

  def maxScoreSightseeingPair(A: Array[Int]): Int = {

    def max(A: List[Int], res: Int = 0, cur:Int = 0): Int ={
      if (A.isEmpty) res else max(A.tail, Math.max(res, cur + A.head), Math.max(cur, A.head) -1 )
    }

   max(A.toList)
  }

}
