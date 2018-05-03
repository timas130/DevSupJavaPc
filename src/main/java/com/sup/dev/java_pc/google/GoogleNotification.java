package com.sup.dev.java_pc.google;

import com.sup.dev.java.libs.debug.Debug;
import com.sup.dev.java.libs.json.Json;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GoogleNotification {

    private final String serverKey;
    private final ThreadPoolExecutor threadPool;

    public GoogleNotification(String serverKey) {
        this.serverKey = serverKey;
        threadPool = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    }

    public void sendData(String message, String tokenId) {
        threadPool.execute(() -> sendDataNow(message, tokenId));
    }

    public void sendDataNow(String message, String tokenId) {

        try {

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=" + serverKey);
            conn.setRequestProperty("Content-Type", "application/json");

            Json jsonMessage = new Json();
            Json jsonData = new Json();

            jsonData.put("my_data", message);

            jsonMessage.put("to", tokenId);
            jsonMessage.put("data", jsonData);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(jsonMessage.toString());
            wr.flush();

            int status = conn.getResponseCode();
            if (status != 200)
                Debug.log("Google notification sending error. code = " + status);

        } catch (IOException ex) {
            Debug.log(ex);
        }

    }

}