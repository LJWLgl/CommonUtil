## 目录
- [概述](#概述)
- [导入项目](#导入项目)
- [工具类介绍](#工具类介绍)
    - [FastJsonUtil(FastJson工具类)](#fastjsonutilfastjson工具类)
    - [DateUtil(时间转换工具类)](#dateutil时间转换工具类)
    - [CsvUtil(CSV文件工具类)](#csvutilcsv文件工具类)
    - [RegexUtil(正则工具类)](#regexutil正则工具类)
    - [LanguageUtil(语言工具类)](#languageutil语言工具类)
    - [NetworkUtil(获取本地IP和主机名工具类)](#networkutil获取本地ip和主机名工具类)
    - [BigDecimalUtil(BigDecimal计算工具类)](#bigdecimalutilbigdecimal计算工具类)
    - [HttpUtil(http请求工具类)](#httputilhttp请求工具类)
    - [CoordinateTransformUtil(坐标系转换工具类)](#coordinatetransformutil坐标系转换工具类)
    - [EncodeDecodeUtil(编码与解码工具类)](#encodedecodeutil编码与解码工具类)
    - [ProtobufUtil(提供Protobuf格式的序列化和反序列)](#protobufutil提供protobuf格式的序列化和反序列)
    - [DeepCopyUtil(深拷贝工具类)](#deepcopyutil深拷贝工具类)
    - [GZIPUtil(通过Gzip算法压缩和解压)](#gziputil通过gzip算法压缩和解压)
    - [CaptchaUtil(验证码工具类)](#captchautil验证码工具类)
    - [UrlParamsUtil（对URL参数处理的工具类）](#urlparamsutil对url参数处理的工具类)
    - [LangArabicNumConvertUtil(语言数字与阿拉伯数字转换工具类)](#langarabicnumconvertutil语言数字与阿拉伯数字转换工具类)
    - [StringUtil(字符串工具类)](#stringutil字符串工具类)
    - [EmailUtil(邮件发送工具类)](#emailutil邮件发送工具类)
    - [XmlConfUtil(解析XMl配置工具类)](#xmlconfutil解析XMl配置工具类)
- [关于开源](#关于开源)
- [Contributor](#contributor)
- [更新日志](#更新日志)

## 概述

本仓库是Java开发常用工具类的总结，旨在追求给大家提供一个轻便简单的工具类库，同时能够cover住大家80%以上的工具类的需求。如果对你有用欢迎点个star，此外，如果你有兴趣和我一起维护该仓库，欢迎提PR或者issue，让我们为拥有一个更强大的工具库而一同努力。

## 导入项目
```
<dependency>
      <groupId>io.github.ljwlgl</groupId>
      <artifactId>common-util</artifactId>
      <version>2.1.0</version>
</dependency>
```
## 工具类介绍
### **FastJsonUtil(FastJson工具类)**
借助FastJson实现序列化和反序列，同时自己实现了Json节点增删改，以及Json关键字段脱敏
- **toJsonString(Object object)**, 序列化Json
- **toJsonString(Object object, String... reAttrs)**，序列化Json转时删除不必要的属性
- **isJSON(String str)**，判断是否是Json串
- **eval(String json, String path)**, 从Json中根据Path取出对象
- **eval(String json, String path, Class<T> clz)**, 根据path从json中取出结果并反序列成JavaBean
- **List<T> evals(String json, String path, Class<T> clz)**, 根据path从json中取出结果并反序列成JavaBean，该方法只支持array
- **remove(String json, String path)**，根据path删除指定属性
- **replace(String json, String path, Object value)**，根据path替换指定属性, 只支持JSONObject
- **replaceNew(String json, String path, String value)**，根据path替换指定属性, 支持JSONArray，但是不支持路径表达式
- **put(String json, String path, Object obj)**，根据path向json加入指定对象，只支持JSONObject
- **parseObject(String json, Class<T> clazz)**，反序列化Json
- **List<T> parseArray(String json, Class<T> clazz)**，反序列化List
- **String getJsonValue(String jsonStr, String key)**，获取Json字符串某节点的值
- **containsKey(String jsonStr, String key)**，判断Json串是否包含某个节点
- **containsKey(String jsonStr, String... keys)**，判断Json串是否包含某些属性
- **String encryptJson(String json, List<String> paths)**，加密json的节点属性，可以对关键字段脱敏，支持JsonArray
- **String encryptToJson(JSONObject preObject, String[] paths)**，同上


### **DateUtil(时间转换工具类)**
在日常开发过程中，我们进程会需要把Date转成String，或者把String转成Date，该工具类大致上能满足我们日志开发需要：
- **getCurrentDate()**，获取当期日志，只包含日期
- **calcIntervalDays(Date date1, Date date2)**，计算两个日期间隔的天数
- **dayOfWeek(Date date)**，返回data对应的是星期几
- **getTodayMinutes()**，获取今天的分钟数，如今天18:05，则返回1805
- **getIntervalDate(Date time, int days)**，获取指定间隔天数的日期，比如昨天 getIntervalDate(new Date(), -1)
- **dateToShortDateString(Date date)**，将date转成String，输出String只包含年月日
- **dateToString(Date date)**， 将date转成String，输出String包含年月日时分秒
- **stringToDate(String dateStr)**，将String转成Date，默认时区东八区，TimeZone.getTimeZone("Asia/Shanghai")
- **dateToVoString(Date date)** ，后端经常会根据传入Date生成View层的String传给前端，该方法计算成相应中文，会例如：
    - 1分钟内：刚刚
    - 超过1分钟并在1小时内：某分钟前 （1分钟前）
    - 超过1小时并在当日内：某小时前（1小时前）
    - 昨天：昨天 + 小时分钟（昨天 08:30）
    - 昨天之前并在当年内：某月某日 + 小时分钟（1月1日 08:30）
    - 隔年：某年某月某日 + 小时分钟（2017年1月1日 08:30）

### **CsvUtil(CSV文件工具类)**
csv作为开发常常需要处理的文件格式，本项目主要提供以下工具方法：
- **List<String[]> read(String filePath, String code, boolean needHeader)**，读取CSV文件，（参数说明，needHeader：是否需要列标题;）
- **List<String[]> read(InputStream inputStream, Charset charset, boolean needHeader, int[] columns)**，读取CSV文件，（参数说明，needHeader：是否需要列标题；columns： 指定读取csv文件的哪几列，如果为null，则读取全部列）
- **List<String[]> read(...)**，读取CSV文件的多个重载方法
- **write(List<String[]> list, String filePath, boolean append)**，写入CSV文件（参数说明，append：是否以追加的方式写入）

### **RegexUtil(正则工具类)**
- **isMobileExact(CharSequence input)**，是否是手机号
- **isEmail(CharSequence input)**，是否是email
- **isURL(CharSequence input)**，是否是URL
- **isIP(CharSequence input**，是否是IP
- **isMatch(String regex, CharSequence input)**，是否匹配正则表达式
- **getMatches(String regex, CharSequence input)**，获取正则表达式的部分
- ...

### **LanguageUtil(语言工具类)**
多语言场景工具类，可以借助下面工具类判断字符是哪种locale
- **isOnlyLetter(String str)**，是否只有字母
- **isLetter(String str)**，是否有字母和空格
- **isChinese(String str)**，是否只有汉字和空格
- **isLetterAndNumber(String str)**，是否只有字母、数字和空格
- **isChineseAndNumber(String str)**，是否只有汉字、数字和空格
- **isKoreanAndNumber(String str)**，是否只有韩语、数字和空格
- **isJapanAndNumber(String str)**，是否只有日文和数字

### **NetworkUtil(获取本地IP和主机名工具类)**
拿到本机IP不是一件困难的事，但是拿到正确的就比较难了，本工具类，是参考了开源项目[CAT](https://github.com/dianping/cat)内部的实现，提供静态方法如下：
- **getLocalHostAddress()**，返回本机IP
- **getLocalHostName()**，返回主机名
- **getLocalInetAddress**，返回InetAddress

### **BigDecimalUtil(BigDecimal计算工具类)**
在和钱打交道的服务里，经常会用到BigDecimal类，下面提供一些他的计算方法
- **subtract(double x, double y)**
- **add(double x, double y)**
- **multiply(double x, double y)**
- **divide(double x, double y, int scale)**
- **roundUp(double val)**，近似处理，向上取整
- **roundUp(double val, int scale)**， 近似处理，向上取整，scale保留点的位数
- **roundDown(double val)**
- **roundDown(double val, int scale)**

### **HttpUtil(http请求工具类)**
在服务里请求别的接口也是很常见的事
- **doGet(String url, Map<String, String> queryParam)**，get请求，queryParam是参数
- **doPost(String url, Map<String, String> params, String json)**，json 请求体内是json字符串
- ... doPost的重载方法

### **CoordinateTransformUtil(坐标系转换工具类)**
各坐标系相互转换，如bd09，wgs84，gcj02
- **convertLatLonByCoordinate(String newCoordinateType, String originalCoordinateType, double lat, double lon)**，将原本坐标系的经纬度转换成新的坐标系的经纬度，支持wgs84转bd09，bd09T转gcj02d等

### **EncodeDecodeUtil(编码与解码工具类)**
- **encodeWithMD5(String str)**， 对字符串进行MD5加密
- **encodeWithSHA1(String str)**，对字符串进行SHA1加密
- **encodeWithSHA256(String str)**，对字符串进行SHA-256加密
- **encode(String algorithm, String str)**，通过指定算法对字符串加密
- **encodeBase64(String str)**，对字符串进行Base64编码
- **decodeBase64(String str)**，对字符串进行Base64解码
- **encodeUrl(String str)**，对URL编码
- **decodeUrl(String str)**，对URL解码

### **ProtobufUtil(提供Protobuf格式的序列化和反序列)**
- **<T> byte[] serialize(T obj)**，序列化对象
- **<T> byte[] serializeList(List<T> objList)**，序列化数组
- **<T> T unSerialize(byte[] data,Class<?> clazz)**，反序列化对象
- **<T> List<T> unSerializeList(byte[] data, Class<T> clazz)**，反序列化数组

### **DeepCopyUtil(深拷贝工具类)**
- **Object depthClone(Object srcObj)**，单个对象的深拷贝，通过序列化与反序列的方式实现，所以srcObj对应的需实现java.io.Serializable接口
- **<T> List<T> listDepthClone(List<T> list)**，多个对象的深拷贝，srcObj对应的需实现java.io.Serializable接口

### **GZIPUtil(通过Gzip算法压缩和解压)**
- **compress(String str)**，字符串压缩为GZIP字节数组
- **compress(String str, String encoding)**，字符串压缩为GZIP字节数组
- **uncompress(byte[] bytes)**，GZIP解压缩

### **CaptchaUtil(验证码工具类)**

- **String genCaptcha(int count)**，生成指定位数为count的随机验证码
- **BufferedImage genCaptchaImg(String captcha)**，为一个验证码生成一个图片

### **UrlParamsUtil（对URL参数处理的工具类）**

- **String join(Map<String, String> map, String separator)**，将Map转成String, 可以指定分隔符，通常用于拼接URL后面的参数
- **Map<String, String> split(String paramsPath, String separator)**，解析ulr参数为map
-  **Map<String, String> split(String paramsPath)**，解析ulr参数为map，这里的separator参数为“=”
-  **Map<String, String> build(String ... keyValues)**，将keyValues转成Map
-  **add(Map<String, String> originMap, String ... keyValues)**，在原Map添加keyValues

### **LangArabicNumConvertUtil(语言数字与阿拉伯数字转换工具类)**
- **String lang2ArabicNumber(String word, String majorLocale)**，将语言数字转成阿拉伯数字，目前只支持英语和中文的转换，使用请参考示例[LangArabicNumConvertUtil](#LangArabicNumConvertUtil)（参数说明，majorLocale：中文zh或英语en）
- **String arabic2LangNumber(String word, String majorLocale)**，将阿拉伯数字转成语言数字，会转成带进制的语言数字（如，16会转成十六），使用请参考示例[LangArabicNumConvertUtil](#LangArabicNumConvertUtil)
- **String arabic2NoDecimalLangNumber(String word, String majorLocale)**，将阿拉伯数字转成语言数字，会转成不带进制的语言数字（如，16会转成一六），使用请参考示例[LangArabicNumConvertUtil](#LangArabicNumConvertUtil)
### **StringUtil(字符串工具类)**
对于字符串工具类，优先推荐使用org.apache.commons.lang3下的StringUtils以及java.lang.String的自带方法，本工具类只是补充了一些个别方法。
- **String replaceString(String str, Map<String, String> oldNewMap)**， 批量替换字符
- **String subArr2String(int i, int j, char[] arr)**，将字符数组的子集合成新的字符串
- **String subArr2String(int i, int j, String[] arr, String separator)**，将字符串数组的子串合成一个新的字符串
- **double castDouble(Object obj, double defaultValue)**，转为double类型 ，如果obj为null或者空字符串或者格式不对则返回defaultValue
- **double cast...(Object obj, double defaultValue)**，转换成对应的基础类型

### **EmailUtil(邮件发送工具类)**

邮件工具类是通过JavaEmail实现，企业级项目一般都会专门的服务去发送邮件，但如果自己的小Demo，用工具类发送Email也未尝不可。
可参考[EmailUtil](#EmailUtil)如下，有兴趣的可以fork代码自己研究一下，代码有详情的注释。

### **xmlconfutil解析XMl配置工具类**
解析工具通过JAXB实现，主要用于xml配置文件的实例化，以及生成配置类javaBean对应的xml
- **<T> T xml2JBean(Class<?> clazz, InputStream in)**，将xml实例化为T类型实例
- **void jBean2Xml(Object instance, OutputStream out)**，将配置类实例instance解析为xml

## 关于开源
本项目是开源项目，若有摘取本项目的代码，请注明出处！（码字不易，请尊重开源精神）

## Contributor

下面是笔者收集的一些对本仓库提过有价值的pr或者issue的朋友，如果你也对本仓库提过不错的pr或者issue的话，你可以发邮件（2715815264@qq.com）与我联系。

<a href="https://github.com/LJWLgl">
    <img src="https://avatars1.githubusercontent.com/u/22522146?s=460&u=34378925405f18325ea493aa7df788410d6204e3&v=4" width="45px">
</a>
<a href="https://github.com/fansengithub">
    <img src="https://avatars1.githubusercontent.com/u/16862948?s=400&v=4" width="45px">
</a>
<a href="https://github.com/927376346">
    <img src="https://avatars2.githubusercontent.com/u/34704332?s=400&v=4" width="45px">
</a>
<a href="https://github.com/drinkagain">
    <img src="https://avatars2.githubusercontent.com/u/29560256?s=400&u=03abc1be6f633c0afbbfc07ec9bdeb9fd3e86a09&v=4" width="45px">
</a>
<a href="https://github.com/lizeze">
    <img src="https://avatars2.githubusercontent.com/u/33169073?s=400&u=f48fa869f2a1739f7716ac80c181300473833796&v=4" width="45px">
<a href="https://github.com/leithda">
    <img src="https://avatars1.githubusercontent.com/u/18017935?s=460&u=11a8d9036b8560dc6b1d335e9b1b4f8f5bdbdf48&v=4" width="45px">	
</a>


**简单说明PR原则**

- 注释需要完备，应该对新增的每个方法标注方法说明，同时对传入参数和返回参数也要相应的说明
- 充分的Unit Test，保证每行代码和分支都要覆盖到
- 代码规范，请遵循[阿里巴巴Java开发手册](https://yq.aliyun.com/articles/69327)

## 使用示例
### LangArabicNumConvertUtil
```java
// 中文转阿拉伯数字示例
LangArabicNumConvertUtil.lang2ArabicNumber("北京三里墩五星小区第陆拾肆栋六零二室", "zh");
// 英文转阿拉伯数字示例
LangArabicNumConvertUtil.lang2ArabicNumber("six six six Beijing abnormalities mottoes Litun two hundred and sixties-five Hotel seven thousand eight hundred and ninety-four", "en")
// 阿拉伯数字转中文
LangArabicNumConvertUtil.arabic2LangNumber("北京3里墩5星小区第64栋602室", "zh")

// 输出
// 北京3里墩5星小区第64栋602室
// 666 beijing abnormalities mottoes litun 200 and sixties-five hotel 7894
// 北京三里墩五星小区第六十四栋六百零二室
```

### EmailUtil
```java
public class EmailUtilTest {
    @Before
    public void before() throws GeneralSecurityException {
        // 发送邮箱配置，授权密码可以去邮件运营商获取
        EmailUtil.config(EmailUtil.SMTP_QQ(false), "xxx@qq.com", "xxxxxx");
    }

    @Test
    public void testSendText() throws MessagingException {
        System.out.println("test");
    }

}
```

## 更新日志
- 2018年08月
	- 创建项目
- 2020年05月
	- 新增LanguageUtil、EncodeDecodeUtil、CaptchaUtil
	- 发布2.0.7、2.0.8、2.0.9版本
    - 修复BigDecimalUtil错误的构造方法
    - 补充一些method的注释
- 2020年06月
    - 发布2.1.0版本
    - 新增CsvUtil、LangArabicNumConvertUtil、StringUtil
    - 补充单元测试	
	 	
