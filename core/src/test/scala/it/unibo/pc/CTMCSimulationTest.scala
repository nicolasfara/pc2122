package it.unibo.pc

import it.unibo.pc.CTMCSimulation.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CTMCSimulationTest extends AnyWordSpec with Matchers {

  trait Fixture {

    enum State {
      case IDLE, SEND, FAIL, DONE
    }
    import State.*

    def stocChannel: CTMC[State] = CTMC.ofTransitions(
      (IDLE, 1.0, SEND),
      (SEND, 100000.0, SEND),
      (SEND, 200000.0, DONE),
      (SEND, 100000.0, FAIL),
      (FAIL, 100000.0, IDLE),
      (DONE, 1.0, DONE),
    )
    val channel = stocChannel
    val channelAnalysis = CTMCSimulation(channel)
  }

  "timeInState" in new Fixture {}
}
