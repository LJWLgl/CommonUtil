package io.github.ljwlgl.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zqgan
 * @since 2019/3/29
 **/

@SuppressWarnings("unchecked")
public class ProtobufUtil {


    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();

    private static Map<Class<?>, Field> wrapperMap = new ConcurrentHashMap<Class<?>, Field>();

    private static Map<Class<?>, Object> unWrapperMap = new ConcurrentHashMap<Class<?>, Object>();


    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if (schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }

    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        if (isNullOrEmpty(obj)) {
            return null;
        }
        try {
            if(List.class.isAssignableFrom(obj.getClass())){
                List<T> list=(List<T>)obj;
                Class<?> clazz=list.get(0).getClass();
                byte [] data=serializeList(list);
                CustomWrapper wrapper=new CustomWrapper(clazz,data);
                return serializeT(wrapper);
            }
            if(Set.class.isAssignableFrom(obj.getClass())){
                List<T> list=new ArrayList<T>((Set<T>)obj);
                Class<?> clazz=list.get(0).getClass();
                byte [] data=serializeList(list);
                CustomWrapper wrapper=new CustomWrapper(clazz,data);
                return serializeT(wrapper);
            }
            if(ValueWrapper.isSpecialType(obj.getClass())){
                ValueWrapper wrapper=new ValueWrapper(obj);
                return serializeT(wrapper);
            }
            return serializeT(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static <T> byte[] serializeList(List<T> objList) {
        if (objList == null || objList.isEmpty()) {
            throw new RuntimeException("序列化对象列表(" + objList + ")参数异常!");
        }
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(objList.get(0).getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] protostuff = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(bos, objList, schema, buffer);
            protostuff = bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("序列化对象列表(" + objList + ")发生异常!", e);
        } finally {
            buffer.clear();
            try {
                if(bos!=null){
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return protostuff;
    }

    private static <T> byte[] serializeT(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.MIN_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }


    /**
     * 反序列化
     *
     * @param data
     * @param cls
     * @return
     */

    public static <T> T unSerialize(byte[] data,Class<?> clazz) {
        if (isNullOrEmpty(data)) {
            return null;
        }
        try {
            if(List.class.isAssignableFrom(clazz)){
                CustomWrapper wrapper= unSerializeT(data, CustomWrapper.class);
                return (T) unSerializeList(data, wrapper.getClazz());
            }
            if(Set.class.isAssignableFrom(clazz)){
                CustomWrapper wrapper= unSerializeT(data, CustomWrapper.class);
                return (T) unSerializeSet(data, wrapper.getClazz());
            }
            if(ValueWrapper.isSpecialType(clazz)){
                ValueWrapper wrapper= unSerializeT(data, ValueWrapper.class);
                if(wrapper==null||isNullOrEmpty(wrapper)){
                    return null;
                }
                return wrapper.getValue();
            }
            return (T) unSerializeT(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static <T> Set<T> unSerializeSet(byte[] data, Class<T> clazz) {
        if (data == null || data.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }

        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        try {
            List<T> list = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(data), schema);
            return new HashSet<T>(list);
        } catch (IOException e) {
            throw new RuntimeException("反序列化对象列表发生异常!",e);
        }
    }

    public static <T> List<T> unSerializeList(byte[] data, Class<T> clazz) {
        if (data == null || data.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }

        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        List<T> result = null;
        try {
            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(data), schema);
        } catch (IOException e) {
            throw new RuntimeException("反序列化对象列表发生异常!",e);
        }
        return result;
    }
    private static <T> T unSerializeT(byte[] data, Class<T> cls) {
        try {
            T message = cls.newInstance();
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    public static boolean isNullOrEmpty(Object obj) {
        try {
            if (obj == null)
                return true;
            if (obj instanceof CharSequence) {
                return ((CharSequence) obj).length() == 0;
            }
            if (obj instanceof Collection) {
                return ((Collection<?>) obj).isEmpty();
            }
            if (obj instanceof Map) {
                return ((Map<?, ?>) obj).isEmpty();
            }
            if (obj instanceof Object[]) {
                Object[] object = (Object[]) obj;
                if (object.length == 0) {
                    return true;
                }
                boolean empty = true;
                for (int i = 0; i < object.length; i++) {
                    if (!isNullOrEmpty(object[i])) {
                        empty = false;
                        break;
                    }
                }
                return empty;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

    }


    @SuppressWarnings({  "serial" })
    public static class CustomWrapper  implements Serializable {

        private Class<?> clazz;

        private byte []data;

        public CustomWrapper(){}

        public CustomWrapper(Class<?> clazz,byte[] data){
            this.clazz=clazz;
            this.data=data;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public void setClazz(Class<?> clazz) {
            this.clazz = clazz;
        }


    }


    @SuppressWarnings({ "rawtypes", "serial", "unused" })
    public static class ValueWrapper implements Serializable{


        private Map mapValue;

        private List listValue;

        private Collection collectionValue;

        private Iterable iterableValue;

        private Set setValue;

        private String stringValue;

        private Byte byteValue;

        private Short shortValue;

        private Long longValue;

        private Integer integerValue;

        private Double doubleValue;

        private Float floatValue;

        private Character characterValue;

        private Boolean booleanValue;


        public ValueWrapper(){}

        public ValueWrapper(Object data) throws IllegalArgumentException, IllegalAccessException {
            if (data == null) {
                return;
            }
            if (isNullOrEmpty(wrapperMap)) {
                initFiledType();
            }
            if (wrapperMap.containsKey(data.getClass())) {
                Field f = wrapperMap.get(data.getClass());
                f.setAccessible(true);
                f.set(this, data);
            }
            for (Class<?> clazz : wrapperMap.keySet()) {
                if (!clazz.isAssignableFrom(data.getClass())) {
                    continue;
                }
                Field f = wrapperMap.get(clazz);
                f.setAccessible(true);
                f.set(this, data);
                wrapperMap.put(data.getClass(), f);
                return;
            }
        }

        public static  boolean isSpecialType(Class<?> clazz){
            if (isNullOrEmpty(wrapperMap)) {
                initFiledType();
            }
            if(unWrapperMap.containsKey(clazz)){
                return false;
            }
            if(wrapperMap.containsKey(clazz)){
                return true;
            }
            for (Class<?> clazzTmp : wrapperMap.keySet()) {
                if (!clazzTmp.isAssignableFrom(clazz)) {
                    continue;
                }
                Field f = wrapperMap.get(clazzTmp);
                f.setAccessible(true);
                wrapperMap.put(clazz, f);
                return true;
            }
            unWrapperMap.put(clazz, clazz);
            return false;
        }
        private static void initFiledType() {
            Field[] fields = ValueWrapper.class.getDeclaredFields();
            for (Field f : fields) {
                wrapperMap.put(f.getType(), f);
            }
        }

        public <T> T getValue() throws IllegalArgumentException, IllegalAccessException {
            for (Class<?> clazz : wrapperMap.keySet()) {
                T result = (T) wrapperMap.get(clazz).get(this);
                if (isNullOrEmpty(result)) {
                    continue;
                }
                return result;
            }
            return null;
        }

    }


}
