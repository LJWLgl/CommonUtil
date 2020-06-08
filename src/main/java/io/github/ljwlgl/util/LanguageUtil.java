package io.github.ljwlgl.util;

import java.util.regex.Pattern;

/**
 * @author zq_gan
 * @since 2020/5/21
 **/

public class LanguageUtil {


    /**
     * 是否只有数字
     */
    public static boolean isNumeric(String str) {
        return str != null && str.length() > 0 && Pattern.matches("[0-9]*", str);
    }

    /**
     * 是否只有字母
     */
    public static boolean isOnlyLetter(String str) {
        return str != null && str.length() > 0 && Pattern.matches("^[A-Za-z]+$", str);
    }

    /**
     * 是否只有字母和空格
     */
    public static boolean isLetter(String str) {
        return str != null && str.length() > 0 && Pattern.matches("^[A-Za-z\\u0020]+$", str);
    }

    /**
     * 是否只有汉字和空格
     */
    public static boolean isChinese(String str) {
        return str != null && str.length() > 0 && Pattern.matches("^[\\u4e00-\\u9fa5\\u0020]+$", str);
    }

    /**
     * 是否只有英文和数字
     */
    public static boolean isLetterAndNumber(String str) {
        return str != null && str.length() > 0 && Pattern.matches("^[0-9A-Za-z\\u0020]+$", str);
    }

    /**
     * 是否只有中文和数字
     */
    public static boolean isChineseAndNumber(String str) {
        return str != null && str.length() > 0 && Pattern.matches("^[0-9\\u4e00-\\u9fa5\\u0020]+$", str);
    }

    /**
     * 是否只有韩文和数字
     */
    public static boolean isKoreanAndNumber(String str) {
        return str != null && str.length() > 0 && Pattern.matches("^[0-9\\uac00-\\ud7a3\\u0020]+$", str);
    }

    /**
     * 是否只有日文和数字
     */
    public static boolean isJapanAndNumber(String str) {
        return str != null && str.length() > 0 && Pattern.matches("^[0-9\\u0800-\\u4e00\\u0020]+$", str);
    }

}
