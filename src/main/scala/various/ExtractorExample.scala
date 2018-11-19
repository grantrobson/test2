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

object ExtractorExample {
  def staticUnapplyMethodOnMatch: String = {

    trait User {
      def name: String
    }

    class FreeUser(val name: String) extends User

    class PremiumUser(val name: String) extends User

    object FreeUser {
      def apply(s: String) = new FreeUser(s)

      def unapply(user: FreeUser): Option[String] = Some(user.name)
    }

    object PremiumUser {
      def apply(s: String) = new PremiumUser(s)

      def unapply(user: PremiumUser): Option[String] = Some(user.name)
    }

    val user: User = PremiumUser("Daniel")
    user match {
      /*
       Looks for an unapply in the FreeUser companion object which takes PremiumUser instance.
       Doesn't find so returns None.
      */
      case FreeUser(name) =>
        "Hello cheapskate user " + name
      case PremiumUser(name) =>
        "Welcome back paying client " + name
    }
  }

  def staticUnapplyMethodOnAssignment: String = {

    trait User {
      def name: String
    }

    class FreeUser(val name: String) extends User

    class PremiumUser(val name: String) extends User

    object FreeUser {
      def apply(s: String) = new FreeUser(s)

      def unapply(user: FreeUser): Option[String] = Some(user.name)
    }

    object PremiumUser {
      def apply(s: String) = new PremiumUser(s)

      def unapply(user: PremiumUser): Option[String] = Some(user.name)
    }

    val user = FreeUser("Grant")

    /*
      Looks for an unapply in the FreeUser companion object which takes a FreeUser instance.
      Finds it so calls it with fu then assigns the result to aaa.
     */
    val FreeUser(retrievedUserName) = user
    retrievedUserName
  }

  def staticUnapplyMethodOnAssignmentMultipleAttributes: Seq[String] = {
    class Albatross(val name: String, val colour: String) {
    }
    object Albatross {
      def apply(s: String, c: String) = new Albatross(s, c)
      def unapply(a: Albatross): Option[(String, String)] = {
        Option(Tuple2(a.name, a.colour))
      }
    }

    val albatross1 = Albatross("Stewart", "blue")
    val Albatross(name, _) = albatross1

    // is equivalent to:-
    val capturedGroups = Albatross.unapply(albatross1).get

    val c = capturedGroups._1
    Seq(name, c)
  }

  def staticUnapplyMethodOnMatchMultipleAttributes: String = {
    class Albatross(val name: String, val colour: String) {
    }
    object Albatross {
      def apply(s: String, c: String) = new Albatross(s, c)
      def unapply(a: Albatross): Option[(String, String)] = {
        Option(Tuple2(a.name, a.colour))
      }
    }

    val albatross1 = Albatross("Stewart", "blue")
    albatross1 match {
      case Albatross(n, c) => "Hello " + n + " - your colour is " + c
      case _ => "Not found"
    }
  }

  def staticUnapplySeqMethodOnAssignmentMultipleAttributes: Seq[String] = {
    class Albatross(val name: String, val colour: String) {
    }
    object Albatross {
      def apply(s: String, c: String) = new Albatross(s, c)

      def unapplySeq(a: Albatross): Option[List[String]] = {
        Option(List(a.name, a.colour))
      }
    }

    val albatross1 = Albatross("Stewart", "blue")
    val Albatross(a, b) = albatross1

    // is equivalent to:-
    val capturedGroups = Albatross.unapplySeq(albatross1).get
    val c = capturedGroups.head
    val d = capturedGroups(1)

    Seq(a, b, c, d)
  }

  def staticUnapplySeqMethodOnMatchMultipleAttributes: String = {
    class Albatross(val name: String, val colour: String) {
    }
    object Albatross {
      def apply(s: String, c: String) = new Albatross(s, c)

      def unapplySeq(a: Albatross): Option[List[String]] = {
        Option(List(a.name, a.colour))
      }
    }

    val albatross1 = Albatross("Stewart", "blue")

    albatross1 match {
      case Albatross(a, b) => "The values extracted are: " + a + "," + b
      case _ => "No values extracted"
    }
  }

  def staticUnapplySeqMethodOnAssignmentVariableAttributes: Seq[String] = {
    class Albatross(val name: String, val colours: List[String]) {
    }
    object Albatross {
      def apply(s: String, c: List[String]) = new Albatross(s, c)

      def unapplySeq(a: Albatross): Option[List[String]] = {
        Option(List(a.name) ++ a.colours)
      }
    }

    val albatross1 = Albatross("Stewart", List("blue", "green"))
    val Albatross(a, b, c) = albatross1

    // is equivalent to:-
    val capturedGroups = Albatross.unapplySeq(albatross1).get
    val d = capturedGroups.head
    val e = capturedGroups(1)
    val f = capturedGroups(2)

    Seq(a, b, c, d, e, f)
  }

  def staticUnapplySeqMethodOnMatchVariableAttributes: String = {
    class Albatross(val name: String, val colours: List[String]) {
    }
    object Albatross {
      def apply(s: String, c: List[String]) = new Albatross(s, c)

      def unapplySeq(a: Albatross): Option[List[String]] = {
        Option(List(a.name) ++ a.colours)
      }
    }

    val albatross1 = Albatross("Stewart", List("blue", "green"))
    albatross1 match {
      // This will never match because there are two items in the list, not one
      case Albatross(_, b) => "There is only one item in the list"
      // This one will match - as list has two colours
      case Albatross(_, b, c) => "The colours are: " + b + "," + c
      case _ => "No values extracted"
    }
  }

  def unapplySeqMethodOnAssignmentCombiningTwoObjects: Seq[String] = {
    case class Penguin(name: String, colour: String) {
      // An unapplySeq defined on a class allows object to be combined with another:-
      def unapplySeq(s: Penguin): Option[List[String]] = {
        Option(List(name + ":" + s.name, colour + ":" + s.colour))
      }
    }
    val penguin1 = Penguin("Harold", "heliotrope")
    val penguin2 = Penguin("Sally", "green")

    val penguin1(a, b) = penguin2

    // is equivalent to:-

    val capturedGroups = penguin1.unapplySeq(penguin2).get

    val c = capturedGroups.head
    val d = capturedGroups(1)

    Seq(a, b, c, d)
  }

  def unapplySeqMethodOnAssignmentCombiningTwoObjectsOfDifferentTypes: Seq[String] = {
    case class Penguin(name: String, colour: String) {
      // An unapplySeq defined on a class allows object to be combined with another:-
      def unapplySeq(s: String): Option[List[String]] = {
        Option(List(name + " (" + s + ")"))
      }
    }
    val penguin1 = Penguin("Harold", "heliotrope")

    val penguin1(a) = "deceased"

    // is equivalent to:-

    val capturedGroups = penguin1.unapplySeq("deceased").get

    val b = capturedGroups.head

    Seq(a, b)
  }

  object penguinv1 {
    case class Penguin(name: String, age: Int)

    object PenguinCategorizer {
      private def isYoung(age: Int): Boolean = age < 6

      private def isMiddleAged(age: Int): Boolean = age > 5 && age < 10

      val youngPenguinDescription = "Young penguin"
      val middleAgedPenguinDescription = "Middle aged penguin"
      val oldPenguinDescription = "Old penguin"

      def categorize(penguin: Penguin): String = {
        penguin match {
          case Penguin(_, age) if isYoung(age) => youngPenguinDescription
          case Penguin(_, age) if isMiddleAged(age) => middleAgedPenguinDescription
          case _ => oldPenguinDescription
        }
      }
    }

  }

  object penguinv2 {
    case class Penguin(name: String, age: Int)

    object PenguinCategorizer {
      private def isYoung(age: Int): Boolean = age < 6
      private def isMiddleAged(age: Int): Boolean = age > 5 && age < 10

      object YouthfulPenguin {
        def unapply(p: Penguin): Option[(String, Int)] = {
          if (isYoung(p.age)) {
            Some(Tuple2(p.name, p.age))
          } else {
            None
          }
        }
      }

      object MiddleAgedPenguin {
        def unapply(p: Penguin): Option[(String, Int)] = {
          if (isMiddleAged(p.age)) {
            Some(Tuple2(p.name, p.age))
          } else {
            None
          }
        }
      }

      def categorize(penguin: Penguin): String =
        penguin match {
          case YouthfulPenguin(_, _) => "Young penguin"
          case MiddleAgedPenguin(_, _) => "Middle aged penguin"
          case _ => "Old penguin"
        }
      }
  }

}
