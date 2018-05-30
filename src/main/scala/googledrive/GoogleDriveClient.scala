package googledrive

import java.io.{File, InputStreamReader}
import java.util

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeFlow, GoogleClientSecrets}
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.FileContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.drive.model.{File => DriveFile}
import com.google.api.services.drive.{Drive, DriveScopes}
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

object GoogleDriveClient {

  def main(args: Array[String]): Unit = {
    // first time setup
    GoogleDriveClient("my drive app", "drive-oauth-credentials", authorizationFlow = true)
  }

  def apply(appName: String, credentialsPath: String, authorizationFlow: Boolean = false) = {
    val credentialsFile = new File(credentialsPath)

    if (!authorizationFlow) {
      // on server we obviously won't be able to do the oauth auth with browser -- app needs to be deployed with creds
      if (!credentialsFile.exists) {
        throw new RuntimeException(s"Credentials does not exist ${credentialsFile.getAbsolutePath}")
      }
    }

    new GoogleDriveClient(appName, credentialsFile)
  }
}

class GoogleDriveClient(appName: String, credentialsFile: File) {

  val log = LoggerFactory.getLogger(GoogleDriveClient.getClass)

  /** Global instance of the JSON factory. */
  private val JSON_FACTORY = JacksonFactory.getDefaultInstance
  /** Global instance of the HTTP transport. */
  val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
  val DATA_STORE_FACTORY = new FileDataStoreFactory(credentialsFile)

  private val SCOPES = util.Arrays.asList(DriveScopes.DRIVE)

  private[this] def authorize: Credential = {
    val in = GoogleDriveClient.getClass.getResourceAsStream("/client_secret_google_drive.json")
    val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in))

    // Build flow and trigger user authorization request.
    val flow = new GoogleAuthorizationCodeFlow.Builder(
    HTTP_TRANSPORT,
    JSON_FACTORY,
    clientSecrets,
    SCOPES)
    .setDataStoreFactory(
      DATA_STORE_FACTORY      ).setAccessType("offline").build // must set offline of you wont get an refreshToken and the token will only work for a few hours or so
    val credential: Credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user")
    log.info("Credentials saved to " + credentialsFile.getAbsolutePath)
    credential
  }

  val drive = {
    val credential = authorize

    new Drive.Builder(
      HTTP_TRANSPORT, JSON_FACTORY, credential)
      .setApplicationName(appName)
      .build()
  }

  val root = drive.files.get("root").execute

  def pregenerateIds(numOfIds: Int): Seq[String] = {
    drive.files().generateIds().setSpace("drive").setCount(numOfIds).execute().getIds.asScala.toSeq
  }

  def createFolder(name: String, parent: Option[String]): DriveFile = {
    val fileMetadata = new DriveFile()
    fileMetadata.setName(name)

    parent.foreach(p => fileMetadata.setParents(Seq(p).asJava))

    fileMetadata.setMimeType("application/vnd.google-apps.folder")

    drive.files().create(fileMetadata)
      .setFields("id")
      .execute()
  }

  def uploadFile(localFile: File, parentDriveOption: Option[DriveFile], idOption: Option[String], checkIfExists: Boolean = false): DriveFile = {
    val fileType = "image/jpeg"
    def upload(): DriveFile = {
      val file = new DriveFile()
      file.setName(localFile.getName)

      parentDriveOption.foreach(p => file.setParents(List(p.getId).asJava))
      idOption.foreach(id => file.setId(id))

      val mediaContent = new FileContent(fileType, localFile)

      val result: DriveFile = drive.files.create(file, mediaContent).setFields("id").execute()

      log.debug("upload result is " + result.getId)

      result
    }

    if (checkIfExists) {
      val existing = getFileByParentAndName(parentDriveOption.get, localFile.getName)

      if (existing.isDefined) {
        log.info("File already exists in google drive")
        existing.get
      } else {
        upload()
      }
    } else {
      upload()
    }
  }

  def getFilesByParent(parent: DriveFile): List[DriveFile] = {
    val request: Drive#Files#List = drive.files.list.setQ("'%s' in parents and trashed = false".format(parent.getId))
    request.execute.getFiles.asScala.toList
  }

  def getFileByParentAndName(parent: DriveFile, name: String): Option[DriveFile] = {
    val request = drive.files.list.setQ("'%s' in parents and name = '%s' and trashed = false".format(parent.getId, name))
    request.execute.getFiles.asScala.toList.headOption
  }

  // unwind the path, and find each part by parent, starting with the root folder  // e.g. /raspi/camera1/files will return the [files], if it exists
  def findFileByPath(path: String): Option[DriveFile] = {
    def findFileByPath(parts: List[String], parent: DriveFile): Option[DriveFile] = {
      parts match {
        case Nil =>
          Some(parent)
        case head :: Nil =>
          getFileByParentAndName(parent, head)
        case head :: tail =>
          getFileByParentAndName(parent, head).map { child =>
            findFileByPath(parts = tail, parent = child)
          }.getOrElse(None)

      }
    }

    findFileByPath(path.split("/").toList, root)
  }
}

