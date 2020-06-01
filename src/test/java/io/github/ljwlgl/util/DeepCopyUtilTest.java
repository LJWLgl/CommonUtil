package io.github.ljwlgl.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeepCopyUtilTest {

    @Test
    public void testDepthClone() {
        assertEquals("result", DeepCopyUtil.depthClone("srcObj"));
    }

    @Test
    public void testListDepthClone() {
    }
}
