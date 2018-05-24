package com.edeqa.helpers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created 9/17/2017.
 */
public class MiscTest {
    private final static Logger LOGGER = Logger.getLogger(MiscTest.class.getName());

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void constructor() throws Exception {
        Misc misc = new Misc();
    }

    @Test
    public void getEncryptedHash() throws Exception {
        assertEquals("033bd94b1168d7e4f0d644c3c95e35bf", Misc.getEncryptedHash("TEST"));
    }

    @Test
    public void getEncryptedHash1() throws Exception {
        assertEquals("984816fd329622876e14907634264e6f332e9fb3", Misc.getEncryptedHash("TEST", 1));
        assertEquals("033bd94b1168d7e4f0d644c3c95e35bf", Misc.getEncryptedHash("TEST", 5));
        assertEquals("94ee059335e587e501cc4bf90613e0814f00a7b08bc7c648fd865a2af6a22cc2", Misc.getEncryptedHash("TEST", 256));
        assertEquals("4f37c49c0024445f91977dbc47bd4da9c4de8d173d03379ee19c2bb15435c2c7e624ea42f7cc1689961cb7aca50c7d17", Misc.getEncryptedHash("TEST", 384));
        assertEquals("7bfa95a688924c47c7d22381f20cc926f524beacb13f84e203d4bd8cb6ba2fce81c57a5f059bf3d509926487bde925b3bcee0635e4f7baeba054e5dba696b2bf", Misc.getEncryptedHash("TEST", 512));
        assertEquals("7bfa95a688924c47c7d22381f20cc926f524beacb13f84e203d4bd8cb6ba2fce81c57a5f059bf3d509926487bde925b3bcee0635e4f7baeba054e5dba696b2bf", Misc.getEncryptedHash("TEST", 12516217));
    }


//    @Test
//    public void getUrl() throws Exception {
//        String res = Misc.getUrl("http://echo.jsontest.com/one/two");
//        LOGGER.info(res);
//        JSONObject json = new JSONObject(res);
//        assertEquals("two", json.getString("one"));
//    }
//
//    @Test
//    public void getUrl1() throws Exception {
//        String res = Misc.getUrl("http://echo.jsontest.com/one/two", "UTF-8");
//        LOGGER.info(res);
//        JSONObject json = new JSONObject(res);
//        assertEquals("two", json.getString("one"));
//    }
//
//    @Test
//    public void getUrl2() throws Exception {
//        String res = Misc.getUrl("http://echo.jsontest.com", "{\"one\":\"two\"}", "UTF-8");
//        LOGGER.info(res);
//        JSONObject json = new JSONObject(res);
//        assertEquals("{\"\": \"\"}", res);
//
//        try {
//            res = Misc.getUrl("https://api.ipify.org/?format=json", "{\"one\":\"two\"}", "UTF-8");
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void getUnique() throws Exception {
        LOGGER.info(Misc.getUnique());
        assertTrue(Misc.getUnique() != null && Misc.getUnique().length() > 0);
    }

    @Test
    public void pause() throws Exception {
        long time = new Date().getTime();
        Misc.pause(2);
        assertTrue(new Date().getTime() - time >= 2000);
    }

    @Test
    public void join() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        assertEquals("A, B, C", Misc.join(", ", list));

    }

    @Test
    public void distanceToString() throws Exception {
        assertEquals("2 ft", Misc.distanceToString(.5));
        assertEquals("49 ft", Misc.distanceToString(15));
        assertEquals("7.7 mi", Misc.distanceToString(12345));

        Locale.setDefault(Locale.ENGLISH);
        assertEquals("500 mm", Misc.distanceToString(0.5));
        assertEquals("15.0 m", Misc.distanceToString(15));
        assertEquals("12.3 km", Misc.distanceToString(12345));
    }

    @Test
    public void durationToString() throws Exception {
        assertEquals("1d 10h 18m", Misc.durationToString(123456789));
        assertEquals("17425d 3h 55m", Misc.durationToString(1505534126840L));
        assertEquals("5s", Misc.durationToString(5000L));
        assertEquals("10d", Misc.durationToString(863995000L));
    }

    @Test
    public void isEmpty() throws Exception {
        String emptyString = "";
        Object nullObject = null;
        Map emptyMap = new HashMap();
        Boolean falseBoolean = false;
        Integer zeroInteger = 0;
        Long zeroLong = 0L;
        Float zeroFloat = 0.F;
        Double zeroDouble = 0.D;

        String notEmptyString = "not-empty";
        Object notNullObject = "not-null-object";
        Map<String,String> notEmptyMap = new HashMap<>();notEmptyMap.put("a","b");
        Boolean notFalseBoolean = true;
        Integer notZeroInteger = 1;
        Long notZeroLong = 1L;
        Float notZeroFloat = 1.F;
        Double notZeroDouble = 1.D;

        assertTrue(Misc.isEmpty(emptyString));
        //noinspection ConstantConditions
        assertTrue(Misc.isEmpty(nullObject));
        assertTrue(Misc.isEmpty(emptyMap));
        //noinspection ConstantConditions
        assertTrue(Misc.isEmpty(falseBoolean));
        assertTrue(Misc.isEmpty(zeroInteger));
        assertTrue(Misc.isEmpty(zeroLong));
        assertTrue(Misc.isEmpty(zeroFloat));
        assertTrue(Misc.isEmpty(zeroDouble));

        assertTrue(!Misc.isEmpty(notEmptyString));
        assertTrue(!Misc.isEmpty(notNullObject));
        assertTrue(!Misc.isEmpty(notEmptyMap));
        //noinspection ConstantConditions
        assertTrue(!Misc.isEmpty(notFalseBoolean));
        assertTrue(!Misc.isEmpty(notZeroInteger));
        assertTrue(!Misc.isEmpty(notZeroLong));
        assertTrue(!Misc.isEmpty(notZeroFloat));
        assertTrue(!Misc.isEmpty(notZeroDouble));

    }

    @Test
    public void toStringDeep() throws Exception {
        class TestObject {
            private Map<String,String> privateMap = new HashMap<>();
            public Map<String,String> publicMap = new HashMap<>();
            private List<String> privateList = new ArrayList<>();
            public List<String> publicList = new ArrayList<>();
            private String privateString = "private string";
            public String publicString = "public string";
            private void privateTestMethod() {}
            public void publicTestMethod() {}
            private String getPrivateString() {return privateString;}
            public String getPublicString() {return publicString;}
            TestObject(){
                privateMap.put("map key 1", "map value 1");
                publicMap.put("map key 2", "map value 2");
                privateList.add("list value 1");
                publicList.add("list value 2");
            }
        }
        class ChildTestObject extends TestObject {
            private Map<String,String> privateChildMap = new HashMap<>();
            public Map<String,String> publicChildMap = new HashMap<>();
            private List<String> privateChildList = new ArrayList<>();
            public List<String> publicChildList = new ArrayList<>();
            private String privateChildString = "private child string";
            public String publicChildString = "public child string";
            private void privateChildTestMethod() {}
            public void publicChildTestMethod() {}
            private String getPrivateChildString() {return privateChildString;}
            public String getPublicChildString() {return publicChildString;}
            private ChildTestObject(){
                super();
                privateChildMap.put("map key 3", "map value 3");
                publicChildMap.put("map key 4", "map value 4");
                privateChildList.add("list value 3");
                publicChildList.add("list value 4");
            }
        }


        TestObject testObject = new ChildTestObject();
        LOGGER.info(Misc.toStringDeep(testObject));

    }

    @Test
    public void log() {
        Misc.log(new String(), "test", 1, true, null, new HashMap(), new Misc());
        Misc.log(new Misc());
        Misc.log("test", 1, true, null, new HashMap());
    }

    @Test
    public void err() {
        Misc.err(new String(), new Throwable("Test throwable"), "test", 1, true, null, new HashMap(), new Misc());
        Misc.err(new Misc());
        Misc.err("test", new Throwable("Test throwable"), "test", 1, true, null, new HashMap());
    }
}