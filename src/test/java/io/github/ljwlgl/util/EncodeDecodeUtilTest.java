package io.github.ljwlgl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EncodeDecodeUtilTest {

    @Test
    public void testEncodeWithMD5() {
        assertEquals("result", EncodeDecodeUtil.encodeWithMD5("str"));
    }

    @Test
    public void testEncodeWithSHA1() {
        assertEquals("result", EncodeDecodeUtil.encodeWithSHA1("str"));
    }

    @Test
    public void testEncodeWithSHA256() {
        assertEquals("result", EncodeDecodeUtil.encodeWithSHA256("str"));
    }

    @Test
    public void testEncode() {
        assertEquals("result", EncodeDecodeUtil.encode("algorithm", "str"));
    }

    @Test
    public void testEncodeBase64() {
        assertEquals("result", EncodeDecodeUtil.encodeBase64("str"));
    }

    @Test
    public void testDecodeBase64() {
        assertEquals("result", EncodeDecodeUtil.decodeBase64("str"));
    }

    @Test
    public void testDecodeUrl() {
        assertEquals("result", EncodeDecodeUtil.decodeUrl("str"));
    }

    @Test
    public void testEncodeUrl() {
        assertEquals("result", EncodeDecodeUtil.encodeUrl("str"));
    }
}
