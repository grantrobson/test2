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

class PartialsExampleTest extends WordSpecLike with Matchers with OptionValues {
  "Partial usages" should {

    "Use partially applied functions" in {
      PartialsExample.partiallyAppliedFunctionExample1() shouldBe 12
    }

    "Curry numbers correctly" in {
      PartialsExample.curriedFunctionExample() shouldBe 9
    }

    "andThenExample 1" in {
      PartialsExample.andThenExample1() shouldBe 30
    }

    "andThenExample 2" in {
      PartialsExample.andThenExample2() shouldBe 30
    }

    "Partial function example 1" in {
      PartialsExample.partialFunctionExample1() shouldBe 1
    }

    "Partial function example 2" in {
      PartialsExample.partialFunctionExample2() shouldBe 1
    }

  }
}
