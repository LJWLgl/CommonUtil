package io.github.ljwlgl.util;

import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

public class CaptchaUtilTest {

    @Test
    public void testGenCaptcha() {
        assertEquals("result", CaptchaUtil.genCaptcha(0));
    }

    @Test
    public void testGenCaptchaImg() {
        // Setup

        // Run the test
        final BufferedImage result = CaptchaUtil.genCaptchaImg("captcha");

        // Verify the results
    }
}
