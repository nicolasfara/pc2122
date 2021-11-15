package it.unibo.pc

import PetriNet.*
import utils.MSet

object ReadersWriterPetriNet extends App {

  object States extends Enumeration {
    val P1, P2, P3, P4, P5, P6, P7 = Value
  }

  type Place = States.Value

  import States._

  def readersWritersSystem() = toSystem(
    PetriNet(
      MSet(P1) ~~> MSet(P2),
      MSet(P2) ~~> MSet(P3),
      MSet(P2) ~~> MSet(P4),
      MSet(P4, P5) ~~> MSet(P7) ^^^ MSet(P6),
      MSet(P3, P5) ~~> MSet(P6, P5),
      MSet(P7) ~~> MSet(P1, P5),
      MSet(P6) ~~> MSet(P1),
    ),
  )

  println(readersWritersSystem().paths(MSet(P1, P5), depth = 5).toList.mkString("\n"))

  println(
    checkSafetyProperty[Value](
      readersWritersSystem(),
      MSet(P1, P5),
      10,
      l =>
        l.forall(m => {
          if (m.contains(P7)) {
            m.size() == 1
          } else {
            true
          }
        }),
    ),
  )
}
