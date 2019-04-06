package io.github.ljwlgl.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zqgan
 * @since 2019/08/28
 * 对FastJson接口的封装
 **/


public class FastJsonUtil {
    /**
     * 序列化Json
     */
    public static String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 序列化Json时删除不必要的属性
     * @param object
     * @param reAttrs
     * @return 序列化
     */
    public static String toJsonString(Object object, String... reAttrs) {
        List<String> reAttrList = Arrays.stream(reAttrs).filter(Objects::nonNull).collect(Collectors.toList());
        PropertyFilter filter = (obj, name, value) -> ! reAttrList.contains(name);
        return JSON.toJSONString(object, filter);
    }

    /**
     * 反序列化Json
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 反序列化成List
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 获取Json字符串某节点的值
     */
    public static String getJsonValue(String jsonStr, String key) {
        if (StringUtils.isEmpty(jsonStr) || StringUtils.isEmpty(key)) {
            return null;
        }
        JSONObject object = JSONObject.parseObject(jsonStr);
        return object.getString(key);
    }

}
