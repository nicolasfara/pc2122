package it.unibo.pc

import utils.MSet

object PetriNet {

  /**
   * A Petri-net is composed by a triple: `pre-condition`, `effects`, `inhibition`.
   */
  type PetriNet[P] = Set[(MSet[P], MSet[P], MSet[P])]

  /**
   * Create a new Petri-net from a list of triple representing the model.
   * @param transitions
   *   list of tranitions.
   * @return
   *   the set of the transitions of the model.
   */
  def apply[P](transitions: (MSet[P], MSet[P], MSet[P])*): PetriNet[P] = transitions.toSet

  def toPartialFunction[P](pn: PetriNet[P]): PartialFunction[MSet[P], Set[MSet[P]]] = { case m =>
    for {
      (cond, eff, inh) <- pn if (m disjoined inh)
      out <- m extract cond
    } yield out union eff
  }

  // factory of A System
  def toSystem[P](pn: PetriNet[P]): System[MSet[P]] = System.ofFunction(toPartialFunction(pn))

  def checkSafetyProperty[F](
      model: System[MSet[F]],
      initialState: MSet[F],
      depth: Int,
      condition: List[MSet[F]] => Boolean,
  ): Boolean = {
    model.paths(initialState, depth).forall(condition)
  }
}

extension [S](self: MSet[S]) def ~~>(otherSet: MSet[S]): (MSet[S], MSet[S], MSet[S]) = (self, otherSet, MSet[S]())

extension [S](self: (MSet[S], MSet[S], MSet[S]))
  def ^^^(otherSet: MSet[S]): (MSet[S], MSet[S], MSet[S]) = (self._1, self._2, otherSet)
