package it.unibo.pc.lab01

import it.unibo.pc.PetriNet.toSystem
import it.unibo.pc.utils.given
import it.unibo.pc.PetriNet
import it.unibo.pc.{ ~~>, ^^^ }

import scala.collection.MultiSet
import scala.language.implicitConversions

object Verifier {

  enum States {
    case P1, P2, P3, P4, P5, P6, P7
  }

  import States.*

  def readersWritersSystem() = toSystem(
    PetriNet(
      P1 ~~> P2,
      P2 ~~> P3,
      P2 ~~> P4,
      (P4, P5) ~~> P7 ^^^ P6,
      (P3, P5) ~~> (P6, P5),
      P7 ~~> (P1, P5),
      P6 ~~> P1,
    ),
  )
}
