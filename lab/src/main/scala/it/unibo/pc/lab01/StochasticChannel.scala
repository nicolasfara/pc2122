package it.unibo.pc.lab01

import it.unibo.pc.CTMC

object StochasticChannel extends App {

  enum State {
    case IDLE, SEND, DONE, FAIL
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
}
