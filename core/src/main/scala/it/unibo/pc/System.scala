package it.unibo.pc

import scala.annotation.tailrec

/**
 * Basically the definition of a Rewrite System
 * @tparam S
 *   type of the system.
 */
trait CoreSystem[S] {
  def next(a: S): Set[S]
}

/**
 * Basic analysis helper.
 */
trait System[S] extends CoreSystem[S] {

  def normalForm(s: S): Boolean = next(s).isEmpty

  def complete(p: List[S]): Boolean = normalForm(p.last)

  def paths(s: S, depth: Int): LazyList[List[S]] = {
    @tailrec
    def _path(s: S, depth: Int, res: LazyList[List[S]]): LazyList[List[S]] = depth match {
      case 0 => LazyList.empty
      case 1 => res
      case _ => _path(s, depth - 1, for (path <- res; next <- next(path.last)) yield (path :+ next))
    }
    _path(s, depth, LazyList(List(s)))
  }

  // complete path with length '<= depth'
  def completePathsUpToDepth(s: S, depth: Int): LazyList[List[S]] =
    LazyList.iterate(1)(_ + 1) take (depth) flatMap (paths(s, _)) filter (complete(_)) // could be optimised

  // an infinite stream: might loop, use with care!
  def completePaths(s: S): LazyList[List[S]] =
    LazyList.iterate(1)(_ + 1) flatMap (paths(s, _)) filter (complete(_))
}

/**
 * Factory of system.
 */
object System {

  // The most general case, an intensional one
  def ofFunction[S](f: PartialFunction[S, Set[S]]): System[S] = new System[S] with CoreSystem[S] {
    override def next(s: S) = f.applyOrElse(s, _ => Set[S]())
  }

  // Extensional with varargs.. note binary tuples can be defined by s->b
  def ofTransitions[S](rel: (S, S)*): System[S] = ofFunction { case s: S =>
    rel.filter(_._1 == s).map(_._2).toSet
  }
}
