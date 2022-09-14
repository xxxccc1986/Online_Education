package top.year21.onlineedu.servicesms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/28 16:23
 */
@SpringBootApplication
@ComponentScan(basePackages = {"top.year21.onlineedu"})
public class ServiceSMS8005 {
    public static void main(String[] args){
        SpringApplication.run(ServiceSMS8005.class,args);
    }
}
