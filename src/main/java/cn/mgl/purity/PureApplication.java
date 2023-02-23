package cn.mgl.purity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan(basePackages = {"cn.mgl.purity.dao"}) // 设置mybatis接口包
@EnableSwagger2 // 开启Swagger启动器
@EnableTransactionManagement // 开始事务管理
public class PureApplication {

    public static void main(String[] args) {
        SpringApplication.run(PureApplication.class, args);
    }

}
