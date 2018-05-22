/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package monads

abstract class Mebbees[+A] {
  def isDefined: Boolean

  def get: A

  def flatMap[B](func: A => Mebbees[B]): Mebbees[B] =
    if (isDefined) {
      func(this.get)
    } else {
      Nowt
    }

  def map[B](func: A => B): Mebbees[B] =
    if (isDefined) {
      Summat(func(this.get))
    } else {
      Nowt
    }

  /*
    Alternatively, map can be defined using flatMap:-
   */
  //  def map[B](func: A => B): Mebbees[B] =
  //    if (isDefined) {
  //      flatMap(value => Summat(func(value)))
  //    } else {
  //      Nowt
  //    }
}

/*
  Nothing is at the bottom of Scala's inheritance hierarchy, i.e. it is a subtype of every
  other type.
 */
case object Nowt extends Mebbees[Nothing] {
  def isDefined = false

  def get = throw new NoSuchElementException("Nowt.get")
}

case class Summat[A](objectToEncapsulate: A) extends Mebbees[A] {
  def isDefined = true

  def get = objectToEncapsulate
}
