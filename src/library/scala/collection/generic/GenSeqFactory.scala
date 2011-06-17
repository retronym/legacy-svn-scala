/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2003-2011, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */
 


package scala.collection
package generic

import annotation.bridge

/** A template for companion objects of GenSeq and subclasses thereof.
 *
 *  @since 2.8
 */
abstract class GenSeqFactory[CC[X] <: GenSeq[X] with GenericTraversableTemplate[X, CC]] extends GenTraversableFactory[CC] {

  // For binary compatibility with 2.9.0, see SI-4709.
  @bridge
  def unapplySeq[A](x: GenSeq[A]): Some[GenSeq[A]] = Some(x)
}
