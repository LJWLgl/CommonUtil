package io.github.ljwlgl.fileutil;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
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
     *
     * @param object
     * @return
     */
    public static String toStringNoSchema(Object object) {
        return toJsonString(object, "schema");
    }

    /**
     * 序列化Json时删除不必要的属性
     *
     * @param object
     * @param reAttrs
     * @return String
     */
    public static String toJsonString(Object object, String... reAttrs) {
        List<String> reAttrList = Arrays.stream(reAttrs).filter(Objects::nonNull).collect(Collectors.toList());
        PropertyFilter filter = (obj, name, value) -> !reAttrList.contains(name);
        return JSON.toJSONString(object, filter);
    }

    public static String paramToString(Object object) {
        return toJsonString(object, "schema");
    }


    /**
     * 根据path向json加入指定对象
     * 注意：只支持JSONObject类型
     *
     * @param json  原json串
     * @param path  需要添加的路径
     * @param value 添加的对象
     * @return 新json串
     */
    public static String put(String json, String path, Object value) {
        if (StringUtils.isEmpty(path) || value == null) {
            return json;
        }
        String[] keys = null;
        if (path.contains(".")) {
            keys = path.split("\\.");
        } else {
            keys = new String[]{path};
        }
        JSONObject preObject = parseObject(json);
        JSONObject object = getJSONObjectByKeys(preObject, keys);
        if (object == null) {
            return json;
        }
        object.put(keys[keys.length - 1], value);
        return toJsonString(preObject);
    }

    /**
     * 根据path删除指定属性
     *
     * @param json 原json串
     * @param path 需要删除的json路径
     * @return 新的json串
     */
    public static String remove(String json, String path) {
        if (StringUtils.isEmpty(path)) {
            return json;
        }
        String[] keys = null;
        if (path.contains(".")) {
            keys = path.split("\\.");
        } else {
            keys = new String[]{path};
        }
        JSONObject preObject = parseObject(json);
        JSONObject object = getJSONObjectByKeys(preObject, keys);
        if (object == null) {
            return json;
        }
        object.remove(keys[keys.length - 1]);
        return toJsonString(preObject);
    }

    /**
     * 根据path替换指定属性, 只支持JSONObject
     *
     * @param json 原json串
     * @param path 需要替换的json路径
     * @return 新的json串
     */
    public static String replace(String json, String path, Object value) {
        if (StringUtils.isEmpty(path)) {
            return json;
        }
        String[] keys = null;
        if (path.contains(".")) {
            keys = path.split("\\.");
        } else {
            keys = new String[]{path};
        }
        JSONObject preObject = parseObject(json);
        JSONObject object = getJSONObjectByKeys(preObject, keys);
        if (object == null) {
            return json;
        }
        object.replace(keys[keys.length - 1], value);
        return toJsonString(preObject);
    }

    /**
     * 根据path替换指定属性, 支持JSONArray，但是不支持路径表达式
     *
     * @param json 原json串
     * @param path 需要替换的json路径
     * @return 新的json串
     */
    public static String replaceNew(String json, String path, String value) {
        if (StringUtils.isEmpty(path)) {
            return json;
        }
        String[] keys = null;
        if (path.contains(".")) {
            keys = path.split("\\.");
        } else {
            keys = new String[]{path};
        }
        List<JSONObject> res = new ArrayList<>();
        JSONObject preObject = parseObject(json);
        getJSONObjectByKeys(res, preObject, keys, 1);
        if (CollectionUtils.isEmpty(res)) {
            return json;
        }
        for (int j = 0; j < res.size(); j++) {
            Object oldValue = res.get(j).get(keys[keys.length - 1]);
            if (oldValue == null) {
                continue;
            }
            res.get(j).replace(keys[keys.length - 1], String.valueOf(value));
        }
        return toJsonString(preObject);
    }

    private static JSONObject getJSONObjectByKeys(JSONObject preObject, String[] keys) {
        JSONObject object = preObject;
        for (int i = keys[0].equals("$") ? 1 : 0; i < keys.length - 1; i++) {
            if (object != null) {
                object = object.getJSONObject(keys[i]);
            }
        }
        return object;
    }

    // 支持JSONArray
    private static void getJSONObjectByKeys(List<JSONObject> res, Object object, String[] keys, int index) {
        if (object == null || index >= keys.length) {
            return;
        }
        if (object instanceof JSONArray) {
            JSONArray array = (JSONArray) object;
            for (int i = 0; i < array.size(); i++) {
                getJSONObjectByKeys(res, array.get(i), keys, index);
            }
            return;
        } else if (object instanceof JSONObject) {
            JSONObject obj = (JSONObject) object;
            if (index < keys.length - 1) {
                getJSONObjectByKeys(res, obj.get(keys[index]), keys, index + 1);
                return;
            }
            res.add((JSONObject) object);
        }
    }


    public static JSONObject encryptJson(JSONObject jsonObject, List<String> paths) {
        if (jsonObject == null || CollectionUtils.isEmpty(paths)) {
            return jsonObject;
        }
        try {
            return encryptToObject(jsonObject, paths.toArray(new String[paths.size()]));
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static String encryptJson(String json, List<String> paths) {
        if (StringUtils.isBlank(json) || CollectionUtils.isEmpty(paths)) {
            return json;
        }
        JSONObject preObject = parseObject(json);
        try {
            return encryptToJson(preObject, paths.toArray(new String[paths.size()]));
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 加密json，支持JsonArray
     *
     * @param preObject 原json串
     * @param paths     需要加密的json路径
     * @return 新的json串
     */
    private static String encryptToJson(JSONObject preObject, String[] paths) {
        return toJsonString(encryptToObject(preObject, paths));
    }

    private static JSONObject encryptToObject(JSONObject preObject, String[] paths) {
        for (int i = 0; i < paths.length; i++) {
            if (StringUtils.isEmpty(paths[i])) {
                continue;
            }
            String[] keys = null;
            if (paths[i].contains(".")) {
                keys = paths[i].split("\\.");
            } else {
                keys = new String[]{paths[i]};
            }
            List<JSONObject> res = new ArrayList<>();
            getJSONObjectByKeys(res, preObject, keys, 1);
            if (CollectionUtils.isEmpty(res)) {
                continue;
            }
            for (int j = 0; j < res.size(); j++) {
                Object oldValue = res.get(j).get(keys[keys.length - 1]);
                if (oldValue == null) {
                    continue;
                }
                res.get(j).replace(keys[keys.length - 1], encryptText(String.valueOf(oldValue)));
            }
        }
        return preObject;
    }

    private static String encryptText(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        // 加密字符占字符比例的0.36
        int passLength = (int) Math.ceil(text.length() * 0.36);
        int midIdx = text.length() / 2;
        char[] arr = text.toCharArray();
        arr[midIdx] = '*';
        for (int i = 1, p = 1, k = 1; i < passLength; i++) {
            if (i % 2 == 0) {
                arr[midIdx + p] = '*';
                p++;
            } else {
                arr[midIdx - k] = '*';
                k++;
            }
        }
        return String.valueOf(arr);
    }

    /**
     * 根据path取出结果
     *
     * @param json
     * @param path
     * @return String
     */
    public static Object eval(String json, String path) {
        try {
            if (!json.contains("{") || !json.contains("}")) {
                return null;
            }
            JSONObject jsonObject = JSON.parseObject(json);
            return JSONPath.eval(jsonObject, path);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Object eval(JSONObject jsonObject, String path) {
        try {
            return JSONPath.eval(jsonObject, path);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 根据path从json中取出结果并反序列成JavaBean
     *
     * @param json json字符串
     * @param path 需要的json路径
     * @param clz  class
     * @param <T>  具体类型
     * @return res
     */
    @SuppressWarnings("unchecked")
    public static <T> T eval(String json, String path, Class<T> clz) {
        Object obj = eval(json, path);
        if (obj == null) {
            return null;
        }
        return instanceOf(obj, clz);
    }

    public static <T> T eval(JSONObject jsonObject, String path, Class<T> clz) {
        Object obj = eval(jsonObject, path);
        if (obj == null) {
            return null;
        }
        return instanceOf(obj, clz);
    }

    private static <T> T instanceOf(Object obj, Class<T> clz) {
        if (obj instanceof String) {
            return valueOf(obj, clz);
        } else if (obj instanceof Integer) {
            return valueOf(obj, clz);
        } else if (obj instanceof Long) {
            return valueOf(obj, clz);
        } else if (obj instanceof BigDecimal) {
            return valueOf(obj, clz);
        } else if (obj instanceof JSONObject) {
            return JSONObject.parseObject(JSON.toJSONString(obj), clz);
        } else if (obj instanceof JSONArray) {
            return valueOf(obj, clz);
        } else {
            return null;
        }
    }

    private static <T> T valueOf(Object obj, Class<T> clz) {
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
        } else if (clz.equals(BigDecimal.class)) {
            return (T) BigDecimal.valueOf(Double.valueOf(obj.toString()));
        } else {
            return null;
        }
    }

    /**
     * 根据path从json中取出结果并反序列成JavaBean，该方法只支持array
     *
     * @param json json字符串
     * @param path 需要的json路径
     * @param clz  class
     * @param <T>  具体类型
     * @return res
     */
    public static <T> List<T> evals(String json, String path, Class<T> clz) {
        Object obj = eval(json, path);
        if (obj == null) {
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

    public static JSONObject parseObject(String json) {
        return JSON.parseObject(json);
    }

    public static Object parse(String text) {
        return JSON.parse(text);
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

    public static boolean containsKey(String jsonStr, String key) {
        boolean result = false;
        if (StringUtils.isEmpty(jsonStr) || StringUtils.isEmpty(key)) {
            return result;
        }
        try {
            JSONObject object = JSONObject.parseObject(jsonStr);
            result = object.containsKey(key);
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    /**
     * 判断Json是否包含keys属性
     *
     * @param jsonStr json
     * @param keys    属性的可变长参数
     * @return boolean
     */
    public static boolean containsKey(String jsonStr, String... keys) {
        if (StringUtils.isEmpty(jsonStr) || keys == null || keys.length == 0) {
            return false;
        }
        JSONObject object = JSONObject.parseObject(jsonStr);
        return containsKey(object, keys);
    }

    public static boolean containsKey(JSONObject jsonObject, String... keys) {
        if (jsonObject == null || keys == null || keys.length == 0) {
            return false;
        }
        try {
            for (int i = 0; i < keys.length; i++) {
                if (!jsonObject.containsKey(keys[i])) {
                    return false;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是Json串
     *
     * @param str 检测字符串
     * @return bool
     */
    public static boolean isJSON(String str) {

        if (StringUtils.isBlank(str)) {
            return false;
        }

        boolean result;

        try {
            JSON.parse(str);
            result = true;
        } catch (JSONException e) {
            result = false;
        }
        return result;
    }

}
