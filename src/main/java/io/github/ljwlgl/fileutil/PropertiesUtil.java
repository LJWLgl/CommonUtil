package io.github.ljwlgl.fileutil;

import io.github.ljwlgl.util.StringUtil;

import java.io.*;
import java.util.Properties;

/**
 * @author zqgan
 * @since 2018/10/31
 **/

public class PropertiesUtil {

    public static String DEFAULT_PATH_PREFIX = "/gzq/config/ares";

    /**
     * Resource目录下*.properties文件
     *
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

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        if (StringUtil.isEmptyOrNull(fileName)) return "";
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;

    }

    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(DEFAULT_PATH_PREFIX + fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
