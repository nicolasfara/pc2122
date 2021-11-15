package it.unibo.pc.utils

import scala.util.Random

object Stochastics extends App {

  given random: Random = new Random()

  // (p1,a1),...,(pn,an) --> (p1,a1),(p1+p2,a2),..,(p1+..+pn,an)
  def cumulative[A](l: List[(Double, A)]): List[(Double, A)] =
    l.tail.scanLeft(l.head) { case ((r, _), (r2, a2)) => (r + r2, a2) }

  // (p1,a1),...,(pn,an) --> ai, selected randomly and fairly
  def draw[A](cumulativeList: List[(Double, A)])(using rnd: Random = random): A = {
    val rndVal = rnd.nextDouble() * cumulativeList.last._1
    cumulativeList.collectFirst { case (r, a) if r >= rndVal => a }.get
  }

  def uniformDraw[A](actions: Set[A])(using rnd: Random = random): A = {
    actions.toList(random.nextInt(actions.size))
  }

  def drawFiltered(filter: Double => Boolean)(using rnd: Random = random): Boolean = {
    filter(rnd.nextDouble())
  }

  // (p1,a1),...,(pn,an) + 100 --> {a1 -> P1%,...,an -> Pn%}
  def statistics[A](choices: Set[(Double, A)], size: Int)(using rnd: Random = random): Map[A, Int] =
    (1 to size)
      .map(i => draw(cumulative(choices.toList))(using rnd))
      .groupBy(identity)
      .view
      .mapValues(_.size)
      .toMap
}
