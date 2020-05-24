package io.github.ljwlgl.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zqgan
 * @since 2019/1/22
 **/

public class UrlParamsUtil {

    /**
     * 将Map转成String, 可以指定分隔符
     * @param map map
     * @param separator 分隔符
     * @return res
     */
    public static String join(Map<String, String> map, String separator) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append(separator);
        }
        if (builder.length() > 0) {
            builder.delete(builder.length() - separator.length(), builder.length());
        }
        return builder.toString();
    }

    /**
     * 解析ulr参数为map
     * @param paramsPath url
     * @return map
     */
    public static Map<String, String> split(String paramsPath) {
        return split(paramsPath, "=");
    }

    /**
     * 解析ulr参数为map
     * @param paramsPath url
     * @return map
     */
    public static Map<String, String> split(String paramsPath, String separator) {
        if (paramsPath == null || paramsPath.length() == 0 || ! paramsPath.contains("?")
                || ! paramsPath.contains(separator)) {
            return null;
        }
        String[] paramsArr = paramsPath.split("\\?");
        String paramsStr = paramsArr[paramsArr.length - 1];
        String[] paramsItems = paramsStr.split("&");
        if (paramsItems.length == 0) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String item : paramsItems) {
            if (item == null || item.length() == 0 || ! item.contains(separator)) {
                continue;
            }
            String[] keyValue = item.split(separator);
            result.put(keyValue[0], keyValue[1]);
        }
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    /**
     * 将keyValues转成Map
     * @param keyValues kayValue
     * @return map
     */
    public static Map<String, String> build(String ... keyValues){
        if(keyValues == null || keyValues.length <= 0){
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            result.put(keyValues[i], keyValues[i + 1]);
        }
        return result;
    }

    /**
     * 在原Map添加keyValues
     * @param originMap origin Map
     * @param keyValues  keyValues
     */
    public static void add(Map<String, String> originMap, String ... keyValues) {
        if (originMap == null || originMap.size() == 0) {
            return;
        }
        for (int i = 0; i < keyValues.length; i+=2) {
            originMap.put(keyValues[i], keyValues[i + 1]);
        }
    }

}
