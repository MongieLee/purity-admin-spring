# Purity Admin
基于`Spring`系列搭建的后端系统，主要使用到的技术：<br/>
`Springboot，Mybatis，Spring-Security，JWT，Maven，Docker`<br/>
在这个基础架构上，可以快速的进行二次开发项目

- 菜单管理：基于前端路由配置的菜单管理，及按钮权限
- 部门管理：配置公司组织架构
- 角色管理：针对角色进行菜单和按钮的分配，需要结合菜单管理使用
- 用户管理：对系统用户进行配置
- 修改密码：可以修改当前用户密码，用户管理模块中只能修改其他非密码信息

## 项目启动
### 数据库灌入
Docker启动容器，测试用没有持久化映射，需要的自行增加
`docker run --name [customName] -e MYSQL_ROOT_PASSWORD=[password] -e MYSQL_DATABASE=demo -p 3306:3306 -d mysql:5.7`

也可以自行创建数据库，并修改对应的链接配置<br/>
执行`mvn flyway:migrate`或在IDEA中找到maven插件的`flyway`双击执行`migrate`

## Swagger2接口文档
项目启动后，访问 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
