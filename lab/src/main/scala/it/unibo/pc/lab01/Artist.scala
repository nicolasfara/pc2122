package it.unibo.pc.lab01

import it.unibo.pc.PriorityPetriNet
import it.unibo.pc.PriorityPetriNet.toSystem
import it.unibo.pc.utils.*
import it.unibo.pc.utils.given

import scala.collection.MultiSet
import scala.language.implicitConversions

object Artist extends App {

  enum State {
    case P0, P1, P2
  }

  import State.*

  def systemWithPriority() = toSystem(
    PriorityPetriNet(
      ((P0, P0), 3, P1, MultiSet.empty),
      (P0, 1, P2, MultiSet.empty),
      (P2, 0, P0, MultiSet.empty),
      (P1, 0, P0, MultiSet.empty),
    ),
  )

  println(systemWithPriority().paths(MultiSet(P0, P0, P0), 10).toList.mkString("\n"))
}
