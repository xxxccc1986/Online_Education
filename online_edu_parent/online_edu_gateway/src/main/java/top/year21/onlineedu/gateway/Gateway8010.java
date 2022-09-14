package top.year21.onlineedu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/3 21:46
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Gateway8010 {
    public static void main(String[] args){
        SpringApplication.run(Gateway8010.class,args);
    }
}
