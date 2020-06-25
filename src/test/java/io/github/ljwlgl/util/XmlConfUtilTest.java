package io.github.ljwlgl.util;


import org.junit.Test;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 长歌
 * Date: 2020/6/17
 * Description:
 */
public class XmlConfUtilTest {

    @Test
    public void testXml2JavaBean() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<computer id=\"1\">\n" +
                "    <create>2020-06-17 08:36:34</create>\n" +
                "    <memorySticks>\n" +
                "        <memory type=\"ddr3\">\n" +
                "            <name>海盗船 3000Hz</name>\n" +
                "            <size>16</size>\n" +
                "        </memory>\n" +
                "        <memory type=\"ddr3\">\n" +
                "            <name>七彩虹 3000Hz</name>\n" +
                "            <size>8</size>\n" +
                "        </memory>\n" +
                "    </memorySticks>\n" +
                "    <name>华硕-飞行堡垒 Fx60</name>\n" +
                "    <price>7000</price>\n" +
                "</computer>";
        ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        Computer computer = XmlConfUtil.xml2JBean(Computer.class, in);
        System.out.println(computer);
    }

    @Test
    public void testJavaBean2Xml() {
        Memory m1 = new Memory();
        m1.setName("海盗船 3000Hz");
        m1.setSize(16);
        m1.setType("ddr3");

        Memory m2 = new Memory();
        m2.setName("七彩虹 3000Hz");
        m2.setSize(8);
        m2.setType("ddr3");

        List<Memory> memorySticks = Arrays.asList(m1, m2);

        Computer computer = new Computer();
        computer.setMac("BC82-64JG-8G9A-H2GF");
        computer.setId(1);
        computer.setName("华硕-飞行堡垒 Fx60");
        computer.setPrice(7000);
        computer.setCreate(new Date());
        computer.setMemories(memorySticks);

        XmlConfUtil.jBean2Xml(computer, System.out);
    }

    @Test
    public void testGenerateSchema() {
        String schema = XmlConfUtil.generateSchema(Computer.class);
        System.out.println(schema);
    }

    @XmlRootElement // xml根元素注解，可以使用name指定根元素节点名称
//    @XmlType(propOrder = {"name", "price", "create", "memories"}) // 指定xml文件节点排列顺序，生成的一级子节点名称都必须包含在内
//    @XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)  // 按字母表顺序排序 XmlAccessOrder.NONE 无序
    static class Computer {
        private Integer id;
        private String name;
        private Integer price;
        private String mac;
        private Date create;
        private List<Memory> memories;

        public Computer() {
        }

        public String getMac() {
            return mac;
        }

        @XmlTransient   // 生成的xml文档中不包含此元素
        public void setMac(String mac) {
            this.mac = mac;
        }

        public Integer getId() {
            return id;
        }

        @XmlAttribute
        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        @XmlJavaTypeAdapter(DateAdapter.class)  // 实现复杂元素的自定义处理，也可以用来进行map集合(原生不支持)的处理
        @XmlElement
        public Date getCreate() {
            return create;
        }

        public void setCreate(Date create) {
            this.create = create;
        }

        @XmlElementWrapper(name = "memorySticks")
        @XmlElement(name = "memory")
        public List<Memory> getMemories() {
            return memories;
        }

        public void setMemories(List<Memory> memories) {
            this.memories = memories;
        }

        @Override
        public String toString() {
            return "Computer{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    ", mac='" + mac + '\'' +
                    ", create=" + create +
                    ", memorySticks=" + memories +
                    '}';
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    static class Memory {
        private String name;

        private Integer size;

        @XmlAttribute
        private String type;

        public Memory() {
        }

        public String getName() {
            return name;
        }

        public void setName(String mName) {
            this.name = mName;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Memory{" +
                    "name='" + name + '\'' +
                    ", size=" + size +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    static class DateAdapter extends XmlAdapter<String, Date> {

        private static final DateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public Date unmarshal(String date) throws Exception {
            return SDF.parse(date);
        }

        @Override
        public String marshal(Date date) {
            return SDF.format(date);
        }
    }
}
