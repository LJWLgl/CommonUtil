package io.github.ljwlgl.util;

import io.github.ljwlgl.util.EmailUtil;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;

public class EmailUtilTest {

    @Before
    public void before() throws GeneralSecurityException {
        // 配置，一次即可
        EmailUtil.config(EmailUtil.SMTP_QQ(false), "xxx@qq.com", "xxxxxx");
    }

    @Test
    public void testSendText() throws MessagingException {
        System.out.println("test");
    }

}