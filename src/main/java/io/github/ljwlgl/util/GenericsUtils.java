package io.github.ljwlgl.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zq_gan
 * @since 2019/10/29
 * 泛型工具类
 **/

public class GenericsUtils {

    public interface BatchApply<K, V> {
        Map<K, V> apply(List<K> list);
    }

    /**
     * 针对有大小限制的接口分批调用
     * @param list idList
     * @param maxSize 限制大小
     * @param apply 回调函数即为原先无限制大小限制的函数
     */
    public <K, V> Map<K, V> batchInvoke(List<K> list, int maxSize, BatchApply<K, V> apply) {
        Map<K, V> map = new HashMap<>();
        if (list.size() > maxSize) {
            int index = 1;
            List<K> tmpIds = list.subList(0, maxSize);
            while (CollectionUtils.isNotEmpty(tmpIds)) {
                map.putAll(apply.apply(tmpIds));
                if (list.size() >=  (index + 1) * maxSize) {
                    tmpIds = list.subList(index * maxSize, (index + 1) * maxSize);
                } else if (list.size() >= index * maxSize) {
                    tmpIds = list.subList(index * maxSize, list.size());
                } else {
                    tmpIds = new ArrayList<>();
                }
                index++;
            }
        } else {
            map.putAll(apply.apply(list));
        }
        return map;
    }

}
