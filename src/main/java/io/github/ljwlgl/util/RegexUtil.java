package io.github.ljwlgl.util;

import java.util.regex.Pattern;

/**
 * @author zqgan
 * @since 2018/11/2
 **/

public class RegexUtil {

    /**
     * 字符串是否只是由数字组成
     * @param str 检测字符
     * @return bool
     */
    public static boolean isNumerice(String str) {
        Pattern pattern = Pattern.compile("[0-9.]*");
        return pattern.matcher(str).matches();
    }

}
