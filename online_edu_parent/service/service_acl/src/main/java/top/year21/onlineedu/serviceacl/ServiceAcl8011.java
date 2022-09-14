package top.year21.onlineedu.serviceacl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/4 1:05
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"top.year21.onlineedu"})
public class ServiceAcl8011 {
    public static void main(String[] args){
        SpringApplication.run(ServiceAcl8011.class,args);
    }

}
