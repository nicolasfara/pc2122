package it.unibo.pc.lab01

import it.unibo.pc.SPN.toCTMC
import it.unibo.pc.{ CTMCSimulation, SPN }
import it.unibo.pc.utils.given
import it.unibo.pc.utils.*

import java.util.Random
import scala.collection.MultiSet
import scala.language.implicitConversions

object Guru extends App {

  enum States {
    case P1, P2, P3, P4, P5, P6, P7
  }

  import States.*

  val readersAndWriters = SPN(
    (P1, _ => 1.0, P2, MultiSet.empty),
    (P2, _ => 2000, P3, MultiSet.empty),
    (P2, _ => 1000, P4, MultiSet.empty),
    ((P3, P5), _ => 1000, (P5, P6), MultiSet.empty),
    (P6, m => 0.1 * m(P6), P1, MultiSet.empty),
    ((P4, P5), _ => 1000, P7, P6),
    (P7, m => m(P7) * 0.2, (P5, P1), MultiSet.empty),
  )

  val simulation = CTMCSimulation(toCTMC(readersAndWriters))

  println(
    simulation.newSimulationTrace(MultiSet(P1, P5), Random()).take(20).toList.mkString("\n"),
  )
}
