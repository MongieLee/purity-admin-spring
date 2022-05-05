## 数据库灌入
启动容器，测试用没有持久化映射
`docker run --name [customName] -e MYSQL_ROOT_PASSWORD=[password] -e MYSQL_DATABASE=demo -p 3306:3306 -d mysql:5.7`

也可以自行创建数据库，并修改对应的链接配置

执行`mvn flyway:migrate`或在IDEA中找到maven插件的flayway双击执行`migrate`

## Swagger2接口文档
项目启动后，访问 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)