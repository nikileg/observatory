package observatory.visualization

import org.apache.spark.rdd.RDD

package object util {

  implicit class RddNumericImplicit[T: Numeric](val rdd: RDD[T]) extends AnyVal {
    import numeric$T$0._

    private type U = (T, T, Boolean) // (result, diff, isFirst)
    private def combOp: (U, U) => U = {
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
      def abs(value: T): T = if (value >= zero) value else -value

      val seqOp: (U, T) => U = {
        case (acc, next) =>
          if (acc._3) (next, abs(number - next), false)
          else acc
      }

      rdd.aggregate((number, number, true))(seqOp, combOp)._1
    }
  }

  implicit class RddImplicit[T](val rdd: RDD[T]) extends AnyVal {
    def closestBy[B: Numeric](zeroT: T)(by: T => B): T = {
      import numeric$B$0._
      type U = (T, B, Boolean)
      val combOp: (U, U) => U = {
        case (acc1, acc2) => if (acc1._2 < acc2._2) acc1 else acc2 //min by ._2
      }
      val seqOp: (U, T) => U = {
        case (acc, next) =>
          if (acc._3) (next, abs(by(zeroT) - by(next)), false)
          else acc
      }

      def abs(value: B): B = if (value >= zero) value else -value

      rdd.aggregate((zeroT, by(zeroT), true))(seqOp, combOp)._1
    }
  }

}
