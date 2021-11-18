package it.unibo.pc

import it.unibo.pc.utils.*

import scala.collection.MultiSet

object PriorityPetriNet {
  // pre-condition, priority, effects, inhibitions
  type PriorityPetriNet[S <: reflect.Enum] = Set[(MultiSet[S], Int, MultiSet[S], MultiSet[S])]

  def apply[S <: reflect.Enum](transitions: (MultiSet[S], Int, MultiSet[S], MultiSet[S])*): PriorityPetriNet[S] =
    transitions.toSet

  def toPartialFunction[S <: reflect.Enum](
      pn: PriorityPetriNet[S],
  ): PartialFunction[MultiSet[S], Set[MultiSet[S]]] = {
    case marking => {
      val outStates = for {
        (condition, priority, effects, inhibitions) <- pn if (marking disjoined inhibitions)
        p = priority
        out <- marking extract condition
      } yield (p, out union effects)
      if (outStates.isEmpty) then { println("Codio"); Set.empty }
      else {
        val maxPriority = outStates.map(_._1).max
        println(maxPriority)
        outStates.filter(_._1 == maxPriority).map(_._2).toSet
      }
    }
  }

  def toSystem[S <: reflect.Enum](pn: PriorityPetriNet[S]): System[MultiSet[S]] =
    System.ofFunction(toPartialFunction(pn))
}
