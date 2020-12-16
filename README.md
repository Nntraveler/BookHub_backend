# BookHub_backend

## 环境配置
于/main/java/resources下新建application.properties文件，补全下列参数

```
server.port=

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:数据库地址}:端口号/数据库名
spring.datasource.username=
spring.datasource.password=
spring.jpa.show-sql=true

#Redis 服务器连接地址
spring.redis.host=
# Redis 服务器连接端口
spring.redis.port=
# Redis 数据库索引（默认为 0）
spring.redis.database=0
# Redis 服务器连接密码（默认为空）
spring.redis.password=
#连接池最大连接数（使用负值表示没有限制）
spring.redis.jedis.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.jedis.pool.max-wait=-1
# 连接池中的最大空闲连接
spring.redis.jedis.pool.max-idle=8
# 连接池中的最小空闲连接
spring.redis.jedis.pool.min-idle=0
# 连接超时时间（毫秒）
spring.redis.timeout=0
#不使用redis作为存储库
spring.data.redis.repositories.enabled=false


spring.mail.host=
spring.mail.username=
spring.mail.password=
spring.mail.properties.mail.smtp.port=
spring.mail.from=
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.ssl.enable=true
spring.mail.default-encoding=utf-8

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

aliyun.oss.endpoint=
aliyun.oss.accessKeyId=
aliyun.oss.accessKeySecret=
aliyun.oss.bucketName=
aliyun.oss.folder=
aliyun.oss.webUrl=
```
