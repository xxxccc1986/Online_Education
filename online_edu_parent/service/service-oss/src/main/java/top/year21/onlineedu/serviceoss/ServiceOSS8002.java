package top.year21.onlineedu.serviceoss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/21 16:03
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //不自动装配数据源，不排除有可能报错
@ComponentScan(basePackages = {"top.year21.onlineedu"})
public class ServiceOSS8002 {
    public static void main(String[] args){
        SpringApplication.run(ServiceOSS8002.class,args);
    }
}
