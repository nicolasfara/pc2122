package it.unibo.pc.utils

import scala.collection.MultiSet
import scala.quoted.{ Expr, Quotes, Type }

extension [S <: reflect.Enum](mSet: MultiSet[S])
  def apply(elem: S): Int = mSet.count(elem == _)
  def containsAll(otherSet: MultiSet[S]): Boolean = otherSet.forall(mSet contains _)
  def intersect(otherSet: MultiSet[S]): MultiSet[S] = MultiSet((mSet.toList intersect otherSet.toList): _*)
  def disjoined(otherSet: MultiSet[S]): Boolean = (mSet.toList intersect otherSet.toList).isEmpty
  def diff(otherSet: MultiSet[S]): MultiSet[S] = MultiSet((mSet.toList diff otherSet.toList): _*)

  def extract(otherSet: MultiSet[S]): Option[MultiSet[S]] =
    Option(mSet diff otherSet) filter (_.size == mSet.size - otherSet.size)

  def union(otherSet: MultiSet[S]): MultiSet[S] = MultiSet((mSet.toList ++ otherSet.toList): _*)

given Conversion[Tuple, MultiSet[reflect.Enum]] with
  def apply(tuple: Tuple): MultiSet[reflect.Enum] = MultiSet(tuple.toList.asInstanceOf[List[reflect.Enum]]: _*)

given Conversion[reflect.Enum, MultiSet[reflect.Enum]] = MultiSet(_)
