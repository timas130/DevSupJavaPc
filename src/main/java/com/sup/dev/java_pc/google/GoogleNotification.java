package com.sup.dev.java_pc.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.sup.dev.java.classes.callbacks.simple.Callback1;
import com.sup.dev.java.libs.debug.Debug;
import com.sup.dev.java.libs.json.Json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GoogleNotification {

    private static final ThreadPoolExecutor threadPool;

    private static Callback1<String> onTokenNotFound;

    static  {
        threadPool = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    }

    public static void send(String message, String token) {
        threadPool.execute(() -> sendNow(message, token));
    }

    public static void sendNow(String message, String token) {

        try {

            Json jsonRoot =
                    new Json()
                            .put("message", new Json()
                                    .put("token", token)
                                    .put("data", new Json()
                                            .put("my_data", message))
                                    .put("android", new Json()
                                            .put("priority", "high")));

            GoogleCredential googleCredential = GoogleCredential
                    .fromStream(new FileInputStream(new File("service-account.json")))
                    .createScoped(Collections.singletonList("https://www.googleapis.com/auth/firebase.messaging"));
            googleCredential.refreshToken();
            String accessToken = googleCredential.getAccessToken();

            URL url = new URL("https://fcm.googleapis.com/v1/projects/communitymc-c51a5/messages:send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-Type", "application/json; UTF-8");
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(jsonRoot.toString());
            wr.flush();

            int status = conn.getResponseCode();

            if (status == 404 && onTokenNotFound != null)
                onTokenNotFound.callback(token);
            else if (status != 200) {
                Debug.print("Google notification sending error. code = " + status);
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while (br.ready()) Debug.print(br.readLine());
            }

        } catch (IOException ex) {
            Debug.log(ex);
        }

    }

    public static void onTokenNotFound(Callback1<String> onTokenNotFound) {
        GoogleNotification.onTokenNotFound = onTokenNotFound;
    }
}