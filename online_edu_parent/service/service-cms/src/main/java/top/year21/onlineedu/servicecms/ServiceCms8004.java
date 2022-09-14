package top.year21.onlineedu.servicecms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/27 19:17
 */
@SpringBootApplication
@ComponentScan(basePackages = {"top.year21.onlineedu"})
public class ServiceCms8004 {
    public static void main(String[] args){
        SpringApplication.run(ServiceCms8004.class,args);
    }
}
