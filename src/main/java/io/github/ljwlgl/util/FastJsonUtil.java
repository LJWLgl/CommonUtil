package io.github.ljwlgl.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class FastJsonUtil {
    /**
     * 序列化Json
     */
    public static String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 序列化Json，remove schema 属性
     * @param object
     * @return
     */
    public static String toStringNoSchema(Object object) {
        return toJsonString(object, "schema");
    }

    /**
     * 序列化Json时删除不必要的属性
     * @param object
     * @param reAttrs
     * @return String
     */
    public static String toJsonString(Object object, String... reAttrs) {
        List<String> reAttrList = Arrays.stream(reAttrs).filter(Objects::nonNull).collect(Collectors.toList());
        PropertyFilter filter = (obj, name, value) -> ! reAttrList.contains(name);
        return JSON.toJSONString(object, filter);
    }

    /**
     * 根据path向json加入指定对象
     * 注意：只支持JsonObject
     * @param json 原json串
     * @param path 需要添加的路径
     * @param obj 添加的对象
     * @return 新json串
     */
    public static String put(String json, String path, Object obj) {
        if (StringUtils.isEmpty(path) || obj == null) {
            return json;
        }
        String[] keys = null;
        if (path.contains(".")) {
            keys = path.split("\\.");
        } else {
            keys = new String[]{path};
        }
        JSONObject preObjcet = JSONObject.parseObject(json);
        JSONObject object = preObjcet;
        for (int i = keys[0].equals("$") ? 1 : 0; i < keys.length - 1; i++) {
            if (object != null) {
                object = object.getJSONObject(keys[i]);
            }
        }
        if (object == null) {
            return json;
        }
        object.put(keys[keys.length - 1], obj);
        return toJsonString(preObjcet);
    }

    /**
     * 根据path取出结果
     * @param json
     * @param path
     * @return String
     */
    public static Object eval(String json, String path) {
        JSONObject jsonObject = JSON.parseObject(json);
        return JSONPath.eval(jsonObject, path);
    }

    /**
     * 根据path从json中取出结果并反序列成JavaBean
     * @param json json字符串
     * @param path 需要的json路径
     * @param clz class
     * @param <T> 具体类型
     * @return res
     */
    @SuppressWarnings("unchecked")
    public static <T> T eval(String json, String path, Class<T> clz) {
        Object obj = eval(json, path);
        if (obj  == null) {
            return null;
        }
        if (obj instanceof String) {
            if (clz.equals(Long.class)) {
                return (T) Long.valueOf(obj.toString());
            } else if (clz.equals(Integer.class)) {
                return (T) Integer.valueOf(obj.toString());
            } else if (clz.equals(String.class)) {
                return (T) obj.toString();
            } else if (clz.equals(Boolean.class)) {
                return (T) Boolean.valueOf(obj.toString());
            } else if (clz.equals(Double.class)) {
                return (T) Double.valueOf(obj.toString());
            } else if (clz.equals(Float.class)) {
                return (T) Float.valueOf(obj.toString());
            } else {
                throw new IllegalArgumentException("eval error, illegal class type");
            }
        } else if (obj instanceof Integer) {
            if (clz.equals(Integer.class)) {
                return (T) obj;
            } else if (clz.equals(Long.class)) {
                return (T) Long.valueOf(String.valueOf(obj));
            }
        } else if (obj instanceof JSONObject) {
            return JSONObject.parseObject(JSON.toJSONString(obj), clz);
        } else if (obj instanceof JSONArray) {
            throw new IllegalArgumentException("eval error, cannot cast to list");
        }
        return null;
    }

    /**
     * 根据path从json中取出结果并反序列成JavaBean，该方法只支持array
     * @param json json字符串
     * @param path 需要的json路径
     * @param clz class
     * @param <T> 具体类型
     * @return res
     */
    public static <T> List<T> evals(String json, String path, Class<T> clz) {
        Object obj = eval(json, path);
        if (obj  == null) {
            return null;
        }
        if (obj instanceof JSONArray) {
            return JSONArray.parseArray(JSON.toJSONString(obj), clz);
        }
        return null;
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
