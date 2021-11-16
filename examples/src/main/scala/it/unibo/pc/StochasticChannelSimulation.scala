package it.unibo.pc

import it.unibo.pc.utils.*
import StochasticChannel.State.*
import it.unibo.pc.CTMCSimulation.{ averageTimeInState, timeInState }

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

  println("Average time: " + averageTimeInState(channelAnalysis, IDLE, DONE, 20, 10))
  println("Time in fail: " + timeInState(channelAnalysis, IDLE, FAIL, DONE, 20, 10))
}
