package elasticsearch

import java.net.{Authenticator, PasswordAuthentication}

import scalaj.http.{Http, HttpOptions, HttpResponse}

trait ElasticClient {
  def results: HttpResponse[String] = {
    //val vv = Http("https://kibana.tools.staging.tax.service.gov.uk/api/es/search")
    val vv = Http("https://kibana.tools.staging.tax.service.gov.uk/api/es/search")
    //"https://kibana.tools.production.tax.service.gov.uk/app/kibana#/discover?_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now-24h,mode:quick,to:now))&_a=(columns:!(_source),index:'logstash-*',interval:auto,query:(query_string:(analyze_wildcard:!t,query:'app:%22ers-returns%22%20AND%20level:%22ERROR%22')),sort:!('@timestamp',desc))"

    Authenticator.setDefault(new Authenticator() {
      val username = "grant.robson"
      val password = "bd!uw5nFvhfPcwx7"
      override def getPasswordAuthentication: PasswordAuthentication = {
        new PasswordAuthentication( s"$username", s"$password".toCharArray)
      }
    })
    val jsonToPost = """{
               |        "query": {
               |            "query_string": {
               |                "query": "app:'tcs-frontend' AND level:'ERROR'"
               |            }
               |        }
               |    }""".stripMargin

    println( "\n\n" + jsonToPost)
//    val httpRequest = vv.postData(jsonToPost)
//      .options(Seq(HttpOptions.method("POST")))
//      .header("content-type","application/json")
//    httpRequest.asString


    val httpRequest = vv.postData(jsonToPost)
      .options(Seq(HttpOptions.method("GET")))
      .header("content-type","application/json")
    httpRequest.asString
  }
}
