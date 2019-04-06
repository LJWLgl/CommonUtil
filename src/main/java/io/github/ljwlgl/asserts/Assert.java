package io.github.ljwlgl.asserts;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * 仿照Spring定制的验证类
 *
 * @author Quding Ding
 * @since 2018/5/5
 */

public class Assert {


    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void state(boolean expression, String message, Object... additional) {
        if (!expression) {
            throw new IllegalStateException(message + Arrays.toString(additional));
        }
    }

    public static void isNull(Object object, String message, Object... additional) {
        if (object != null) {
            throw new IllegalArgumentException(message + Arrays.toString(additional));
        }
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object, String message, Object... additional) {
        if (object == null) {
            throw new IllegalArgumentException(message + Arrays.toString(additional));
        }
    }

    public static void hasLength(String text, String message) {
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasLength(String text, String message, Object... additional) {
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException(message + Arrays.toString(additional));
        }
    }

    public static void equals(Object o1, Object o2, String message) {
        if (!Objects.equals(o1, o2)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void equals(Object o1, Object o2, String message, Object... additional) {
        if (!Objects.equals(o1, o2)) {
            throw new IllegalArgumentException(message + Arrays.toString(additional));
        }
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (null == collection || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, String message, Object... additional) {
        if (null == collection || collection.isEmpty()) {
            throw new IllegalArgumentException(message + Arrays.toString(additional));
        }
    }

}
