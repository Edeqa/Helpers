package com.edeqa.helpers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created 10/18/16.
 */

@SuppressWarnings({"HardCodedStringLiteral", "unused", "WeakerAccess"})
public class HtmlGenerator {

    public static final String META = "meta";
    public static final String HEAD = "head";
    public static final String HTML = "html";
    public static final String BUTTON = "button";
    public static final String BODY = "body";
    public static final String LANG = "lang";
    public static final String STYLE = "style";
    public static final String CLASS = "class";
    public static final String DIV = "div";
    public static final String SPAN = "span";
    public static final String IMG = "img";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String SCRIPT = "script";
    public static final String NOSCRIPT = "noscript";
    public static final String TITLE = "title";
    public static final String ID = "id";
    public static final String SRC = "src";
    public static final String HTTP_EQUIV = "http-equiv";
    public static final String CONTENT = "content";
    public static final String SIZES = "sizes";
    public static final String ONCLICK = "onclick";
    public static final String TABINDEX = "tabindex";
    public static final String TABLE = "table";
    public static final String TR = "tr";
    public static final String TH = "th";
    public static final String TD = "td";
    public static final String H1 = "h1";
    public static final String H2 = "h2";
    public static final String H3 = "h3";
    public static final String H4 = "h4";
    public static final String H5 = "h5";
    public static final String H6 = "h6";
    public static final String H7 = "h7";
    public static final String BORDER = "border";
    public static final String COLSPAN = "colspan";
    public static final String ROWSPAN = "rowspan";
    public static final String A = "a";
    public static final String HREF = "href";
    public static final String TARGET = "target";
    public static final String SMALL = "small";
    public static final String LINK = "link";
    public static final String REL = "rel";
    public static final String STYLESHEET = "stylesheet";
    public static final String TYPE = "type";
    public static final String BR = "br";
    public static final String ONLOAD = "onload";

    public static final String FORM = "form";
    public static final String NAME = "name";
    public static final String INPUT = "input";
    public static final String SUBMIT = "submit";
    public static final String TEXT = "text";
    public static final String VALUE = "value";

    public static final String MANIFEST = "manifest";
    public static final String ASYNC = "async";
    public static final String DEFER = "defer";


    ArrayList<String> notClosableTags = new ArrayList<>(Arrays.asList(BR,META,INPUT));
    private Tag body;
    private Tag head;
    private int level = 0;
    Map<String,String> properties = new HashMap<>();

    public HtmlGenerator() {
        head = new Tag(HEAD);
        body = new Tag(BODY);
        with(LANG, "en");
    }

    public Tag getHead(){
        return head;
    }

    public Tag getBody(){
        return body;
    }

    public String build(){
        StringBuilder buf = new StringBuilder();

        buf.append("<!DOCTYPE html>\n");
        ArrayList<String> parts = new ArrayList<>();
        parts.add(HTML);
        for(Map.Entry<String,String> entry: properties.entrySet()){
            if(entry.getValue() != null && entry.getValue().length() > 0) {
                if(LANG.equals(entry.getKey())) {
                    parts.add("lang=\"" + entry.getValue() + "\" xml:lang=\"" + entry.getValue() + "\" xmlns=\"http://www.w3.org/1999/xhtml\"");
                } else {
                    parts.add(entry.getKey() + "=\"" + entry.getValue() + "\"");
                }
            } else {
                parts.add(entry.getKey());
            }
        }
        buf.append("<").append(Misc.join(" ", parts)).append(">");
        buf.append(head.build());
        buf.append(body.build());
        buf.append("</html>");
        return buf.toString();
    }

    public void clear(){
        head = new Tag(HEAD);
        body = new Tag(BODY);
        properties.clear();
        with(LANG, "en");
    }

    public HtmlGenerator with(String key,String value){
        properties.put(key,value);
        return this;
    }

    public class Tag {
        String tag;
        ArrayList<Object> inner = new ArrayList<>();
        Map<String,String> properties = new HashMap<>();

        public Tag(String tag){
            this.tag = tag;
        }

        public Tag add(String type){
            Tag n = new Tag(type);
            inner.add(n);
            return n;
        }

        public String build(){
            StringBuilder buf = new StringBuilder();
            buf.append("\n");
            for(int i=0;i<level;i++) buf.append("   ");

            buf.append("<").append(tag);

            if(!properties.isEmpty()){
                for(Map.Entry<String,String> x:properties.entrySet()){
                    String key = x.getKey();
                    String value = x.getValue();
                    key = key.replaceAll("\"","&quot;");
                    value = value.replaceAll("\"","&quot;");

                    if(ASYNC.equals(key) || DEFER.equals(key)) {
                        if("true".equals(value)) {
                            buf.append(" ").append(key);
                        }
                    } else {
                        buf.append(" ").append(key).append("=\"").append(value).append("\"");
                    }
                }
            }

            buf.append(">");
            boolean indent = false;
            for(Object x:inner){
                if(x instanceof Tag) {
                    indent = true;
                    level ++;
                    buf.append(((Tag)x).build());
                    level --;
                } else if(x instanceof String){
                    buf.append(x);
                }
            }
            if(indent) {
                buf.append("\n");
                for (int i = 0; i < level; i++) buf.append("   ");
            }
            if(!notClosableTags.contains(tag)) {
                buf.append("</").append(tag).append(">");
            }
            return buf.toString();
        }

        public Tag with(String key,String value){
            properties.put(key,value);
            return this;
        }

        public Tag with(String key,boolean value){
            properties.put(key,"" + value);
            return this;
        }

        public Tag with(String key,int value){
            properties.put(key,String.valueOf(value));
            return this;
        }

        public Tag with(String key,JSONObject value){
            inner.add("var " + key + " = " + value.toString(4) + ";");
            return this;
        }

        public Tag with(String key,JSONArray value){
            inner.add("var " + key + " = " + value.toString(4) + ";");
            return this;
        }

        public Tag with(String text){
            inner.add(text);
            return this;
        }

        public Tag with(Number number){
            inner.add(number.toString());
            return this;
        }
    }

}
