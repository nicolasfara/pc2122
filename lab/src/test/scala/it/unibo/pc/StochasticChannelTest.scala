package it.unibo.pc

import it.unibo.pc.lab01.StochasticChannel
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StochasticChannelTest extends AnyWordSpec with Matchers {
  private val channel = StochasticChannel.stocChannel
  private val channelAnalysis = CTMCSimulation(channel)
  // TODO("write a good test")
}
