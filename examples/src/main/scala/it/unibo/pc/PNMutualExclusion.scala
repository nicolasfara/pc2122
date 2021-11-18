package it.unibo.pc

import it.unibo.pc.PNMutualExclusion.Place
import it.unibo.pc.PNMutualExclusion.Place.C
import it.unibo.pc.PNMutualExclusion.Place.N
import it.unibo.pc.PNMutualExclusion.Place.T
import it.unibo.pc.PetriNet.toSystem
import it.unibo.pc.utils.given

import scala.collection.MultiSet
import scala.language.implicitConversions

object PNMutualExclusion extends App {

  enum Place {
    case N, T, C
  }

  import Place.*

  // DSL-like specification of A Petri Net
  def mutualExclusionSystem() = toSystem(
    PetriNet(N ~~> T, T ~~> C ^^^ C, C ~~> MultiSet.empty),
  )

  // example usage
  println(mutualExclusionSystem().paths(MultiSet(N, N), 7).toList.mkString("\n"))
}
