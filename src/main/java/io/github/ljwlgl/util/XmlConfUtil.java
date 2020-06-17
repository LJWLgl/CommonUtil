package io.github.ljwlgl.util;


import javax.xml.bind.*;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;


/**
 * Created with IntelliJ IDEA.
 * User: 长歌
 * Date: 2020/6/17
 * Description: 基于JAXB完成 xml 与 javaBean 的互转进行xml配置文件的读取，JAXB相关注解在 javax.xml.bind.annotation包下
 */
public class XmlConfUtil {


    private XmlConfUtil() {

    }

    /**
     * 获取 XML 装配器
     *
     * @return 装配器
     */
    private static Marshaller getMarshaller(JAXBContext context) {
        try {
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            return marshaller;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取解码器
     *
     * @return 解码器
     */
    private static Unmarshaller getUnmarshaller(JAXBContext context) {
        try {
            return context.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 xml 转换为 javaBean
     *
     * @param in  输入流
     * @param <T> javaBean 类类型
     * @return javaBean 实例
     */
    public static <T> T xml2JBean(Class<?> clazz, InputStream in) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            return (T) getUnmarshaller(context).unmarshal(in);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将使用注解的 javaBean 转换为 xml
     *
     * @param instance javaBean 实例
     * @param out      输出流
     */
    public static void jBean2Xml(Object instance, OutputStream out) {
        try {
            JAXBContext context = JAXBContext.newInstance(instance.getClass());
            Objects.requireNonNull(getMarshaller(context)).marshal(instance, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成 Schema 文件
     *
     * @return Schema文件内容
     */
    public static String generateSchema(Class<?> clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            context.generateSchema(new SchemaOutputResolver() {
                @Override
                public Result createOutput(String namespaceUri, String suggestedFileName) {
                    StreamResult result = new StreamResult(System.out);
                    result.setSystemId(suggestedFileName);
                    return result;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
