package io.github.ljwlgl.util;

import java.util.Map;

/**
 * @author zqgan
 * @since 2019/1/22
 **/

public class MapUtil {

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

}
