# 项目

常用总结,用来帮助自己快速构建一个项目



## MyBatis-plus的使用

可以通过数据库自动生成相应的实体,service，mapper类



导入相关依赖

JAXB 是一个用于在Java对象和XML数据之间进行转换的Java API。

```xml
   		<dependency>
            <groupId>javax.xml.bind</groupId>
            <artifactId>jaxb-api</artifactId>
            <version>2.3.1</version>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
            <version>3.5.5</version>
        </dependency>
 		<dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
   		<dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-3-starter</artifactId>
            <version>1.2.23</version>
        </dependency>
```

扫描mapper所在包

```java
@MapperScan("com.wzy.blog.mapper")
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}
```

配置文件

```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      url: jdbc:mysql:///wzy9858blog
      username: root
      password: mima
      driver-class-name: com.mysql.cj.jdbc.Driver
  jackson: #数据库涉及到时间
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    map-underscore-to-camel-case: true #驼峰命名
  type-aliases-package: com.wzy.pojo #起别名
  mapper-locations: classpath:/mapper/*.xml
```

相关的mapper和service和serviceimpl实现类

```java
public interface ArticlesMapper extends BaseMapper<Articles> {}

public interface ArticlesService extends IService<Articles> {}

@Service
public class ArticlesServiceImpl extends ServiceImpl<ArticlesMapper, Articles>
    implements ArticlesService{}

//mapper中
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.wzy.blog.mapper.ArticlesMapper">

    <resultMap id="BaseResultMap" type="com.wzy.blog.pojo.Articles">
            <id property="id" column="id" jdbcType="INTEGER"/>
            <result property="articleTitle" column="article_title" jdbcType="VARCHAR"/>
            <result property="articleContent" column="article_content" jdbcType="VARCHAR"/>
            <result property="createdAt" column="created_at" jdbcType="TIMESTAMP"/>
            <result property="updatedAt" column="updated_at" jdbcType="TIMESTAMP"/>
            <result property="isEncrypted" column="is_encrypted" jdbcType="TINYINT"/>
            <result property="tags" column="tags" jdbcType="VARCHAR"/>
            <result property="popularity" column="popularity" jdbcType="INTEGER"/>
            <result property="homeDisplayImageUrl" column="home_display_image_url" jdbcType="VARCHAR"/>
            <result property="isPinned" column="is_pinned" jdbcType="INTEGER"/>
            <result property="owner" column="owner" jdbcType="VARCHAR"/>
    </resultMap>

    <sql id="Base_Column_List">
        id,article_title,article_content,
        created_at,updated_at,is_encrypted,
        tags,popularity,home_display_image_url,
        is_pinned,owner
    </sql>
</mapper>
```

分页插件的使用

```java
@Configuration
public class MyConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //mybatis-plus插件集合, 任何mybatis-plus插件加入到这个集合即可
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
```

```java
@Autowired
	ArticlesMapper articlesMapper;
	@Test
	public void getList(){
		//设置分页参数  第几页 页容量
		Page<Articles> page = new Page<>(1, 5);
		articlesMapper.selectPage(page, null);
		//获取分页数据
		List<Articles> list = page.getRecords();
		list.forEach(System.out::println);
		System.out.println("当前页："+page.getCurrent());
		System.out.println("每页显示的条数："+page.getSize());
		System.out.println("总记录数："+page.getTotal());
		System.out.println("总页数："+page.getPages());
		System.out.println("是否有上一页："+page.hasPrevious());
		System.out.println("是否有下一页："+page.hasNext());
	}
```

自定义分页插件的使用

```java
    IPage<Articles> selectPageVo(IPage<?> page, Integer id);//在mapper中 并且写出相应的语句 调用这个方法,剩下与上方一致
```





## 参数的接收

param参数接收（名字相同可以省略`@RequestParam("name")`）

请求:`http://localhost:8080/param/data?name=xx&stuAge=18`

```
@RequestParam("name") String name, @RequestParam("stuAge") int age){
@RequestParam(value = "stuAge",required = false,defaultValue = "18")
//可以设置默认值
```

多个相同的param参数可以使用列表接收

请求:`http://localhost:8080/param/mul?hbs=吃&hbs=喝v`

```java
@RequestParam List<String> hbs   //java函数内的参数
```

路径参数的接收

```java
@GetMapping("/user/{id}/{name}")
@ResponseBody
public String getUser(@PathVariable Long id, 
                      @PathVariable("name") String uname) 
```





## 相应工具类

```java
@Data
public class R {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();
    private R(){}

    public static R ok(){
        R r = new R();

        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }



}
```

```java
public class ResultCode {
    public static Integer SUCCESS = 200;  //成功码
    public static Integer ERROR = 404;  //失败码
}
```



## JWT的使用

