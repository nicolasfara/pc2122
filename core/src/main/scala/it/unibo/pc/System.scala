package it.unibo.pc

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

  // paths of exactly length `depth`
  def paths(s: S, depth: Int): LazyList[List[S]] = depth match {
    case 0 => LazyList()
    case 1 => LazyList(List(s))
    case _ =>
      for {
        path <- paths(s, depth - 1)
        next <- next(path.last)
      } yield (path :+ next)
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
  def ofFunction[S](f: PartialFunction[S, Set[S]]): System[S] =
    new CoreSystem[S] with System[S] {
      override def next(s: S) = f.applyOrElse(s, (x: S) => Set[S]())
    }

  // Extensional specification
  def ofRelation[S](rel: Set[(S, S)]): System[S] = ofFunction { case s: S =>
    rel filter (_._1 == s) map (_._2)
  }

  // Extensional with varargs.. note binary tuples can be defined by s->b
  def ofTransitions[S](rel: (S, S)*): System[S] = ofRelation(rel.toSet)
}
