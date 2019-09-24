package observatory.visualization.space

trait Distance[X, U] {
  def distance(x1: X, x2: X): U
}
