package com.edeqa.helpers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created 9/18/2017.
 */
public class HtmlGeneratorTest {


    private HtmlGenerator html;

    @Before
    public void setUp() throws Exception {
        html = new HtmlGenerator();
    }

    @Test
    public void getHead() throws Exception {
        Assert.assertEquals("\n<head></head>", html.getHead().build());
    }

    @Test
    public void getBody() throws Exception {
        Assert.assertEquals("\n<body></body>", html.getBody().build());
    }

    @Test
    public void build() throws Exception {
        Assert.assertEquals("<!DOCTYPE html>\n" +
                "<html lang=\"en\" xml:lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head></head>\n" +
                "<body></body></html>", html.build());
    }

    @Test
    public void clear() throws Exception {
        html.getBody().with(HtmlGenerator.DIV, "123");
        Assert.assertEquals("\n<body div=\"123\"></body>", html.getBody().build());
        html.clear();
        Assert.assertEquals("\n<body></body>", html.getBody().build());
    }

    @Test
    public void with() throws Exception {
        html.with(HtmlGenerator.META, "test");
        html.with(HtmlGenerator.REL, null);
        html.getBody().add(HtmlGenerator.DIV).with(HtmlGenerator.ID, "test");
        html.getBody().add(HtmlGenerator.DIV).with(5);
        html.getBody().add(HtmlGenerator.DIV).with(HtmlGenerator.DIV, true);
        html.getBody().add(HtmlGenerator.DIV).with(HtmlGenerator.DIV, 123);
        html.getBody().add(HtmlGenerator.DIV).with(HtmlGenerator.DIV);

        JSONArray a = new JSONArray();a.put(true);
        JSONObject o = new JSONObject();o.put("value", true);

        html.getBody().add(HtmlGenerator.DIV).with(HtmlGenerator.DIV, a);
        html.getBody().add(HtmlGenerator.DIV).with(HtmlGenerator.DIV, o);

        Assert.assertEquals("<!DOCTYPE html>\n" +
                "<html meta=\"test\" rel lang=\"en\" xml:lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head></head>\n" +
                "<body>\n" +
                "   <div id=\"test\"></div>\n" +
                "   <div>5</div>\n" +
                "   <div div=\"true\"></div>\n" +
                "   <div div=\"123\"></div>\n" +
                "   <div>div</div>\n" +
                "   <div>var div = [true];</div>\n" +
                "   <div>var div = {\"value\": true};</div>\n" +
                "</body></html>", html.build());
    }


}