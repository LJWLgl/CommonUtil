package io.github.ljwlgl.fileutil;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FastJsonUtilTest {

    @Test
    public void testToJsonString() {
        assertEquals("result", FastJsonUtil.toJsonString("object"));
    }

    @Test
    public void testToStringNoSchema() {
        assertEquals("result", FastJsonUtil.toStringNoSchema("object"));
    }

    @Test
    public void testToJsonString1() {
        assertEquals("result", FastJsonUtil.toJsonString("object", "reAttrs"));
    }

    @Test
    public void testParamToString() {
        assertEquals("result", FastJsonUtil.paramToString("object"));
    }

    @Test
    public void testPut() {
        assertEquals("result", FastJsonUtil.put("json", "path", "value"));
    }

    @Test
    public void testRemove() {
        assertEquals("result", FastJsonUtil.remove("json", "path"));
    }

    @Test
    public void testReplace() {
        assertEquals("result", FastJsonUtil.replace("json", "path", "value"));
    }

    @Test
    public void testReplaceNew() {
        assertEquals("result", FastJsonUtil.replaceNew("json", "path", "value"));
    }

    @Test
    public void testEncryptJson() {
        // Setup
        final JSONObject jsonObject = new JSONObject(0, false);
        final List<String> paths = Arrays.asList("value");
        final JSONObject expectedResult = new JSONObject(0, false);

        // Run the test
        final JSONObject result = FastJsonUtil.encryptJson(jsonObject, paths);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testEncryptJson1() {
        // Setup
        final List<String> paths = Arrays.asList("value");

        // Run the test
        final String result = FastJsonUtil.encryptJson("json", paths);

        // Verify the results
        assertEquals("result", result);
    }

    @Test
    public void testEval() {
        assertEquals("result", FastJsonUtil.eval("json", "path"));
    }

    @Test
    public void testEval1() {
        // Setup
        final JSONObject jsonObject = new JSONObject(0, false);

        // Run the test
        final Object result = FastJsonUtil.eval(jsonObject, "path");

        // Verify the results
    }




    @Test
    public void testEvals() {
        // Setup

        // Run the test
        final List<Object> result = FastJsonUtil.evals("json", "path", Object.class);

        // Verify the results
    }



    @Test
    public void testParseObject1() {
        // Setup
        final JSONObject expectedResult = new JSONObject(0, false);

        // Run the test
        final JSONObject result = FastJsonUtil.parseObject("json");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testParse() {
        assertEquals("result", FastJsonUtil.parse("text"));
    }

    @Test
    public void testParseArray() {
        // Setup

        // Run the test
        final List<Object> result = FastJsonUtil.parseArray("json", Object.class);

        // Verify the results
    }

    @Test
    public void testGetJsonValue() {
        assertEquals("result", FastJsonUtil.getJsonValue("jsonStr", "key"));
    }

    @Test
    public void testContainsKey() {
        assertTrue(FastJsonUtil.containsKey("jsonStr", "key"));
    }

    @Test
    public void testContainsKey1() {
        assertTrue(FastJsonUtil.containsKey("jsonStr", "keys"));
    }

    @Test
    public void testContainsKey2() {
        // Setup
        final JSONObject jsonObject = new JSONObject(0, false);

        // Run the test
        final boolean result = FastJsonUtil.containsKey(jsonObject, "keys");

        // Verify the results
        assertTrue(result);
    }

    @Test
    public void testIsJSON() {
        assertTrue(FastJsonUtil.isJSON("str"));
    }
}
