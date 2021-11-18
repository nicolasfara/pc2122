package it.unibo.pc

import it.unibo.pc.utils.given
import it.unibo.pc.PriorityPetriNet.toSystem

import scala.collection.MultiSet
import scala.language.implicitConversions

object PPetriNet extends App {

  enum State {
    case P0, P1, P2
  }

  import State.*

  def systemWithPriority() = toSystem(
    PriorityPetriNet(
      ((P0, P0), 3, P1, MultiSet.empty),
      (P0, 1, P2, MultiSet.empty),
      (P2, 0, P0, MultiSet.empty),
    ),
  )

  println(systemWithPriority().paths(MultiSet(P0, P0, P0), 10).toList.mkString("\n"))
}
