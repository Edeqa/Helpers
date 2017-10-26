/*
 * Some simple helpers <http://waytous.net>
 * Copyright (C) Edeqa LLC <http://www.edeqa.com>
 *
 * Created 9/16/2017.
 */
package com.edeqa.helpers;

import com.google.common.net.HttpHeaders;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    /**
     * Checks the object for empty state. This means one of following:
     * <ul>
     * <li>null
     * <li>false
     * <li>"" (empty string)
     * <li>0
     * <li>0L
     * <li>0.0F
     * <li>0.0D
     * <li>empty map
     * <li>empty list
     * </ul>
     * @param object any object
     * @return
     */
    public static boolean isEmpty(Object object) {
        if(object == null) return true;
        if(object instanceof Boolean && !((Boolean) object)) return true;
        if(object instanceof String && ((String) object).length() == 0) return true;
        if(object instanceof Integer && Integer.valueOf(object.toString()) == 0) return true;
        if(object instanceof Long && Long.valueOf(object.toString()) == 0L) return true;
        if(object instanceof Float && Float.valueOf(object.toString()) == 0F) return true;
        if(object instanceof Double && Double.valueOf(object.toString()) == 0D) return true;
        if(object instanceof Map && ((Map) object).size() == 0) return true;
        if(object instanceof List && ((List) object).size() == 0) return true;
        return false;
    }

    /**
     * Returns string that represents the deep structure of object including public maps and lists. Also public methods are listed.
     * @param object any object
     * @return string
     */
    public static String toStringDeep(Object object) {
        if(object == null) return "null";
        String res = "toStringDeep: " + object.getClass().getCanonicalName();
        Map<String, Boolean> own = new HashMap();
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            String respart = "";
            if (fields.length > 0) {
                for(Field field : fields) {
                    if (!Modifier.isPublic(field.getModifiers())) continue;
                    Object value = field.get(object);
                    own.put(field.getName(), true);
                    if (value != null && value instanceof Map) {
                        respart += field.getName() + ": " + value;
                    } else if (value != null && value instanceof List) {
                        respart += field.getName() + ": " + value;
                    } else {
                        respart += field.getName() + ": " + value;
                    }
                    respart += ", ";
                }
                if(respart.length() > 0) {
                    res += "\n>> Own variables: {" + respart + "}";
                }
            }
            fields = object.getClass().getFields();
            if (fields.length > 0) {
                respart = "";
                for(Field field : fields) {
                    if (own.containsKey(field.getName()) || !Modifier.isPublic(field.getModifiers())) continue;
                    Object value = field.get(object);
                    if (value != null && value instanceof Map) {
                        respart += field.getName() + ": " + value;
                    } else if (value != null && value instanceof List) {
                        respart += field.getName() + ": " + value;
                    } else {
                        respart += field.getName() + ": " + value;
                    }
                    respart += ", ";
                }
                if(respart.length() > 0) {
                    res += "\n>> Super variables: {" + respart + "}";
                }
            }
            Method[] methods = object.getClass().getDeclaredMethods();
            own.clear();
            if (methods.length > 0) {
                respart = "";
                for(Method method : methods) {
                    if (!Modifier.isPublic(method.getModifiers())) continue;
                    own.put(method.getName(), true);
                    respart += method.getName() + ", ";
                }
                if(respart.length() > 0) {
                    res += "\n>> Own methods: [" + respart + "]";
                }
            }
            methods = object.getClass().getMethods();
            List<Method> getters = new ArrayList();
            if (methods.length > 0) {
                respart = "";
                for(Method method : methods) {
                    if ((method.getName().startsWith("get") || method.getName().startsWith("is")) && method.getParameterTypes().length == 0) getters.add(method);
                    if ("equals".equals(method.getName()) || "getClass".equals(method.getName())
                            || "hashCode".equals(method.getName()) || "notify".equals(method.getName())
                            || "notifyAll".equals(method.getName()) || "wait".equals(method.getName())) continue;
                    if (own.containsKey(method.getName()) || !Modifier.isPublic(method.getModifiers())) continue;
                    respart += method.getName() + ", ";
                }
                if(respart.length() > 0) {
                    res += "\n>> Super methods: [" + respart + "]";
                }
            }
            if(getters.size() > 0) {
                respart = "";
                for(Method method: getters) {
                    if("getClass".equals(method.getName())) continue;
                    Object value = method.invoke(object);
                    if(value != null && value instanceof String && value.toString().length() > 200) value = "[String " + value.toString().length() + " byte(s)]";
                    else if(value != null && value.toString().length() > 1024) value = "[" + value.getClass().getSimpleName() + " " + value.toString().length() + " byte(s)]";
                    respart += method.getName() + ": " + value + ", ";
                }
                if(respart.length() > 0) {
                    res += "\n>> Getters: {" + respart + "}";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
