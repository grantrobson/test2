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

import org.joda.time.LocalDate
import org.scalatest.{Matchers, OptionValues, WordSpecLike}

class DateExampleTest extends WordSpecLike with Matchers with OptionValues {
  "collectExample" should {

    "bla1" in {
      val d1 = new LocalDate(2018, 3, 15)
      val d2 = new LocalDate(2018, 6, 28)
      DateExample.dateExample1(d1, d2) shouldBe 15
    }
  }
}
