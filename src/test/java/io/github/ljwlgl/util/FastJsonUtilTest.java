package io.github.ljwlgl.util;

import com.google.common.collect.Lists;
import io.github.ljwlgl.asserts.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FastJsonUtilTest {

    @Test
    public void test() {
        String request = "{\"ww\":[{\"eee\":[{\"ddd\":1111,\"sss\":2}]}],\"requestParameters\":\"{\\\"abc\\\":114}\"}";
        System.out.println(FastJsonUtil.replaceNew(request, "$.ww.eee.ddd", ""));
    }

}