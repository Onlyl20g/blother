package com.jinke.project.tool.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {

    final static String regEx="[`~!@#$%^&*()+-=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    public static String replaceStr(String str) {
        Pattern compile = Pattern.compile(regEx);
        Matcher matcher = compile.matcher(str);
        String trim = matcher.replaceAll("").trim();
        trim = trim.replaceAll(" ", "");
        return trim;
    }

    public static boolean testStr(String str) {
        Pattern compile = Pattern.compile(regEx);
        Matcher matcher = compile.matcher(str);
        return matcher.matches();
    }
}
