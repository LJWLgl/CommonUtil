package io.github.ljwlgl.util;

import java.util.Map;
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
        StringBuilder sb = null;
        while (strLen < strLength) {
            sb = new StringBuilder();
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

    /**
     * 批量替换字符
     * @param str str
     * @param oldNewMap 输入字符串
     * @return 新的字符串
     */
    public static String replaceString(String str, Map<String, String> oldNewMap) {
        if (oldNewMap == null || oldNewMap.size() == 0) {
            return str;
        }
        String newStr = str;
        for (Map.Entry<String, String> entry : oldNewMap.entrySet()) {
            newStr = newStr.replace(entry.getKey(), entry.getValue());
        }
        return newStr;
    }

    private static String castString(Object obj) {
        return StringUtil.castString(obj, "");
    }

    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 将字符数组的子集合成新的字符串
     * @param i 子集的开始id
     * @param j 子集的结束id
     * @param arr 字符数组
     * @return 新的字符串
     */
    public static String subArr2String(int i, int j, char[] arr) {
        if (j < i) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int k = i; k < j; k++) {
            builder.append(arr[k]);
        }
        return builder.toString();
    }

    /**
     *  将字符串数组的子串合成一个新的字符串
     * @param i 子串开始index
     * @param j 子串结束index
     * @param arr 字符串数组
     * @param separator 分隔符
     * @return 新的字符串
     */
    public static String subArr2String(int i, int j, String[] arr, String separator) {
        if (j < i) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int k = i; k < j - 1; k++) {
            if (separator != null) {
                builder.append(arr[k]).append(separator);
            } else {
                builder.append(arr[k]);
            }
        }
        builder.append(arr[j - 1]);
        return builder.toString().trim();
    }

    /**
     * 全角转半角
     * @param input String.
     * @return 半角字符串
     */
    public static String ToDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000')
                c[i] = ' ';
            else if (c[i] > '\uFF00' && c[i] < '\uFF5F')
                c[i] = (char) (c[i] - 65248);
        }

        return new String(c);
    }

}
