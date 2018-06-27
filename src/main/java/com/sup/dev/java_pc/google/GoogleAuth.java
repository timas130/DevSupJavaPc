package com.sup.dev.java_pc.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.sup.dev.java.classes.collections.Cash;
import com.sup.dev.java.libs.debug.Debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GoogleAuth {

    private static Cash<String, String> cash;
    private static GoogleIdTokenVerifier verifier;

    public static void init(String apiKey) {

        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(apiKey))
                .build();
        cash = new Cash<>(10000);
    }


    public static String getGoogleId(String token) {

        String s = getMapFromGoogleTokenString(token);
        Debug.log("G TOKEN == " + s);
        Debug.log("G TOKEN == " + s);
        Debug.log("G TOKEN == " + s);
        Debug.log("G TOKEN == " + s);
        Debug.log("G TOKEN == " + s);
        Debug.log("G TOKEN == " + s);


        String googleId = cash.get(token);
        if (googleId != null) return googleId;
        try {
            GoogleIdToken gToken = verifier.verify(token);
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

    private static String getMapFromGoogleTokenString(final String idTokenString){
        BufferedReader in = null;
        try {
            // get information from token by contacting the google_token_verify_tool url :
            in = new BufferedReader(new InputStreamReader(
                    (new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + idTokenString.trim()))
                            .openConnection().getInputStream(), Charset.forName("UTF-8")));

            // read information into a string buffer :
            StringBuffer b = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                b.append(inputLine + "\n");
            }

           return inputLine;

            // exception handling :
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            System.out.println("\n\n\tFailed to transform json to string\n");
            e.printStackTrace();
        } finally{
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // chack the "email_verified" and "email" values in token payload
    private static boolean verifyEmail(final Map<String,String> tokenPayload){
        if(tokenPayload.get("email_verified")!=null && tokenPayload.get("email")!=null){
            try{
                return Boolean.valueOf(tokenPayload.get("email_verified")) && tokenPayload.get("email").contains("@gmail.");
            }catch(Exception e){
                System.out.println("\n\n\tCheck emailVerified failed - cannot parse "+tokenPayload.get("email_verified")+" to boolean\n");
            }
        }else{
            System.out.println("\n\n\tCheck emailVerified failed - required information missing in the token");
        }
        return false;
    }

    // check token expiration is after now :
    private static boolean checkExpirationTime(final Map<String,String> tokenPayload){
        try{
            if(tokenPayload.get("exp")!=null){
                // the "exp" value is in seconds and Date().getTime is in mili seconds
                return Long.parseLong(tokenPayload.get("exp")+"000") > new java.util.Date().getTime();
            }else{
                System.out.println("\n\n\tCheck expiration failed - required information missing in the token\n");
            }
        }catch(Exception e){
            System.out.println("\n\n\tCheck expiration failed - cannot parse "+tokenPayload.get("exp")+" into long\n");
        }
        return false;
    }

    // check that at least one CLIENT_ID matches with token values
    private static boolean checkAudience(final Map<String,String> tokenPayload){
        if(tokenPayload.get("aud")!=null && tokenPayload.get("azp")!=null){
            List<String> pom = Arrays.asList("MY_CLIENT_ID_1",
                    "MY_CLIENT_ID_2",
                    "MY_CLIENT_ID_3");

            if(pom.contains(tokenPayload.get("aud")) || pom.contains(tokenPayload.get("azp"))){
                return true;
            }else{
                System.out.println("\n\n\tCheck audience failed - audiences differ\n");
                return false;
            }
        }
        System.out.println("\n\n\tCheck audience failed - required information missing in the token\n");
        return false;
    }

    // verify google token payload :
    private static boolean doTokenVerification(final Map<String,String> tokenPayload){
        if(tokenPayload!=null){
            return verifyEmail(tokenPayload) // check that email address is verifies
                    && checkExpirationTime(tokenPayload) // check that token is not expired
                    && checkAudience(tokenPayload) // check audience
                    ;
        }
        return false;
    }
}
