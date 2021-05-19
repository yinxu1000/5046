package com.example.fit5046;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class SearchFatSecretAPI {
    private static final String oauth_consumer_key = "b4d6bfadfa2948928d8f68e6b6548e6c";
    private static final String oauth_signature_method ="HMAC-SHA1";
    private static final String oauth_timestamp= Long.valueOf(System.currentTimeMillis() * 2).toString();
    private static final String oauth_version="1.0";
    private static final String Consumer_Secret="ea75c3d0f8f34ee7bb94a8b1fbd47fdd";
    private static final String Request_URL = "http://platform.fatsecret.com/rest/server.api";
    private static final String HTTP_Method = "GET";

    private static String getOauth_signature(String method, String uri, String[] params) {
        String[] p = {method, Uri.encode(uri), Uri.encode(normalizeParameters(params))};
        Log.i("mytagpar",normalizeParameters(params));
        String s = join(p, "&");
        Log.i("mytags",s);
        String newKey= Consumer_Secret+"&";
        SecretKey sk = new SecretKeySpec(newKey.getBytes(), oauth_signature_method);
        try {
            Mac m = Mac.getInstance(oauth_signature_method);
            m.init(sk);
            return Uri.encode(new String(Base64.encode(m.doFinal(s.getBytes()), Base64.DEFAULT)).trim());
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.w("FatSecret_TEST FAIL", e.getMessage());
            return null;
        } catch (java.security.InvalidKeyException e) {
            Log.w("FatSecret_TEST FAIL", e.getMessage());
            return null;
        }
    }

    public String searchFood(String searchFood, int page) {
        List<String> params = new ArrayList<>(Arrays.asList(generateOauthParams(page)));
        String[] template = new String[1];
        params.add("method=foods.search");
        params.add("search_expression=" + Uri.encode(searchFood));
        params.add("oauth_signature=" + getOauth_signature(HTTP_Method, Request_URL, params.toArray(template)));

        JSONObject foods = null;
        try {
            URL url = new URL(Request_URL + "?" + normalizeParameters(params.toArray(template)));
            URLConnection api = url.openConnection();
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(api.getInputStream()));
            while ((line = reader.readLine()) != null) builder.append(line);
            JSONObject food = new JSONObject(builder.toString());
            foods = food.getJSONObject("foods");
        } catch (Exception exception) {
            Log.e("FatSecret Error", exception.toString());
            exception.printStackTrace();
        }

     return foods.toString();


    }

    private static String[] generateOauthParams(int i) {
        return new String[]{
                "oauth_consumer_key=" + oauth_consumer_key,
                "oauth_signature_method="+oauth_signature_method,
                "oauth_timestamp=" +oauth_timestamp,
                "oauth_nonce=" + nonce(),
                "oauth_version="+oauth_version,
                "format=json",
                "page_number=" + i,
                "max_results=" + 20};
    }



    private static String normalizeParameters(String[] params) {
        String[] p = Arrays.copyOf(params, params.length);
        Arrays.sort(p);
        return join(p, "&");
    }

    private static String join(String[] array, String separator) {
        String b = "";
        for (int i = 0; i < array.length; i++) {
            if (i > 0)
                b+=separator;
            b+=array[i];
        }
        return b;
    }

    private static String nonce() {
        Random r = new Random();
        StringBuilder n = new StringBuilder();
        for (int i = 0; i < r.nextInt(8) + 2; i++)
            n.append(r.nextInt(26) + 'a');
        return n.toString();
    }


    public static String getSnippet(String result) {
        Log.i("mytagResult",result);
        String snippet = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("food");
            if (jsonArray != null && jsonArray.length() > 0) {
                snippet = jsonArray.getJSONObject(0).getString("food_description");

            }
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }

        return snippet;
    }



}
