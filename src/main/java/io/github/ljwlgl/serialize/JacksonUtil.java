package io.github.ljwlgl.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Strings;

import java.util.*;

/**
 * @author zqgan
 * @since 2018/9/1
 * 序列化相关类
 */

public class JacksonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    private JacksonUtil() {

    }

    public static ObjectMapper getInstance() {

        return objectMapper;
    }


    public static String obj2jsonForSoaEntity(Object obj) {
        if (obj == null) {
            return "null";
        }
        String result;
        try {
            result = JSON.toJSON(removeSchema(obj)).toString();
        } catch (Throwable t) {
            result = "obj to json error:" + obj;
        }
        return result;
    }

    public static String obj2json(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    public static Object removeSchema(Object obj) {
        try {
            JSONObject jsonObject;
            if (obj instanceof JSONObject) {
                jsonObject = (JSONObject) obj;
            } else {
                String result = JSON.toJSON(obj).toString();
                jsonObject = JSON.parseObject(result);
            }
            jsonObject.remove("schema");
            Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                if (entry.getValue() instanceof JSONObject) {
                    String temp = JSON.toJSON(entry.getValue()).toString();
                    if (temp.contains("schema")) {
                        entry.setValue(removeSchema(entry.getValue()));
                    }
                } else if (entry.getValue() instanceof JSONArray) {
                    JSONArray jsonArray = JSONArray.parseArray(JSON.toJSON(entry.getValue()).toString());
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Object tempObj = jsonArray.get(i);
                        if (tempObj instanceof JSONObject) {
                            removeSchema(tempObj);
                        }
                    }
                    entry.setValue(jsonArray);
                }
            }
            obj = jsonObject;
        } catch (Throwable t) {
            return obj;
        }
        return obj;
    }


    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2jsonSafe(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "convert to json error:" + e.getMessage();
        }
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojoSafe(String jsonStr, Class<T> clazz) {
        T object = null;
        try {
            if (Strings.isNullOrEmpty(jsonStr)) {
                object = objectMapper.readValue(jsonStr, clazz);
            }
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz) throws Exception {
        T object = null;
        if (Strings.isNullOrEmpty(jsonStr)) {
            try {
                object = objectMapper.readValue(jsonStr, clazz);
            } catch (Exception e) {
                throw e;
            }
        }
        return object;
    }

    /**
     * json string convert to map
     */
    public static <T> Map<String, Object> json2map(String jsonStr)
            throws Exception {
        return objectMapper.readValue(jsonStr, Map.class);
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz)
            throws Exception {
        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json string convert to map with javaBean
     * @jsonStr:JsonStr
     * @clazz:TypeOf
     */
    public static <T> Map<Long, T> json2LongTreeMap(String jsonStr, Class<T> clazz)
            throws Exception {
        Map<String, Map<Long, Object>> map = objectMapper.readValue(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        Map<Long, T> result = new TreeMap<Long, T>(
                new Comparator<Long>() {
                    public int compare(Long obj1, Long obj2) {
                        //按升序排列
                        return obj1.compareTo(obj2);
                    }
                });
        for (Map.Entry<String, Map<Long, Object>> entry : map.entrySet()) {
            result.put(Long.parseLong(entry.getKey()), map2pojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json string convert to map with javaBean, between start and end
     * @jsonStr:JsonStr
     * @clazz:TypeOf
     */
    public static <T> Map<Long, T> json2LongTreeMap(String jsonStr, Class<T> clazz, long start, long end)
            throws Exception {
        Map<String, Map<Long, Object>> map = objectMapper.readValue(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        Map<Long, T> result = new HashMap<>();
        for (Map.Entry<String, Map<Long, Object>> entry : map.entrySet()) {
            long key = Long.parseLong(entry.getKey());
            if (key >= start && key < end) {
                result.put(Long.parseLong(entry.getKey()), map2pojo(entry.getValue(), clazz));
            }
        }
        return result;
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz)
            throws Exception {
        List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr,
                new TypeReference<List<T>>() {
                });
        List<T> result = new ArrayList<T>();
        for (Map<String, Object> map : list) {
            result.add(map2pojo(map, clazz));
        }
        return result;
    }

    /**
     * map convert to javaBean
     */
    public static <T> T map2pojo(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /// <summary>
    /// 获取Json字符串某节点的值
    ///这里如果值里包含 逗号(,)会只取逗号之前的值
    /// </summary>
    public static String getJsonValue(String jsonStr, String key) {
        String result = "";
        if (!Strings.isNullOrEmpty(jsonStr)) {
            key = "\"" + key.trim() + "\"";
            int index = jsonStr.indexOf(key) + key.length() + 1;
            if (index > key.length() + 1) {
                //先截逗号，若是最后一个，截“｝”号，取最小值
                //这里如果值里包含 逗号(,)会只取逗号之前的值，暂不影响使用。
                int end = jsonStr.indexOf(',', index);
                if (end == -1) {
                    end = jsonStr.indexOf('}', index);
                }

                result = jsonStr.substring(index, end);
                result = result.replaceAll("\"", "").trim(); //过滤引号或空格
            }
        }
        return result;
    }

}
