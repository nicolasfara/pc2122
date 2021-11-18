package it.unibo.pc

import it.unibo.pc.PetriNet.*
import it.unibo.pc.ReadersWriterPetriNet.States.*
import it.unibo.pc.ReadersWriterPetriNet.readersWritersSystem
import it.unibo.pc.utils.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.MultiSet

class ReadersWriterPetriNetTest extends AnyWordSpec with Matchers {

  "The ReadersAndWriters model" should {
    "not have readers during writing" in {
      checkSafetyProperty(
        readersWritersSystem(),
        MultiSet(P1, P5),
        10,
        m => m.forall(s => !s.containsAll(MultiSet(P6, P7))),
      ) shouldBe true
    }
  }
}
