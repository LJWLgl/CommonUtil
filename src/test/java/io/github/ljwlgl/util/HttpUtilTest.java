package io.github.ljwlgl.util;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HttpUtilTest {

    @Test
    public void testDoGet() throws Exception {
        assertEquals("result", HttpUtil.doGet("uri"));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test(expected = IOException.class)
    public void testDoGet_ThrowsIOException() throws Exception {
        HttpUtil.doGet("uri");
    }

    @Test
    public void testDoGet1() throws Exception {
        // Setup
        final Map<String, String> queryParam = new HashMap<>();

        // Run the test
        final String result = HttpUtil.doGet("url", queryParam);

        // Verify the results
        assertEquals("result", result);
    }

    @Test(expected = IOException.class)
    public void testDoGet_ThrowsIOException1() throws Exception {
        // Setup
        final Map<String, String> queryParam = new HashMap<>();

        // Run the test
        HttpUtil.doGet("url", queryParam);
    }

    @Test
    public void testDoPost() throws Exception {
        // Setup
        final Map<String, String> params = new HashMap<>();

        // Run the test
        final String result = HttpUtil.doPost("url", params, "json");

        // Verify the results
        assertEquals("result", result);
    }

    @Test(expected = IOException.class)
    public void testDoPost_ThrowsIOException() throws Exception {
        // Setup
        final Map<String, String> params = new HashMap<>();

        // Run the test
        HttpUtil.doPost("url", params, "json");
    }

    @Test
    public void testDoPost1() throws Exception {
        // Setup
        final Map<String, String> params = new HashMap<>();
        final Map<String, String> headers = new HashMap<>();

        // Run the test
        final String result = HttpUtil.doPost("url", params, "json", headers);

        // Verify the results
        assertEquals("result", result);
    }

    @Test(expected = IOException.class)
    public void testDoPost_ThrowsIOException1() throws Exception {
        // Setup
        final Map<String, String> params = new HashMap<>();
        final Map<String, String> headers = new HashMap<>();

        // Run the test
        HttpUtil.doPost("url", params, "json", headers);
    }

    @Test
    public void testDoPost2() throws Exception {
        // Setup
        final Map<String, String> form = new HashMap<>();

        // Run the test
        final String result = HttpUtil.doPost("url", form);

        // Verify the results
        assertEquals("result", result);
    }

    @Test(expected = IOException.class)
    public void testDoPost_ThrowsIOException2() throws Exception {
        // Setup
        final Map<String, String> form = new HashMap<>();

        // Run the test
        HttpUtil.doPost("url", form);
    }

    @Test
    public void testDoPost3() throws Exception {
        assertEquals("result", HttpUtil.doPost("url"));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test(expected = IOException.class)
    public void testDoPost_ThrowsIOException3() throws Exception {
        HttpUtil.doPost("url");
    }
}
