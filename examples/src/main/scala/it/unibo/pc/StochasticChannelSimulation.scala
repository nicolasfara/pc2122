package it.unibo.pc

import it.unibo.pc.utils.*
import StochasticChannel.State.*
import java.util.Random

object StochasticChannelSimulation extends App {

  val channel = StochasticChannel.stocChannel

  val channelAnalysis = CTMCSimulation(channel)

  Time.timed {
    println(
      channelAnalysis
        .newSimulationTrace(IDLE, new Random)
        .take(10)
        .toList
        .mkString("\n"),
    )
  }
}
