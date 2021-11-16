package it.unibo.pc

import it.unibo.pc.utils.given
import it.unibo.pc.utils.*
import it.unibo.pc.PetriNet.{ checkSafetyProperty, toSystem }
import scala.collection.MultiSet
import scala.language.implicitConversions

object ReadersWriterPetriNet {

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
