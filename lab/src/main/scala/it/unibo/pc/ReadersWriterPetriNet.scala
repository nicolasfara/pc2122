package it.unibo.pc

import it.unibo.pc.utils.given
import it.unibo.pc.utils.*
import it.unibo.pc.PetriNet.{ checkSafetyProperty, toSystem }
import scala.collection.MultiSet

object ReadersWriterPetriNet extends App {

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

  println(readersWritersSystem().paths(MultiSet(P1, P5), depth = 5).toList.mkString("\n"))

  println(
    checkSafetyProperty(
      readersWritersSystem(),
      MultiSet(P1, P5),
      10,
      l =>
        l.forall(m => {
          if (m.contains(P7)) {
            m.size == 1
          } else {
            true
          }
        }),
    ),
  )
}
