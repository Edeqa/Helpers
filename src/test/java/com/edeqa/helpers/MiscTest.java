package com.edeqa.helpers;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created 9/17/2017.
 */
public class MiscTest {

    private final static Logger LOGGER = Logger.getLogger(MiscTest.class.getName());

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getEncryptedHash() throws Exception {
        Assert.assertEquals("033bd94b1168d7e4f0d644c3c95e35bf", Misc.getEncryptedHash("TEST"));
    }

    @Test
    public void getEncryptedHash1() throws Exception {
        Assert.assertEquals("984816fd329622876e14907634264e6f332e9fb3", Misc.getEncryptedHash("TEST", 1));
        Assert.assertEquals("033bd94b1168d7e4f0d644c3c95e35bf", Misc.getEncryptedHash("TEST", 5));
        Assert.assertEquals("94ee059335e587e501cc4bf90613e0814f00a7b08bc7c648fd865a2af6a22cc2", Misc.getEncryptedHash("TEST", 256));
        Assert.assertEquals("4f37c49c0024445f91977dbc47bd4da9c4de8d173d03379ee19c2bb15435c2c7e624ea42f7cc1689961cb7aca50c7d17", Misc.getEncryptedHash("TEST", 384));
        Assert.assertEquals("7bfa95a688924c47c7d22381f20cc926f524beacb13f84e203d4bd8cb6ba2fce81c57a5f059bf3d509926487bde925b3bcee0635e4f7baeba054e5dba696b2bf", Misc.getEncryptedHash("TEST", 512));
    }


    @Test
    public void getUrl() throws Exception {
        String res = Misc.getUrl("https://www.waytous.net/rest/v1/getVersion");
        LOGGER.info(res);
        JSONObject json = new JSONObject(res);
        Assert.assertEquals("success", json.getString("status"));
    }

    @Test
    public void getUrl1() throws Exception {
        String res = Misc.getUrl("https://www.waytous.net/rest/v1/getVersion", "CP-1251");
        LOGGER.info(res);
        JSONObject json = new JSONObject(res);
        Assert.assertEquals("success", json.getString("status"));
    }

    @Test
    public void getUnique() throws Exception {
        LOGGER.info(Misc.getUnique());
        Assert.assertTrue(Misc.getUnique() != null && Misc.getUnique().length() > 0);
    }

    @Test
    public void pause() throws Exception {

    }

    @Test
    public void join() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        Assert.assertEquals("A, B, C", Misc.join(", ", list));

    }

    @Test
    public void formatLengthToLocale() throws Exception {
        Assert.assertEquals(" 7.7 mi", Misc.formatLengthToLocale(12345));
    }

    @Test
    public void toDateString() throws Exception {
        Assert.assertEquals("1d 10h 18m", Misc.toDateString(123456789));
    }

}