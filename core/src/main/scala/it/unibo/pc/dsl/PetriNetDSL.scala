package it.unibo.pc.dsl

import it.unibo.pc.System
import it.unibo.pc.PetriNet
import it.unibo.pc.PetriNet.{PetriNet, toSystem}

import scala.collection.MultiSet

private class PN[S <: reflect.Enum]:
  var steps: Set[(MultiSet[S], MultiSet[S], MultiSet[S])] = Set.empty
  def addStep(s: (MultiSet[S], MultiSet[S], MultiSet[S])): Unit = steps = steps + s

def petriNet[S <: reflect.Enum](init: PN[S] ?=> Unit): System[MultiSet[S]] =
  given pn: PN[S] = PN[S]()
  init
  pn
  toSystem(PetriNet(pn.steps.toList: _*))

def trantition[S <: reflect.Enum](tr: (MultiSet[S], MultiSet[S], MultiSet[S]))(using pn: PN[S]) =
  pn.addStep(tr)

extension[S <: reflect.Enum : PN](m1: MultiSet[S])
  def ~~>(m2: MultiSet[S]): (MultiSet[S], MultiSet[S], MultiSet[S]) = {
    summon[PN[S]].addStep(m1, m2, MultiSet.empty)
    (m1, m2, MultiSet.empty)
  }
  def ^^^(m2: MultiSet[S], inh: MultiSet[S]) = {
    summon[PN[S]].addStep(m1, m2, inh)
  }
