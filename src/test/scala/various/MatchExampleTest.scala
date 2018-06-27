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

class MatchExampleTest extends WordSpecLike with Matchers with OptionValues {
  "atMatchExample" should {
    "return the full option for the first item when 2nd is none, not just the contained item" in {
      MatchExample.atMatchExample(Some("abc"), None) shouldBe Some("abc")
    }
    "return the full option for the 2nd arg when first is none, not just the contained item" in {
      MatchExample.atMatchExample(None, Some("def")) shouldBe Some("def")
    }
  }
}
