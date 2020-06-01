package io.github.ljwlgl.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UrlParamsUtilTest {

    @Test
    public void testJoin() {
        // Setup
        final Map<String, String> map = new HashMap<>();

        // Run the test
        final String result = UrlParamsUtil.join(map, "separator");

        // Verify the results
        assertEquals("result", result);
    }

    @Test
    public void testSplit() {
        // Setup
        final Map<String, String> expectedResult = new HashMap<>();

        // Run the test
        final Map<String, String> result = UrlParamsUtil.split("paramsPath");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSplit1() {
        // Setup
        final Map<String, String> expectedResult = new HashMap<>();

        // Run the test
        final Map<String, String> result = UrlParamsUtil.split("paramsPath", "separator");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testBuild() {
        // Setup
        final Map<String, String> expectedResult = new HashMap<>();

        // Run the test
        final Map<String, String> result = UrlParamsUtil.build("keyValues");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testAdd() {
        // Setup
        final Map<String, String> originMap = new HashMap<>();

        // Run the test
        UrlParamsUtil.add(originMap, "keyValues");

        // Verify the results
    }
}
