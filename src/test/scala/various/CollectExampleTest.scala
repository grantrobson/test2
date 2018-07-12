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

class CollectExampleTest extends WordSpecLike with Matchers with OptionValues {
  "collectExample" should {

    "bla1" in {
      CollectExample.collectExample1 shouldBe Seq("two", "one", "two", "two")
    }

    "bla2" in {
      CollectExample.collectExample2 shouldBe Seq("two", "one", "two", "two")
    }

    "bla3" in {
      CollectExample.collectExample3 shouldBe Seq("two", "one", "two", "two")
    }

    "bla4" in {
      CollectExample.collectExample4 shouldBe Seq("two", "one")
    }

    "bla5" in {
      a [RuntimeException] shouldBe thrownBy { // throws match error
        CollectExample.collectExample5
      }
    }
  }
}
