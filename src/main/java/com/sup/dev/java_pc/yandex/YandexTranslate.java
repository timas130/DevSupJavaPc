package com.sup.dev.java_pc.yandex;

import com.sup.dev.java.libs.debug.Debug;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class YandexTranslate {

    public YandexTranslate() {

    }

    public String translate(String targetLang, String input) throws IOException {
        Debug.log(targetLang, input);
        String urlStr = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20180417T025628Z.c947d37bbba4cd90.5441a8db317dd6d5dd48210958933cda684e01ea";
        URL urlObj = new URL(urlStr);
        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeBytes("text=" + URLEncoder.encode(input, "UTF-8") + "&lang=" + targetLang);

        try {
            InputStream response = connection.getInputStream();
            String json = new java.util.Scanner(response).nextLine();
            int start = json.indexOf("[");
            int end = json.indexOf("]");
            return json.substring(start + 2, end - 1);
        }catch (IOException e){
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while (br.ready()) Debug.log(br.readLine());
            throw e;
        }


    }

}
