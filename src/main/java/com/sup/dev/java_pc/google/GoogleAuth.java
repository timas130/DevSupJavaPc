package com.sup.dev.java_pc.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.sup.dev.java.classes.collections.Cash;
import com.sup.dev.java.libs.debug.Debug;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleAuth {

    private static Cash<String, String> cash;
    private static GoogleIdTokenVerifier verifier;

    public static void init(String apiKey) {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(apiKey))
                .build();

        cash = new Cash<>(10000);
    }


    public static String getGoogleId(String token) {
        String googleId = cash.get(token);
        if (googleId != null) return googleId;
        try {
            GoogleIdToken gToken = verifier.verify(token);
            Debug.log("G TOKEN == " + gToken);
            Debug.log("G TOKEN == " + gToken);
            Debug.log("G TOKEN == " + gToken);
            Debug.log("G TOKEN == " + gToken);
            Debug.log("G TOKEN == " + gToken);
            Debug.log("G TOKEN == " + gToken);
            Debug.log("G TOKEN == " + gToken);
            Debug.log("G TOKEN == " + gToken);
            if (gToken != null) {
                GoogleIdToken.Payload payload = gToken.getPayload();
                googleId = payload.getSubject();
                cash.put(token, googleId);
                return googleId;
            } else {
                return null;
            }
        } catch (GeneralSecurityException | IOException | IllegalArgumentException e) {
            Debug.log(e);
            return null;
        }
    }

}
