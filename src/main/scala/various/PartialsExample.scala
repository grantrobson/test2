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

package various

object PartialsExample {

  def partiallyAppliedFunctionExample1(): Int = {

    val a: (Int,Int,Int) => Int = (a,b,c) => a + b + c

    val b = a(1, _: Int, _: Int) // Partially applied function

    val c = b(2, _: Int) // Partially applied function

    c(9)
  }

  // Currying is the technique of transforming a function that takes multiple arguments into
  // a function that takes a single argument.

  def curriedFunctionExample(): Int = {

    // So, instead of a function a(2,3,4) taking 3 arguments we can produce a function which
    // takes one argument and returns a function which takes another argument which returns
    // a function taking a third argument:-

    val a: Int => Int => Int => Int = a => b => c => a + b + c

    a(2)(3)(4)

    // which is the same as:-

    val b: Int => (Int => (Int => Int)) = a => b => c => a + b + c

    b(2)(3)(4)
  }

  // A partial function is a function that only works for a subset of possible input values.

  // Range can be specified explicitly using isDefinedAt:-
  def partialFunctionExample1(): Int = {
    val pf = new PartialFunction[Int, Int] {
      def apply(d: Int): Int = 42/ d
      def isDefinedAt(d: Int): Boolean = d != 0
    }

    pf(42)
  }

  // Or implicitly using case statements:-
  def partialFunctionExample2(): Int = {
    val pf = PartialFunction[Int, Int] {
      case d: Int if d != 0 => 42/d
    }

    pf(42)
  }


  def andThenExample1():Int = {
    val a:Int => Int = x => x + 1
    val b:Int => Int = x => x * 10

    val c = a andThen b

    c(2)
  }

    def andThenExample2():Int = {
    def a(x:Int) : Int = x + 1
    def b(x:Int) : Int = x * 10

      // (unapplied) function and eta expansion (transformation of methods into functions)
    val c = a _ andThen b

    c(2)
  }
}
