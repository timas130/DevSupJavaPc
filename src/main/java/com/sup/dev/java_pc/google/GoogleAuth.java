package com.sup.dev.java_pc.google;

import com.sup.dev.java.classes.collections.Cash;
import com.sup.dev.java.libs.debug.Debug;
import com.sup.dev.java.libs.json.Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class GoogleAuth {

    private static String apiKey;
    private static Cash<String, String> cash;

    public static void init(String apiKey) {
        GoogleAuth.apiKey = apiKey;
        cash = new Cash<>(10000);
    }

    public static String getGoogleId(String token) {



        String googleId = cash.get(token);
        if (googleId != null) return googleId;

        Json json = requestTokenInfo(token);
        if(!verify(json) || !json.containsKey("sub"))return null;

        googleId = json.getString("sub");
        cash.put(token, googleId);
        return googleId;


    }

    private static boolean verify(Json tokenJson){
        if(tokenJson.containsKey("azp") && tokenJson.getString("azp").equals(apiKey))return true;
        if(tokenJson.containsKey("aud") && tokenJson.getString("aud").equals(apiKey))return true;
        return false;
    }

    private static Json requestTokenInfo(String token) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader((new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + token)).openConnection().getInputStream(), Charset.forName("UTF-8")));
            String s = "";
            while (in.ready()) s += in.readLine();

            return new Json(s);
        } catch (Exception e) {
            Debug.log(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Debug.log(e);
                }
            }
        }
        return null;
    }

}
