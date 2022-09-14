package top.year21.onlineedu.servicevod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/26 0:54
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"top.year21.onlineedu"})
@EnableDiscoveryClient
public class ServiceVodMain8003 {
    public static void main(String[] args){
        SpringApplication.run(ServiceVodMain8003.class,args);
    }
}
