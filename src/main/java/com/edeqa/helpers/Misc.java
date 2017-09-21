/*
 * Some simple helpers <http://waytous.net>
 * Copyright (C) Edeqa LLC <http://www.edeqa.com>
 *
 * Created 9/16/2017.
 */
package com.edeqa.helpers;

import com.google.common.net.HttpHeaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class Misc {
    public static final int DIGEST_METHOD_MD2 = 2;
    public static final int DIGEST_METHOD_MD5 = 5;
    public static final int DIGEST_METHOD_SHA1 = 1;
    public static final int DIGEST_METHOD_SHA256 = 256;
    public static final int DIGEST_METHOD_SHA512 = 512;

    public static String getEncryptedHash(String str) {
        return getEncryptedHash(str, 5);
    }

    public static String getEncryptedHash(String str, int type) {
        String sType;
        switch (type) {
            case 1:
                sType = "SHA-1";
                break;
//            case 2:
//                sType = "MD2";
//                break;
            case 5:
                sType = "MD5";
                break;
            case 256:
                sType = "SHA-256";
                break;
            case 384:
                sType = "SHA-384";
                break;
            case 512:
                sType = "SHA-512";
                break;
            default:
                sType = "SHA-512";
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(sType);
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] bytes = messageDigest.digest();
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes) {
                buffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUnique() {
        return new BigInteger(48, new SecureRandom()).toString(32).toUpperCase();
    }

    public static void pause(int i) {
        try {
            Thread.sleep(i*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String join(String conjunction, List<String> list) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String item : list) {
            if (first)
                first = false;
            else
                sb.append(conjunction);
            sb.append(item);
        }
        return sb.toString();
    }

    public static String getUrl(String url, String post, String urlCharset) throws IOException {

        if(urlCharset == null) urlCharset = "UTF-8";

        URL obj = new URL(url);
        HttpURLConnection con;
        if(url.toLowerCase().startsWith("https://")) {
            con = (HttpsURLConnection) obj.openConnection();
        } else {
            con = (HttpURLConnection) obj.openConnection();
        }

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty(HttpHeaders.USER_AGENT,
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; ru; rv:1.8.1.12) Gecko/20080201 Firefox");
//        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/json");
        con.setRequestProperty(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
        outputStreamWriter.write(URLEncoder.encode(post,urlCharset));
        outputStreamWriter.flush();
        outputStreamWriter.close();

        int responseCode = con.getResponseCode();
//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();
    }

    public static String getUrl(String url) throws IOException {
        return getUrl(url, "UTF-8");
    }

    public static String getUrl(String url, String urlCharset) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        InputStream in;
        URLConnection feedUrl;
        feedUrl = new URL(url).openConnection();
        feedUrl.setConnectTimeout(5000);
        feedUrl.setRequestProperty(HttpHeaders.USER_AGENT,
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; ru; rv:1.8.1.12) Gecko/20080201 Firefox");
        feedUrl.setRequestProperty(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

        in = feedUrl.getInputStream();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in, urlCharset))) {
            while ((line = reader.readLine()) != null) {
                sb.append(new String(line.getBytes("UTF-8"))).append("\n");
            }
        }
        in.close();

        return sb.toString();
    }

    public static String distanceToString(double meters) {
        if(Locale.US.equals(Locale.getDefault())) {
            meters = meters * 3.2808399;
            if(meters < 530) {
                return String.format("%.0f %s", meters, "ft");
            } else {
                meters = meters / 5280;
                return String.format("%.1f %s", meters, "mi");
            }
        } else {
            String unit = "m";
            if (meters < 1) {
                meters *= 1000;
                unit = "mm";
                return String.format("%.0f %s", meters, unit);
            } else if (meters > 1000) {
                meters /= 1000;
                unit = "km";
                return String.format("%.1f %s", meters, unit);
            } else {
                return String.format("%.1f %s", meters, unit);
            }
        }
    }

    public static String durationToString(long millis) {
        StringBuilder res = new StringBuilder();

        int days = (int) (millis / (24 * 60 * 60 * 1000));
        millis = millis - days * (24 * 60 * 60 * 1000L);

        int hours = (int) (millis / (60 * 60 * 1000L));
        millis = millis - hours * (60 * 60 * 1000L);

        int minutes = (int) (millis / (60 * 1000L));
        millis = millis - minutes * (60 * 1000L);

        int seconds = (int) (millis / (1000L));
        millis = millis - seconds * (1000L);

        if(days > 0 || hours > 0) {
            if(seconds > 30) {
                minutes ++;
            }
            seconds = 0;
        }
        if(minutes > 59) {
            hours ++;
            minutes = 0;
        }
        if(hours > 23) {
            days ++;
            hours = 0;
        }

        if(days > 0) {
            res.append(days + "d");
        }
        if(hours > 0) {
            if(res.length() > 0) res.append(" ");
            res.append(hours + "h");
        }
        if(minutes > 0) {
            if(res.length() > 0) res.append(" ");
            res.append(minutes + "m");
        }
        if(seconds > 0) {
            if(res.length() > 0) res.append(" ");
            res.append(seconds + "s");
        }

        return res.toString();
    }
}
