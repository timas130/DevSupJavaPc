package com.sup.dev.java_pc.google

import com.sup.dev.java.libs.debug.info
import com.sup.dev.java.libs.debug.error
import com.sup.dev.java.libs.json.Json
import com.sup.dev.java.libs.json.JsonArray
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


object GoogleNotification {

    private val threadPool: ThreadPoolExecutor = ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, LinkedBlockingQueue())

    private var onTokenNotFound: ((String) -> Unit)? = null
    private var apiKey: String? = null

    fun init(apiKey: String) {
        GoogleNotification.apiKey = apiKey
    }

    fun send(message: String, vararg tokens: String) {
        threadPool.execute { sendNow(message, *tokens) }
    }

    fun sendNow(message: String, vararg tokens: String) {

        try {

            val jsonRoot = Json()
                    .put("registration_ids", JsonArray().put(*tokens))
                    .put("data", Json().put("my_data", message))

            val url = URL("https://fcm.googleapis.com/fcm/send")
            val conn = url.openConnection() as HttpURLConnection
            conn.setRequestProperty("Authorization", "key=$apiKey")
            conn.setRequestProperty("Content-Type", "application/json; UTF-8")
            conn.requestMethod = "POST"
            conn.useCaches = false
            conn.doInput = true
            conn.doOutput = true

            val wr = OutputStreamWriter(conn.outputStream)
            wr.write(jsonRoot.toString())
            wr.flush()

            val status = conn.responseCode

            if (status == 200) {
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
                } else if (json.containsKey("results")) {
                    val jsons = json.getJsonArray("results")!!
                    for (i in 0 until jsons.size()) {
                        val j = jsons.getJson(i)
                        if(j.containsKey("error") && j.getString("error", "") == "NotRegistered")
                            onTokenNotFound!!.invoke(tokens[i])
                    }
                }
            } else {
                info("Google notification sending error. code = $status")
                val br = BufferedReader(InputStreamReader(conn.errorStream))
                while (br.ready()) info(br.readLine())

            }

        } catch (ex: IOException) {
            error(ex)
        }

    }

    fun onTokenNotFound(onTokenNotFound: ((String) -> Unit)) {
        GoogleNotification.onTokenNotFound = onTokenNotFound
    }
}