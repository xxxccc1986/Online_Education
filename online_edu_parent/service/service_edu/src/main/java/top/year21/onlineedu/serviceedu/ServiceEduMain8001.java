package top.year21.onlineedu.serviceedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/18 16:07
 */
@SpringBootApplication
@ComponentScan(basePackages = {"top.year21.onlineedu"}) //扫描top.year21.onlineedu包下的所有组件
@EnableDiscoveryClient //将此服务注册进nacos
@EnableFeignClients //开启feign的服务调用
@EnableHystrix //开启hystrix的熔断机制
public class ServiceEduMain8001 {
    public static void main(String[] args){
        SpringApplication.run(ServiceEduMain8001.class,args);
    }
}
