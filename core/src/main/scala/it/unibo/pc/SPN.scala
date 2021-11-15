package it.unibo.pc

import scala.collection.MultiSet

import it.unibo.pc.utils.*

object SPN {

  // pre-conditions, rate, effects, inhibition
  type SPN[P] = Set[(MultiSet[P], MultiSet[P] => Double, MultiSet[P], MultiSet[P])]

  def toPartialFunction[P](spn: SPN[P]): PartialFunction[MultiSet[P], Set[(Double, MultiSet[P])]] = { case m =>
    for {
      (cond, rate, eff, inh) <- spn
      if (m disjoined inh)
      r = rate(m)
      out <- m extract cond
    } yield (r, out union eff)
  }

  def toCTMC[P](spn: SPN[P]): CTMC[MultiSet[P]] = CTMC.ofFunction(toPartialFunction(spn))

  def apply[P](transitions: (MultiSet[P], MultiSet[P] => Double, MultiSet[P], MultiSet[P])*): SPN[P] = transitions.toSet

}
