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

import org.scalatest.{Matchers, OptionValues, WordSpecLike}

class MebbeesTest extends WordSpecLike with Matchers with OptionValues {

  val theCatSatOnTheMat = "the cat sat on the mat"
  val mebbeesTheCatSatOnTheMat = Summat(theCatSatOnTheMat)

  val andFellAsleep = " and fell asleep"

  "A Summat object" should {

    "indicate it is defined if it encapsulates a value" in {
      Summat("one").isDefined shouldBe true
    }

    "indicate it is not defined if it does not encapsulate a value" in {
      Nowt.isDefined shouldBe false
    }

    "allow an encapsulated value to be retrieved" in {
      mebbeesTheCatSatOnTheMat.get shouldBe theCatSatOnTheMat
    }

    "throw a NoSuchElementException if an attempt made to retrieve where no value encapsulated" in {
      a [NoSuchElementException] shouldBe thrownBy {
        Nowt.get
      }
    }

    "allow an encapsulated value to be mapped to another value of the same class" in {
      mebbeesTheCatSatOnTheMat.map(_ + andFellAsleep).get shouldBe theCatSatOnTheMat + andFellAsleep
    }

    "allow an encapsulated value to be mapped to another value of a different class" in {
      mebbeesTheCatSatOnTheMat.map(_.length).get shouldBe theCatSatOnTheMat.length
    }

    "allow an encapsulated value to be flat mapped to another value of the same type" in {
      mebbeesTheCatSatOnTheMat.flatMap(xx => Summat(xx + andFellAsleep)).get shouldBe theCatSatOnTheMat + andFellAsleep
    }

    "allow an encapsulated value to be flat mapped to another value of a different type" in {
      mebbeesTheCatSatOnTheMat.flatMap(xx => Summat(xx.length)).get shouldBe theCatSatOnTheMat.length
    }

    /*
      If you have a monad with a value in it and a function that takes the same type of value and
      returns the same type of monad, then flatMapping it on the monad or just simply applying it to the value
      should yield the same result.
     */
    "obey the left identity law" in {
      val func: String => Summat[String] = a => Summat(a + andFellAsleep)
      mebbeesTheCatSatOnTheMat.flatMap(func) shouldBe func(theCatSatOnTheMat)
    }

    /*
      If you have a monad with a value in it and you have a function that takes the same type of value and
      wraps it in the same kind of monad untouched, then after flatMapping that function on your monad should not
      change it.
     */
    "obey the right identity law" in {
      mebbeesTheCatSatOnTheMat.flatMap(Summat(_)) shouldBe mebbeesTheCatSatOnTheMat
    }

    /*
      If you have a monad and a chain of functions that operates on it as the previous two did, then it
      should not matter how you nest the flatMappings of those functions.
     */
    "obey the associativity law" in {
      val func1: String => Summat[String] = a => Summat(a + andFellAsleep)
      val func2: String => Summat[String] = a => Summat(a + " then woke up again")
      mebbeesTheCatSatOnTheMat.flatMap(func1).flatMap(func2) shouldBe
        mebbeesTheCatSatOnTheMat.flatMap(func1(_).flatMap(func2))
    }

    "be capable of participating in a for comprehension" in {
      val result =
        for (b <- mebbeesTheCatSatOnTheMat) yield b + andFellAsleep
      result shouldBe Summat(theCatSatOnTheMat + andFellAsleep)
    }
  }
}
