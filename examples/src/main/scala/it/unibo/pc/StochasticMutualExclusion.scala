package it.unibo.pc

import it.unibo.pc.utils.*
import it.unibo.pc.utils.given

import java.util.Random
import scala.collection.MultiSet
import scala.language.implicitConversions

object StochasticMutualExclusion extends App {

  // Specification of my data-type for states
  enum Place {
    case N, T, C
  }

  import Place.*
  import SPN.*

  val spn = SPN(
    (N, _ => 1.0, T, MultiSet.empty),
    (T, m => m(T), C, C),
    (C, _ => 2.0, MultiSet.empty, MultiSet.empty),
  )

  val rwSimulator = CTMCSimulation(toCTMC(spn))

  println(
    rwSimulator
      .newSimulationTrace(MultiSet(N, N, N, N), new Random)
      .take(20)
      .toList
      .mkString("\n"),
  )
}
