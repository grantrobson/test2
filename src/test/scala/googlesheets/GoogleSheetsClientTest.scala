package googlesheets

import org.scalatest.{Matchers, OptionValues, WordSpecLike}

import scala.collection.mutable.ArrayBuffer

class GoogleSheetsClientTest extends WordSpecLike with Matchers with OptionValues {
  "GoogleSheetsClient" should {
    "do something" in {
      val expectedResult = Some(
        ArrayBuffer(
          ArrayBuffer("TestA1","TestB1", "TestC1", "TestD1"),
          ArrayBuffer("TestA2","TestB2", "TestC2", "TestD2")
        )
      )

      val result = GoogleSheetsClient.readSheet(
        spreadsheetId = "10LpP78cTg3wcDMN71RikzElDFZta12lXtsjs8uLcUU8",
        range = "Sheet1!A1:D2"
      )
      result shouldBe expectedResult

    }
  }
}
