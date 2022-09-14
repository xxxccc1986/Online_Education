package top.year21.onlineedu.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/31 22:32
 */
@SpringBootApplication
@ComponentScan(basePackages = "top.year21.onlineedu")
@EnableFeignClients
@EnableDiscoveryClient
public class ServiceOrder8007 {
    public static void main(String[] args){
        SpringApplication.run(ServiceOrder8007.class,args);
    }
}
