package top.year21.onlineedu.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/28 20:11
 */
@SpringBootApplication
@MapperScan("top.year21.onlineedu.usercenter.mapper")
@ComponentScan(basePackages = {"top.year21.onlineedu"})
@EnableDiscoveryClient
public class UserCenter8160 {
    public static void main(String[] args){
        SpringApplication.run(UserCenter8160.class,args);
    }
}
