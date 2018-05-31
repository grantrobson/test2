package googlesheets

import java.io.{File, IOException}
import java.security.GeneralSecurityException
import java.util.Collections

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeFlow, GoogleClientSecrets}
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.{Sheets, SheetsScopes}
import collection.JavaConverters._

import scala.collection.mutable

object GoogleSheetsClient {
  private val APPLICATION_NAME = "Google Sheets API Java Quickstart"
  private val JSON_FACTORY = JacksonFactory.getDefaultInstance
  private val CREDENTIALS_FOLDER = "credentials" // Directory to store user credentials.

  /**
    * Global instance of the scopes required by this quickstart.
    * If modifying these scopes, delete your previously saved credentials/ folder.
    */
  private val SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)
  private val CLIENT_SECRET_DIR = "client_secret_google_sheets.json"

  /**
    * Creates an authorized Credential object.
    *
    * @param HTTP_TRANSPORT The network HTTP Transport.
    * @return An authorized Credential object.
    * @throws IOException If there is no client_secret.
    */
  @throws[IOException]
  private def getCredentials(HTTP_TRANSPORT: NetHttpTransport) = { // Load client secrets.
    import scala.io.Source
    val in = Source.fromResource(CLIENT_SECRET_DIR).reader
    val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in)
    // Build flow and trigger user authorization request.
    val flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(new File(CREDENTIALS_FOLDER))).setAccessType("offline").build
    new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver).authorize("user")
  }
  /**
    * Prints the names and majors of students in a sample spreadsheet:
    * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
    */
  @throws[IOException]
  @throws[GeneralSecurityException]
  def readSheet(spreadsheetId: String, range: String): Option[mutable.Buffer[mutable.Buffer[String]]] = {
    val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport
    val service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
      .setApplicationName(APPLICATION_NAME)
      .build()
    val response = service.spreadsheets.values.get(spreadsheetId, range).execute
    val values = response.getValues
    if (values == null || values.isEmpty) None
    else {
      Some(values.asScala.map(_.asScala.map(_.toString)))
    }
  }
}
