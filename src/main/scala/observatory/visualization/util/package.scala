package observatory.visualization

import org.apache.spark.rdd.RDD

package object util {

  implicit class RddNumericImplicit[T: Numeric](val rdd: RDD[T]) {
    private val numeric = implicitly[Numeric[T]]
    import numeric._

    private type U = (T, T, Boolean) // (result, diff, isFirst)
    private val combOp: (U, U) => U = {
      case (acc1, acc2) => if (acc1._2 < acc2._2) acc1 else acc2 //min by ._2
    }

    def floor(number: T): T = {
      val seqOp: (U, T) => U = {
        case (acc, next) =>
          if (next > number) acc
          else if (acc._3) (next, number - next, false)
          else acc
      }

      rdd.aggregate((number, number, true))(seqOp, combOp)._1
    }

    def ceil(number: T): T = {
      val seqOp: (U, T) => U = {
        case (acc, next) =>
          if (next < number) acc
          else if (acc._3) (next, next - number, false)
          else acc
      }

      rdd.aggregate((number, number, true))(seqOp, combOp)._1
    }

    def closest(number: T): T = {
      def abs(value: T): T = if (value >= numeric.zero) value else -value
      val seqOp: (U, T) => U = {
        case (acc, next) =>
          if (acc._3) (next, abs(number - next) , false)
          else acc
      }

      rdd.aggregate((number, number, true))(seqOp, combOp)._1
    }
  }

}
