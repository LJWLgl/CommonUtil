## 概述
Java开发常用工具类总结，如果对你有用欢迎点个start，此外，如果你有兴趣和我一起维护该仓库，欢迎提PR或者issue，让我们为拥有一个更强大的工具库而一同努力。

## 导入项目
```
<dependency>
      <groupId>io.github.ljwlgl</groupId>
      <artifactId>common-util</artifactId>
      <version>2.0.7</version>
</dependency>
```
## 工具类介绍

### **FastJsonUtil(FastJson工具类)**
借助FastJson实现序列化和反序列，同时自己实现了Json节点增删改，以及Json关键字段脱敏
- **toJsonString(Object object)**, 序列化Json
- **toJsonString(Object object, String... reAttrs)**，序列化Json转时删除不必要的属性
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

### **EmailUtil(邮件发送工具类)**
邮件工具类是通过JavaEmail实现，企业级项目一般都会专门的服务去发送邮件，但如果自己的小Demo，用工具类发送Email也未尝不可。示例Demo如下，有兴趣的可以fork代码自己研究一下，代码有详情的注释。
```
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
### **ProtobufUtil(提供Protobuf格式的序列化和反序列)**
### GZIPUtil(提供Gzip)
- **compress(String str)**，字符串压缩为GZIP字节数组
- **compress(String str, String encoding)**，字符串压缩为GZIP字节数组
- **uncompress(byte[] bytes)**，GZIP解压缩

## Contributor

下面是笔主收集的一些对本仓库提过有价值的pr或者issue的朋友，人数较多，如果你也对本仓库提过不错的pr或者issue的话，你可以加我的微信与我联系。下面的排名不分先后！

<a href="https://github.com/LJWLgl">
    <img src="https://avatars1.githubusercontent.com/u/22522146?s=460&u=34378925405f18325ea493aa7df788410d6204e3&v=4" width="45px">
</a>

## 更新日志
- 2020-05-22，新增LanguageUtil & 升级pom
