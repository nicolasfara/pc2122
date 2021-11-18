package it.unibo.pc

import it.unibo.pc.utils.*

import scala.collection.MultiSet
import scala.reflect.*

object PetriNet {

  /**
   * A Petri-net is composed by a triple: `pre-condition`, `effects`, `inhibition`.
   */
  type PetriNet[P <: Enum] = Set[(MultiSet[P], MultiSet[P], MultiSet[P])]

  /**
   * Create a new Petri-net from a list of triple representing the model.
   * @param transitions
   *   list of tranitions.
   * @return
   *   the set of the transitions of the model.
   */
  def apply[P <: Enum](transitions: (MultiSet[P], MultiSet[P], MultiSet[P])*): PetriNet[P] = transitions.toSet

  def toPartialFunction[P <: Enum](pn: PetriNet[P]): PartialFunction[MultiSet[P], Set[MultiSet[P]]] = { case m =>
    for {
      (cond, eff, inh) <- pn if (m disjoined inh)
      out <- m extract cond
    } yield out union eff
  }

  // factory of A System
  def toSystem[P <: Enum](pn: PetriNet[P]): System[MultiSet[P]] = System.ofFunction(toPartialFunction(pn))

  def checkSafetyProperty[F <: Enum](
      model: System[MultiSet[F]],
      initialState: MultiSet[F],
      depth: Int,
      condition: List[MultiSet[F]] => Boolean,
  ): Boolean = {
    model.paths(initialState, depth).forall(condition)
//    (1 to depth)
//      .map(model.paths(initialState, _))
//      .takeWhile(_.exists(p => p.tail.contains(p.head)))
//      .flatten
//      .reverse
//      .foldLeft(LazyList[List[MultiSet[F]]]())((sl, nl) => if (sl.exists(_.containsSlice(nl))) sl else sl :+ nl)
//      .forall(condition)
  }
}

extension [S <: Enum](self: MultiSet[S])
  def ~~>(otherSet: MultiSet[S]): (MultiSet[S], MultiSet[S], MultiSet[S]) = (self, otherSet, MultiSet[S]())

extension [S <: Enum](self: (MultiSet[S], MultiSet[S], MultiSet[S]))
  def ^^^(otherSet: MultiSet[S]): (MultiSet[S], MultiSet[S], MultiSet[S]) = (self._1, self._2, otherSet)
