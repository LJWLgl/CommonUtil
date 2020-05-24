package io.github.ljwlgl.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EncodeDecodeUtilTest {

    @Test
    public void testEncodeWithMD5() {
        String expectRes = "098f6bcd4621d373cade4e832627b4f6";
        Assert.assertEquals(expectRes, EncodeDecodeUtil.encodeWithMD5("test"));
    }
}