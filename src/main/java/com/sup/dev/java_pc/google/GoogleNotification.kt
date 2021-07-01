package com.sup.dev.java_pc.google

import com.sup.dev.java.classes.items.Item2
import com.sup.dev.java.libs.debug.err
import com.sup.dev.java.libs.debug.info
import com.sup.dev.java.libs.json.Json
import com.sup.dev.java.libs.json.JsonArray
import com.sup.dev.java.tools.ToolsThreads
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

object GoogleNotification {

    private var onTokenNotFound: ((String) -> Unit)? = null
    private var apiKey: String? = null
    private val executePacks = ArrayList<Item2<String, Array<String>>>()

    fun init(apiKey: String) {
        GoogleNotification.apiKey = apiKey
        ToolsThreads.thread {
            while (true){
                if (executePacks.isEmpty()) ToolsThreads.sleep(1000)
                else {
                    var item: Item2<String, Array<String>>? = null
                    synchronized(executePacks) { item = executePacks.removeAt(0) }

                    if (item != null) {
                        val message = item!!.a1
                        val tokens = item!!.a2
                        val max = 500
                        var position = 0
                        while (position < tokens.size) {
                            val end = position + max
                            sendNow(message, tokens.copyOfRange(position, Math.min(tokens.size, end)))
                            position += max
                        }
                    }


                }
            }
        }
    }

    fun send(message: String, tokens: Array<String>) {
        synchronized(executePacks) { executePacks.add(Item2(message, tokens)) }
    }

    fun sendNow(message: String, tokens: Array<String>) {
        try {

            val jsonRoot = Json()
                    .put("registration_ids", JsonArray().put(*tokens))
                    .put("data", Json().put("my_data", message))
                    .put("time_to_live", 30)

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
                        if (j.containsKey("token") && j.containsKey("error") && j.getString("error").startsWith("bad registration id data: ")) {
                            onTokenNotFound!!.invoke(j.getString("token"))
                        }
                    }
                } else if (json.containsKey("results")) {
                    val jsons = json.getJsonArray("results")!!
                    for (i in 0 until jsons.size()) {
                        val j = jsons.getJson(i)
                        if (j.containsKey("error") && j.getString("error", "") == "NotRegistered") {
                            onTokenNotFound!!.invoke(tokens[i])
                        }
                    }
                }
            } else {
                info("Google notification sending error. code = $status")
                val br = BufferedReader(InputStreamReader(conn.errorStream))
                while (br.ready()) info(br.readLine())

            }

        } catch (ex: IOException) {
            err(ex)
        }

    }

    fun onTokenNotFound(onTokenNotFound: ((String) -> Unit)) {
        GoogleNotification.onTokenNotFound = onTokenNotFound
    }
}