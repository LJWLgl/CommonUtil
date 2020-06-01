package io.github.ljwlgl.util;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrintUtilTest {

    private PrintUtil printUtilUnderTest;

    @Before
    public void setUp() {
        printUtilUnderTest = new PrintUtil();
    }

    @Test
    public void testPrintThreadPoolStatus() {
        // Setup
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 0, 0L, TimeUnit.MILLISECONDS, null);
        final Logger logger = null;

        // Run the test
        printUtilUnderTest.printThreadPoolStatus(executor, logger);

        // Verify the results
    }

    @Test
    public void testPrintThreadPoolStatus1() {
        // Setup
        final Logger logger = null;
        final ThreadPoolExecutor executors = new ThreadPoolExecutor(0, 0, 0L, TimeUnit.MILLISECONDS, null);

        // Run the test
        printUtilUnderTest.printThreadPoolStatus(logger, executors);

        // Verify the results
    }
}
