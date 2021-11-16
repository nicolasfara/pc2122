package it.unibo.pc

import java.util.Random

import utils.Stochastics
import it.unibo.pc.utils.Stochastics.random

trait CTMCSimulation[S] { self: CTMC[S] =>

  type Trace[A] = LazyList[(Double, A)]

  def newSimulationTrace(s0: S, rnd: Random): Trace[S] =
    LazyList.iterate((0.0, s0)) {
      case (t, s) => {
        if (transitions(s).isEmpty) (t, s)
        else {
          val next = Stochastics.cumulative(transitions(s).toList)
          val sumR = next.last._1
          val choice = Stochastics.draw(next)(using rnd)
          (t + Math.log(1 / rnd.nextDouble()) / sumR, choice)
        }
      }
    }
}

object CTMCSimulation {

  def apply[S](ctmc: CTMC[S]): CTMCSimulation[S] = new CTMC[S] with CTMCSimulation[S] {
    override def transitions(s: S) = ctmc.transitions(s)
  }

  private def sumTimeUntilState[S](
      simulation: LazyList[(Double, S)],
      untilState: S,
      nSteps: Int,
  ): Option[Double] = {
    val steps = simulation
      .take(nSteps)
      .takeWhile((_, state) => state != untilState)

    if (steps.size == nSteps) then None else Some(steps.map(_._1).sum)
  }

  def averageTimeInState[S](
      simulation: CTMCSimulation[S],
      initState: S,
      untilState: S,
      nSteps: Int,
      nRuns: Int,
  ): Option[Double] = {
    (0 until nRuns).map { _ =>
      sumTimeUntilState(simulation.newSimulationTrace(initState, Random()), untilState, nSteps)
    }.fold(Some(0.0)) { (acc, elem) =>
      for {
        elm <- elem
        ac <- acc
      } yield { elm + ac }
    } match {
      case Some(s) => Some(s / nRuns)
      case _ => None
    }
  }

  def timeInState[S](
      simulation: CTMCSimulation[S],
      initState: S,
      untilState: S,
      finalState: S,
      nSteps: Int,
      nRuns: Int,
  ): Option[Double] = {
    (0 until nRuns).map { _ =>
      val simSteps = simulation.newSimulationTrace(initState, Random())
      val allTime = sumTimeUntilState(simSteps, finalState, nSteps)
      val untilTime = simSteps.take(nSteps).takeWhile(_._2 != finalState).filter(_._2 == untilState).map(_._1).sum
      (allTime, untilTime) match {
        case (Some(all), until) => Some(all / until)
        case _ => None
      }
    }.fold(Some(0.0)) { (acc, elem) =>
      for {
        el <- elem
        ac <- acc
      } yield { el + ac }
    } match {
      case Some(s) => Some(s / nRuns)
      case _ => None
    }
  }
}
