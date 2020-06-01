package io.github.ljwlgl.fileutil;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CsvUtilTest {

    @Test
    public void testRead() throws Exception {
        // Setup

        // Run the test
        final List<String[]> result = CsvUtil.read("/Users/nanxuan/temp/test11.csv");
        // Verify the results
    }

    @Test(expected = Exception.class)
    public void testRead_ThrowsException() throws Exception {
        // Setup

        // Run the test
        CsvUtil.read("/Users/nanxuan/temp/test11.csv");
    }

    @Test
    public void testRead1() throws Exception {
        // Setup

        // Run the test
        final List<String[]> result = CsvUtil.read("/Users/nanxuan/temp/test11.csv", new int[]{0,1});
        Assert.assertNotNull(result);
        // Verify the results
    }

    @Test(expected = Exception.class)
    public void testRead_ThrowsException1() throws Exception {
        // Setup

        // Run the test
        CsvUtil.read("/Users/nanxuan/temp/test11.csv", new int[]{0});
    }

    @Test
    public void testRead2() throws Exception {
        // Setup

        // Run the test
        final List<String[]> result = CsvUtil.read("/Users/nanxuan/temp/test11.csv", false);

        // Verify the results
    }

    @Test(expected = Exception.class)
    public void testRead_ThrowsException2() throws Exception {
        // Setup

        // Run the test
        CsvUtil.read("/Users/nanxuan/temp/test11.csv", false);
    }

    @Test
    public void testRead3() throws Exception {
        // Setup

        // Run the test
        final List<String[]> result = CsvUtil.read("/Users/nanxuan/temp/test11.csv", false, new int[]{0});

        // Verify the results
    }

    @Test(expected = Exception.class)
    public void testRead_ThrowsException3() throws Exception {
        // Setup

        // Run the test
        CsvUtil.read("/Users/nanxuan/temp/test11.csv", false, new int[]{0});
    }

    @Test
    public void testRead4() throws Exception {
        // Setup

        // Run the test
        final List<String[]> result = CsvUtil.read("/Users/nanxuan/temp/test11.csv", "code", false);

        // Verify the results
    }

    @Test(expected = Exception.class)
    public void testRead_ThrowsException4() throws Exception {
        // Setup

        // Run the test
        CsvUtil.read("/Users/nanxuan/temp/test11.csv", "code", false);
    }

    @Test
    public void testRead5() throws Exception {
        // Setup
        final InputStream inputStream = new ByteArrayInputStream("content".getBytes());

        // Run the test
        final List<String[]> result = CsvUtil.read(inputStream, StandardCharsets.UTF_8, false, new int[]{0});

        // Verify the results
    }

    @Test(expected = Exception.class)
    public void testRead_ThrowsException5() throws Exception {
        // Setup
        final InputStream inputStream = new ByteArrayInputStream("content".getBytes());

        // Run the test
        CsvUtil.read(inputStream, StandardCharsets.UTF_8, false, new int[]{0});
    }

    @Test
    public void testWrite() throws Exception {
        String[] arr1 = new String[]{"aaa", "bbb"};
        String[] arr2 = new String[]{"北京", "上海"};
        String[] arr3 = new String[]{"eee", "fff", "ggg"};
        // Setup
        final List<String[]> list = new ArrayList<>();
        list.add(arr1);
        list.add(arr2);
        list.add(arr3);

        // Run the test
        CsvUtil.write(list, "/Users/nanxuan/temp/test11.csv", false);

        // Verify the results
    }

    @Test(expected = Exception.class)
    public void testWrite_ThrowsException() throws Exception {
//        // Setup
//        final List<String[]> list = Arrays.asList(new String[]{"value"});
//
//        // Run the test
//        CsvUtil.write(list, "/Users/nanxuan/temp/test11.csv", false);
    }

}
