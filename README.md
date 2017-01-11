本java程序使用了spring aop实现注解切换多dataSource,用于将oralce数据库中的用户表数据，迁移到mysql的gitlab数据库和rdc的zentao数据库中。

1. 数据库配置文件src/main/resources/dbconfig.properties中。

2. resources目录下的oracleToGitlab.txt和oralceToRdc.txt表示oralce用户表中的字段到mysql数据库表中的字段的对应关系。
(请按照格式填写),例如：
```
NAME    name
EMAIL   email
PASSWORD    encrypted_password
```
> 前者表示oralce数据库字段，后者表示mysql数据库表字段，用tab或多个空格分隔。

3. 运行(需要maven和jdk)
```
mvn clean compile exec:java -Dexec.mainClass="com.hand.App"
```
