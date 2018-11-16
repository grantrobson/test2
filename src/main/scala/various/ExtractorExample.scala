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
  trait User { def name: String }
  class FreeUser(val name: String) extends User
  class PremiumUser(val name: String) extends User

  object FreeUser {
    def apply(s:String) = new FreeUser(s)
    def unapply(user: FreeUser): Option[String] = Some(user.name)
  }

  object PremiumUser {
    def apply(s:String) = new PremiumUser(s)
    def unapply(user: PremiumUser): Option[String] = Some(user.name)
  }

  def unapplyMethodOnMatch: String = {
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

  def unapplyMethodOnAssignment: String = {
    val user = FreeUser("Grant")

    /*
      Looks for an unapply in the FreeUser companion object which takes a FreeUser instance.
      Finds it so calls it with fu then assigns the result to aaa.
     */
    val FreeUser(retrievedUserName) = user
    retrievedUserName
  }

  case class Penguin(name: String, colour: String) {
    def unapplySeq(s: Penguin): Option[List[String]] = {
      Option(List(name + ":" + s.name, colour + ":" + s.colour))
    }

    def unapplySeq(s: String): Option[List[String]] = {
      Option(List(name + " (" + s + ")"))
    }
  }

  def unapplySeqMethodOnAssignment: Seq[String] = {
    class Albatross(val name: String, val colour: String) {
    }
    object Albatross {
      def apply(s:String, c: String) = new Albatross(s, c)
      def unapplySeq(a:Albatross): Option[List[String]] = {
        Option(List(a.name, a.colour))
      }
    }

    val albatross1 = Albatross("Juan", "blue")
    val Albatross(name, _) = albatross1

    // is equivalent to:-
    val capturedGroups = Albatross.unapplySeq(albatross1).get

    val c = capturedGroups.head
    val d = capturedGroups(1)

    Seq(name, c)
  }

  def unapplySeqMethodOnAssignmentForTwoObjects: Seq[String] = {
    val penguin1 = Penguin("Harold", "heliotrope")
    val penguin2 = Penguin("Sally", "green")

    val penguin1(a, b) = penguin2

    // is equivalent to:-

    val capturedGroups = penguin1.unapplySeq(penguin2).get

    val c = capturedGroups.head
    val d = capturedGroups(1)

    Seq(a,b,c,d)
  }

  def unapplySeqMethodOnAssignmentForTwoObjectsOfDifferentTypes: Seq[String] = {
    val penguin1 = Penguin("Harold", "heliotrope")

    val penguin1(a) = "deceased"

    // is equivalent to:-

    val capturedGroups = penguin1.unapplySeq("deceased").get

    val b = capturedGroups.head

    Seq(a,b)
  }
}
