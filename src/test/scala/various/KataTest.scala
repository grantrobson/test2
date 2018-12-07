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

import org.scalatest.{Matchers, OptionValues, WordSpecLike}

import scala.annotation.tailrec

class KataTest extends WordSpecLike with Matchers with OptionValues {
  "Abhijeets kata" should {
    "be able to arrange a string of numbers into its highest number" when {
      "I have a single digit" in {
        val myNumber = AbhijeetsKata(1)

        myNumber.getHighestPossible() shouldBe 1
      }

      "I have a two digit number" in {
        val myNumber = AbhijeetsKata(12)

        myNumber.getHighestPossible() shouldBe 21
      }

      "I have a three digit number" in {
        val myNumber = AbhijeetsKata(123)

        myNumber.getHighestPossible() shouldBe 321
      }

    }

    "To be able to rearrange a string of numbers into it's smallest number" when {
      "I have a single digit" in {
        val smallNumber = AbhijeetsKata(1)

        smallNumber.getSmallestPossible() shouldBe 1
      }

      "I have a two digit number" in {
        val myNumber = AbhijeetsKata(12)

        myNumber.getSmallestPossible() shouldBe 12
      }
    }

    "to be able to subtract lowest no from highest" when {
      "I have two numbers" in {
        val ak = AbhijeetsKata(3141)
        ak.subtractLowestFromHighest() shouldBe 3177
      }
    }

    "to be able to continue until we reach a repeated number" when {
      "I start with 3141 and stop at 6174" in {
        val test = AbhijeetsKata(3141)

        test.calculate shouldBe AbhijeetsKata(6174)
      }
    }
  }

  case class AbhijeetsKata(values: Int) {
    def getHighestPossible() : Int = {
      values.toString.toSeq.sorted.reverse.toString().toInt
    }

    def getSmallestPossible() : Int = {
      values.toString.toSeq.sorted.toString().toInt
    }

    def subtractLowestFromHighest(): Int = {
      getHighestPossible() - getSmallestPossible()
    }

    def calculate(): AbhijeetsKata = {
      val calculatedNumber = subtractLowestFromHighest()

      if (calculatedNumber == 6174) {
          AbhijeetsKata(calculatedNumber)
      }
      else {
        val abs = AbhijeetsKata(calculatedNumber)

        abs.calculate()
      }
    }
  }
}
