package cn.mgl.purity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan(basePackages = {"cn.mgl.purity.dao"})
@EnableSwagger2
@EnableTransactionManagement
public class PureApplication {

    public static void main(String[] args) {
        SpringApplication.run(PureApplication.class, args);
    }

}
