package io.github.ljwlgl.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zqgan
 * @since 2018/9/14
 * 对象深度拷贝工具类
 **/

public class DeepCopyUtil {

    /**
     * 单个对象的深拷贝，srcObj对应的需实现java.io.Serializable接口
     * @param srcObj obj
     * @return new  obj
     */
    public static Object depthClone(Object srcObj) {
        Object cloneObj = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(out);
            oo.writeObject(srcObj);
            ByteArrayInputStream in = new ByteArrayInputStream(
                    out.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(in);
            cloneObj = oi.readObject();
        } catch (Exception ex) {
            return null;
        }
        return cloneObj;
    }

    /**
     * 多个对象的深拷贝，srcObj对应的需实现java.io.Serializable接口
     * @param list obj
     * @return new list obj
     */
    public static <T> List<T> listDepthClone(List<T> list) {
        List<T> newList = new ArrayList<>();
        for (Object item : list) {
            if (item == null) {
                continue;
            }
            Object val = depthClone(item);
            if (val != null) {
                newList.add((T) val);
            }
        }
        return newList;
    }

}
