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
  "extractorExample" should {
    "find the correct unapply method on a match" in {
      ExtractorExample.unapplyMethodOnMatch shouldBe "Welcome back paying client Daniel"
    }
    "find the correct unapply method on an assignment" in {
      ExtractorExample.unapplyMethodOnAssignment shouldBe "Grant"
    }

    "find the correct unapply method on an assignment for unapplySeq" in {
      ExtractorExample.unapplySeqMethodOnAssignment shouldBe Seq("Juan", "Juan")
    }

    "find the correct unapply method on an assignment for unapplySeq to combine two object together" in {
      val result = ExtractorExample.unapplySeqMethodOnAssignmentForTwoObjects
      println( "\nResult=" + result)
      result.head shouldBe result(2)
      result(1) shouldBe result(3)
    }

    "find the correct unapply method on an assignment for unapplySeq to combine one object and a different type of object together" in {
      val result = ExtractorExample.unapplySeqMethodOnAssignmentForTwoObjectsOfDifferentTypes
      println( "\nResult=" + result)
      result.head shouldBe result(1)
    }
  }
}
