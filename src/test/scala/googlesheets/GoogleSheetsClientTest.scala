package googlesheets

import org.scalatest.{Matchers, OptionValues, WordSpecLike}

import scala.collection.mutable.ArrayBuffer

class GoogleSheetsClientTest extends WordSpecLike with Matchers with OptionValues {

  val TestGoogleSheetURL = "https://docs.google.com/spreadsheets/d/10LpP78cTg3wcDMN71RikzElDFZta12lXtsjs8uLcUU8/edit#gid=0"
  val TestGoogleSheetID = "10LpP78cTg3wcDMN71RikzElDFZta12lXtsjs8uLcUU8"

  "GoogleSheetsClient" should {
    s"correctly read range A1 to D2 of the sheet at $TestGoogleSheetURL" in {
      val expectedResult = Some(
        ArrayBuffer(
          ArrayBuffer("TestA1","TestB1", "TestC1", "TestD1"),
          ArrayBuffer("TestA2","TestB2", "TestC2", "TestD2")
        )
      )

      val result = GoogleSheetsClient.readSheet(
        spreadsheetId = TestGoogleSheetID,
        range = "Sheet1!A1:D2"
      )
      result shouldBe expectedResult
    }
  }
}
