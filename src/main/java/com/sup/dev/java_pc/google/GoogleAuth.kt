package com.sup.dev.java_pc.google

import com.sup.dev.java.classes.collections.Cash
import com.sup.dev.java.libs.debug.Debug
import com.sup.dev.java.libs.json.Json
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.Charset


object GoogleAuth {

    private var apiKey: String? = null
    private var cash: Cash<String, String>? = null

    fun init(apiKey: String) {
        GoogleAuth.apiKey = apiKey
        cash = Cash(10000)
    }

    fun getGoogleId(token: String): String? {

        var googleId = cash!![token]
        if (googleId != null) return googleId

        val json = requestTokenInfo(token)
        if (!verify(json!!) || !json.containsKey("sub")) return null

        googleId = json.getString("sub")
        cash!!.put(token, googleId)
        return googleId


    }

    private fun verify(tokenJson: Json): Boolean {
        if (tokenJson.containsKey("azp") && tokenJson.getString("azp") == apiKey) return true
        return if (tokenJson.containsKey("aud") && tokenJson.getString("aud") == apiKey) true else false
    }

    private fun requestTokenInfo(token: String): Json? {
        var inp: BufferedReader? = null
        try {
            inp = BufferedReader(InputStreamReader(URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=$token").openConnection().getInputStream(), Charset.forName("UTF-8")))
            var s = ""
            while (inp.ready()) s += inp.readLine()

            return Json(s)
        } catch (e: Exception) {
            Debug.log(e)
        } finally {
            if (inp != null) {
                try {
                    inp.close()
                } catch (e: IOException) {
                    Debug.log(e)
                }

            }
        }
        return null
    }

}
