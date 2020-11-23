package io.github.ljwlgl.util;

import io.github.ljwlgl.fileutil.PropertiesUtil;
import org.junit.Test;

/**
 * @author ：lzz
 * @BelongsProject: io.github.ljwlgl.util
 * @date ：Created in 2020/6/9 16:04
 * @description ：
 * @modified By：
 */

public class FileUtilTest {

    @Test
    public void getSuffix() {
        String rs = PropertiesUtil.getSuffix("aaa.txt");
        String rs1 = PropertiesUtil.getSuffix("");
    }
}
