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

