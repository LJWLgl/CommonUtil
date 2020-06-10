package io.github.ljwlgl.util;

import java.util.UUID;

/**
 * @author ：lzz
 * @BelongsProject: io.github.ljwlgl.util
 * @date ：Created in 2020/6/9 15:52
 * @description ：
 * @modified By：
 */
public class StringUtil {
    /**
     * 判断字符是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmptyOrNull(String str) {

        return str == null || str.length() == 0 || str.trim().length() == 0;
    }

    /**
     * 位数不足补0
     *
     * @param str
     * @param strLength
     * @return
     */

    public static String appendZero(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);// 左补0
            // sb.append(str).append("0");//右补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    /**
     * @Description: 转为double类型 ，如果obj为null或者空字符串或者格式不对则返回defaultValue
     * @Param: [obj, defaultValue]
     * @return: String obj为null或者空字符串或者格式不对返回defaultValue
     */
    public static double castDouble(Object obj, double defaultValue) {
        double value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (!StringUtil.isEmptyOrNull(strValue)) {
                try {
                    value = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }

            }
        }
        return value;
    }

    /**
     * 转为int型(提供默认值)
     *
     * @param obj
     * @param defaultValue
     * @return 如果obj为null或者空字符串或者格式不对则返回defaultValue
     */
    public static int castInt(Object obj, int defaultValue) {
        int value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (!StringUtil.isEmptyOrNull(strValue)) {
                try {
                    value = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }

            }
        }
        return value;
    }


    /**
     * 获取一段唯一的字符串
     *
     * @return
     */
    public static String getKey() {
        return UUID.randomUUID().toString();
    }

    public static String replaceString(String str, String oldString, String newString) {
        if (StringUtil.isEmptyOrNull(str)) return "";
        return str.replaceAll(oldString, newString);


    }

    private static String castString(Object obj) {
        return StringUtil.castString(obj, "");
    }

    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

}
