# forward-generation-starter
类似JPA根据实体生成表的一个   starter   仅限于mybatis

配置文件：.yml
```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3307/test?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8
    username: root
    password: tiger
    
    ## create 每次启动都会重新创建  update 进行更新
    ddl-auto: create #update,create
    
```    
 2.注解说明
（1） @EnableAutoForwardGeneration
 ```
   @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @Documented
  @Import(RegisterScaner.class)
  public @interface EnableAutoForwardGeneration
  {
    // 实体类所在的包路径
    String entityPackages(); 
    // 是否开启 自动生成表
    boolean  OnOff() default true;
  }

 ```
 （2）@LColumn
```
/**
 * <br>
 * 列注解
 * @author 永健
 * @since 2019/5/7 14:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface LColumn
{
    /**
     * <br>
     * 列长度 String 类型的默认为
     */
    int width() default 0;

    /**
     * <br>
     * 指定数据库类型
     */
    ColumnType type() default ColumnType.FIELDTYPE;

    /**
     * <br>
     * 列是否默认为空
     */
    boolean isNull() default true;

    /**
     * <br>
     * 是否有默认值
     */
    String defaultValue() default "";

    /**
     * <br>
     * 注释
     */
    String comment() default "";

}

```

（3）@LIncrement 
```
/**
 * <br>
 * 是否自增
 * @author 永健
 * @since 2019/5/7 15:01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface LIncrement
{
    IdType type() default IdType.NONE;
}

```

(4) @LNotTableField 非表中字段
```

/**
 * <br>
 * 排除非表中字段
 * @author 永健
 * @since 2019/5/7 15:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface LNotTableField
{
}
```

(5) @LTable
```
/**
 * <br>
 * 实体注解 指定表名否则转下划线
 * @author 永健
 * @since 2019/5/7 15:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface LTable
{
  String name() default "";
}

```

(5) @LTableId
```
/**
 * <br>
 * 主键注解
 * @author 永健
 * @since 2019/5/7 14:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface LTableId
{
    IdType type() default IdType.NONE;
}
```
