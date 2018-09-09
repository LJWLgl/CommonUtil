### 概述
开发常用工具类总结，如果对你有用欢迎star~

### 导入项目
```
<dependency>
      <groupId>io.github.ljwlgl</groupId>
      <artifactId>common-util</artifactId>
      <version>2.0.1</version>
</dependency>
```

### 该项目是日常业务开发的工具类，目前包含以下部分：
1. 时间转换工具类
2. 邮件发送工具类
3. http请求工具类
4. 序列化工具类
5. 获取本地IP和主机名工具类
6. BigDecimal计算类

### 时间转换工具类
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

### 序列化工具类
对于web服务，我们需要把Object序列化成Json，另外也要Json也需要被反序列化成Object，这几乎是后端开发每天都要做的事，JacksonUtil提供了静态方法方便对其操作，例如：
- **obj2json(Object obj)**，Object -> Json
- **obj2jsonSafe(Object obj)**，Object -> Json，内部处理了Exception
- **obj2jsonForSoaEntity(Object obj)**，Object -> Json，针对SOA服务
- **removeSchema(Object obj)**，把soa 契约类的schema字段删除
- **json2pojo(String jsonStr, Class<T> clazz)**，把jsonStr反序列化成Class
- **json2pojoSafe(String jsonStr, Class<T> clazz)**，把jsonStr反序列化成Class，内部处理了异常
- ** json2list(String jsonArrayStr, Class<T> clazz)**，把jsonStr反序列化成Object List
- **json2map(String jsonStr)**，把jsonStr反序列化成map
- **json2map(String jsonStr, Class<T> clazz)**，把jsonStr反序列成包含JavaBean的Map

### 获取本地IP和主机名工具类
拿到本机IP不是一件困难的事，但是拿到正确的就比较难了，本工具类，是参考了开源项目[CAT](https://github.com/dianping/cat)内部的实现，提供静态方法如下：
- **getLocalHostAddress()**，返回本机IP
- **getLocalHostName()**，返回主机名
- **getLocalInetAddress**，返回InetAddress
### BigDecimal计算类
在和钱打交道的服务里，经常会用到BigDecimal类，下面提供一些他的计算方法
- **subtract(double x, double y)**
- **add(double x, double y)**
- **multiply(double x, double y)**
- **divide(double x, double y, int scale)**
- **roundUp(double val)**，近似处理，向上取整
- **roundUp(double val, int scale)**， 近似处理，向上取整，scale保留点的位数
- **roundDown(double val)**
- **roundDown(double val, int scale)**

###  http请求工具类
在服务里请求别的接口也是很常见的事
- **doGet(String url, Map<String, String> queryParam)**，get请求，queryParam是参数
- **doPost(String url, Map<String, String> params, String json)**，json 请求体内是json字符串
- ... doPost的重载方法


###  邮件发送工具类
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

