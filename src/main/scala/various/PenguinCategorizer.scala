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

case class Penguin(name: String, age: Int)

object PenguinCategorizer {
  def isYoung(age: Int): Boolean = age < 6

  def isMiddleAged(age: Int): Boolean = age > 5 && age < 10

  val youngPenguinDescription = "Young penguin"
  val middleAgedPenguinDescription = "Middle aged penguin"
  val oldPenguinDescription = "Old penguin"

  def categorizev1(penguin: Penguin): String = {
    penguin match {
      case Penguin(_, age) if isYoung(age) => youngPenguinDescription
      case Penguin(_, age) if isMiddleAged(age) => middleAgedPenguinDescription
      case _ => oldPenguinDescription
    }
  }

  def asOption[A, B](b: => Boolean,
                     sourceOfOptionContent: A,
                     contentExtractor: A => B,
                     isSomeItem: Boolean => Boolean = identity): Option[B] =
    if (isSomeItem(b)) {
      Some(contentExtractor(sourceOfOptionContent))
    } else {
      None
    }

  val penguinExtractorFunction: Penguin => (String, Int) = p => (p.name, p.age)

  def categorize(penguin: Penguin): String = {
    object YouthfulPenguin {
      def unapply(p: Penguin): Option[(String, Int)] = {
        asOption(isYoung(p.age), penguin, penguinExtractorFunction)
      }
    }

    object MiddleAgedPenguin {
      def unapply(p: Penguin): Option[(String, Int)] = {
        asOption(isMiddleAged(p.age), penguin, penguinExtractorFunction)
      }
    }

    penguin match {
      case YouthfulPenguin(_, _) => youngPenguinDescription
      case MiddleAgedPenguin(_, _) => middleAgedPenguinDescription
      case _ => oldPenguinDescription
    }
  }
}
