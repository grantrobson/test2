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

package alternativetoselftype

import org.scalatest.{Matchers, WordSpecLike}

class TaxCalculatorTest extends WordSpecLike with Matchers  {

  /*
    Cake pattern = dependency injection - mixins
   */

  class MockedTaxThresholdProvider1 extends TaxThresholdProvider {
    override def thresholds = Set(
      (BigDecimal(0), BigDecimal(999), BigDecimal(2.5)),
      (BigDecimal(1000), BigDecimal(9999), BigDecimal(5)),
      (BigDecimal(10000), BigDecimal(99999), BigDecimal(8))
    )
  }

  class MockedTaxThresholdProvider2 extends TaxThresholdProvider {
    override def thresholds = Set(
      (BigDecimal(0), BigDecimal(1000), BigDecimal(0)),
      (BigDecimal(1000), BigDecimal(99999), BigDecimal(6.5))
    )
  }

  private val taxCalculatorWithMockedThresholds1 = new TaxCalculator {
    override val taxThresholdProvider = new MockedTaxThresholdProvider1
  }

  "TaxCalculator with two thresholds mixed in" should {
    "work for value at 2nd threshold" in {
      taxCalculatorWithMockedThresholds1.calculate(BigDecimal(1500)) shouldBe BigDecimal(5)
    }

    "work for value at 3rd threshold" in {
      taxCalculatorWithMockedThresholds1.calculate(BigDecimal(10001)) shouldBe BigDecimal(8)
    }
  }

  private val taxCalculatorWithMockedThresholds2 = new TaxCalculator {
    override val taxThresholdProvider = new MockedTaxThresholdProvider2
  }

  "TaxCalculator with one thresholds mixed in" should {
    "give zero for value at 1st threshold" in {
      taxCalculatorWithMockedThresholds2.calculate(BigDecimal(2)) shouldBe BigDecimal(0)
    }

    "give threshold for value at 2nd threshold" in {
      taxCalculatorWithMockedThresholds2.calculate(BigDecimal(1001)) shouldBe BigDecimal(6.5)
    }
  }
}
