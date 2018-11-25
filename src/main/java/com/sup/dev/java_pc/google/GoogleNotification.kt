package com.sup.dev.java_pc.google

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.sup.dev.java.libs.debug.Debug

import com.sup.dev.java.libs.json.Json
import com.sup.dev.java.libs.json.JsonArray
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


object GoogleNotification {

    private val threadPool: ThreadPoolExecutor

    private var onTokenNotFound: ((String) -> Unit)? = null
    private var urlKey: String? = null
    private var filePostfix: String = ""

    init {
        threadPool = ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, LinkedBlockingQueue())
    }

    fun init(urlKey: String, filePostfix: String = "") {
        GoogleNotification.urlKey = urlKey
        GoogleNotification.filePostfix = filePostfix
    }

    fun send(message: String, vararg tokens: String) {
        threadPool.execute { sendNow(message, *tokens) }
    }

    fun sendNow(message: String, vararg tokens: String) {

        try {

            val jsonRoot = Json()
                    .put("message", Json()
                            .put("tokens", JsonArray().put(*tokens))
                            .put("android", Json().put("ttl", "20s"))
                            .put("data", Json().put("my_data", message))
                            .put("android", Json().put("priority", "high")))

            val googleCredential = GoogleCredential
                    .fromStream(FileInputStream(File("service-account$filePostfix.json")))
                    .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging"))
            googleCredential.refreshToken()
            val accessToken = googleCredential.accessToken

            val url = URL("https://fcm.googleapis.com/v1/projects/$urlKey/messages:send")
            val conn = url.openConnection() as HttpURLConnection
            conn.setRequestProperty("Authorization", "Bearer $accessToken")
            conn.setRequestProperty("Content-Type", "application/json; UTF-8")
            conn.requestMethod = "POST"
            conn.useCaches = false
            conn.doInput = true
            conn.doOutput = true

            val wr = OutputStreamWriter(conn.outputStream)
            wr.write(jsonRoot.toString())
            wr.flush()

            val status = conn.responseCode

            if (status != 200) {
                Debug.print("Google notification sending error. code = $status")
                val br = BufferedReader(InputStreamReader(conn.errorStream))
                while (br.ready()) Debug.print(br.readLine())
            } else {
                val br = BufferedReader(InputStreamReader(conn.inputStream))
                var s = ""
                while (br.ready()) s += br.readLine()
                val json = Json(s)
                if (json.containsKey("errors")) {
                    val jsons = json.getJsonArray("errors")!!
                    for (i in 0 until jsons.size()) {
                        val j = jsons.getJson(i)
                        if (j.containsKey("token") && j.containsKey("error") && j.getString("error")!!.startsWith("bad registration id data: ")) {
                            onTokenNotFound!!.invoke(j.getString("token")!!)
                        }
                    }
                }
            }

        } catch (ex: IOException) {
            Debug.log(ex)
        }

    }

    fun onTokenNotFound(onTokenNotFound: ((String) -> Unit)) {
        GoogleNotification.onTokenNotFound = onTokenNotFound
    }
}