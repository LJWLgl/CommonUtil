package io.github.ljwlgl.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zqgan
 * @since 2018/10/31
 **/

public class PropertiesUtil {

    /**
     * Resource目录下*.properties文件
     * @param key key
     * @return value
     */
    public static String getProperty(String path, String key) {
        InputStream in = PropertiesUtil.class.getResourceAsStream(path);
        Properties prop = new Properties();
        try {
            prop.load(in);
            return prop.getProperty(key);
        } catch (IOException e) {
            return null;
        }
    }

}
