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

class ExtractorExampleTest extends WordSpecLike with Matchers with OptionValues {
  "ExtractorExample object" should {
    "find the correct unapply method on a match when staticUnapplyMethodOnMatch is called" in {
      ExtractorExample.staticUnapplyMethodOnMatch shouldBe "Welcome back paying client Daniel"
    }












































    "find the correct unapply method on an assignment when staticUnapplyMethodOnAssignment is called" in {
      ExtractorExample.staticUnapplyMethodOnAssignment shouldBe "Grant"
    }














































    "find the correct unapply method on an assignment for unapply where there are 2 attributes when staticUnapplyMethodOnAssignmentMultipleAttributes is called" in {
      ExtractorExample.staticUnapplyMethodOnAssignmentMultipleAttributes shouldBe Seq("Stewart", "Stewart")
    }













































    "find the correct unapply method on an match for unapply where there are 2 attributes when staticUnapplyMethodOnMatchMultipleAttributes is called" in {
      ExtractorExample.staticUnapplyMethodOnMatchMultipleAttributes shouldBe "Hello Stewart - your colour is blue"
    }













































    "penguin categorizer v1" should {
      "match first match object" in {
        val youngPenguin = ExtractorExample.penguinv1.Penguin("Arnold", 3)
        ExtractorExample.penguinv1.PenguinCategorizer.categorize(youngPenguin) shouldBe "Young penguin"
      }
      "match second match object" in {
        val middleAgedPenguin = ExtractorExample.penguinv1.Penguin("Sam", 7)
        ExtractorExample.penguinv1.PenguinCategorizer.categorize(middleAgedPenguin) shouldBe "Middle aged penguin"
      }
      "match else condition" in {
        val oldPenguin = ExtractorExample.penguinv1.Penguin("Stan", 18)
        ExtractorExample.penguinv1.PenguinCategorizer.categorize(oldPenguin) shouldBe "Old penguin"
      }
      "calculate correct life insurance risk for first match object" in {
        val youngPenguin = ExtractorExample.penguinv1.Penguin("Arnold", 3)
        ExtractorExample.penguinv1.PenguinCategorizer.calculateLifeInsuranceRisk(youngPenguin) shouldBe 25
      }
      "calculate correct life insurance risk for second match object" in {
        val middleAgedPenguin = ExtractorExample.penguinv1.Penguin("Sam", 7)
        ExtractorExample.penguinv1.PenguinCategorizer.calculateLifeInsuranceRisk(middleAgedPenguin) shouldBe 50
      }
      "calculate correct life insurance risk for else condition" in {
        val oldPenguin = ExtractorExample.penguinv1.Penguin("Stan", 18)
        ExtractorExample.penguinv1.PenguinCategorizer.calculateLifeInsuranceRisk(oldPenguin) shouldBe 75
      }
    }


































    "penguin categorizer v2" should {
      "match first match object" in {
        val youngPenguin = ExtractorExample.penguinv2.Penguin("Arnold", 3)
        ExtractorExample.penguinv2.PenguinCategorizer.categorize(youngPenguin) shouldBe "Young penguin"
      }
      "match second match object" in {
        val middleAgedPenguin = ExtractorExample.penguinv2.Penguin("Sam", 7)
        ExtractorExample.penguinv2.PenguinCategorizer.categorize(middleAgedPenguin) shouldBe "Middle aged penguin"
      }
      "match else condition" in {
        val oldPenguin = ExtractorExample.penguinv2.Penguin("Stan", 18)
        ExtractorExample.penguinv2.PenguinCategorizer.categorize(oldPenguin) shouldBe "Old penguin"
      }
      "calculate correct life insurance risk for first match object" in {
        val youngPenguin = ExtractorExample.penguinv2.Penguin("Arnold", 3)
        ExtractorExample.penguinv2.PenguinCategorizer.calculateLifeInsuranceRisk(youngPenguin) shouldBe 25
      }
      "calculate correct life insurance risk for second match object" in {
        val middleAgedPenguin = ExtractorExample.penguinv2.Penguin("Sam", 7)
        ExtractorExample.penguinv2.PenguinCategorizer.calculateLifeInsuranceRisk(middleAgedPenguin) shouldBe 50
      }
      "calculate correct life insurance risk for else condition" in {
        val oldPenguin = ExtractorExample.penguinv2.Penguin("Stan", 18)
        ExtractorExample.penguinv2.PenguinCategorizer.calculateLifeInsuranceRisk(oldPenguin) shouldBe 75
      }
    }


































    "find the correct unapply method on an assignment for unapplySeq when staticUnapplySeqMethodOnAssignmentMultipleAttributes is called" in {
      ExtractorExample.staticUnapplySeqMethodOnAssignmentMultipleAttributes shouldBe Seq("Stewart", "blue", "Stewart", "blue")
    }














































    "find the correct unapply method on an match for unapplySeq when staticUnapplySeqMethodOnMatchMultipleAttributes is called" in {
      ExtractorExample.staticUnapplySeqMethodOnMatchMultipleAttributes shouldBe "The values extracted are: Stewart,blue"
    }














































    "find the correct unapply method on an assignment for unapplySeq where variable attributes where staticUnapplySeqMethodOnAssignmentVariableAttributes is called" in {
      ExtractorExample.staticUnapplySeqMethodOnAssignmentVariableAttributes shouldBe List("Stewart", "blue", "green", "Stewart", "blue", "green")
    }













































    "find the correct unapply method on an match for unapplySeq where variable attributes where staticUnapplySeqMethodOnMatchVariableAttributes is called" in {
      ExtractorExample.staticUnapplySeqMethodOnMatchVariableAttributes shouldBe "The colours are: blue,green"
    }













































    "find the correct unapply method on an assignment for unapplySeq to combine two object together where unapplySeqMethodOnAssignmentCombiningTwoObjects is called" in {
      val result = ExtractorExample.unapplySeqMethodOnAssignmentCombiningTwoObjects
      println( "\nResult=" + result)
      result.head shouldBe result(2)
      result(1) shouldBe result(3)
    }











































    "find the correct unapply method on an assignment for unapplySeq to combine one object and a different type of object together where unapplySeqMethodOnAssignmentCombiningTwoObjectsOfDifferentTypesis called" in {
      val result = ExtractorExample.unapplySeqMethodOnAssignmentCombiningTwoObjectsOfDifferentTypes
      println( "\nResult=" + result)
      result.head shouldBe result(1)
    }











































  }
}
