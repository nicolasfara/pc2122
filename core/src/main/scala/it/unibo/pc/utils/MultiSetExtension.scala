package it.unibo.pc.utils

import scala.collection.MultiSet

extension [S](mSet: MultiSet[S])
  def disjoined(otherSet: MultiSet[S]): Boolean = (mSet.toList intersect otherSet.toList).isEmpty
  def diff(otherSet: MultiSet[S]): MultiSet[S] = MultiSet((mSet.toList diff otherSet.toList): _*)

  def extract(otherSet: MultiSet[S]): Option[MultiSet[S]] =
    Option(mSet diff otherSet) filter (_.size == mSet.size - otherSet.size)

  def union(otherSet: MultiSet[S]): MultiSet[S] = MultiSet((mSet.toList ++ otherSet.toList): _*)
