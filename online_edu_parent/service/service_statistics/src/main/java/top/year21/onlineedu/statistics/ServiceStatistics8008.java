package top.year21.onlineedu.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/1 23:35
 */
@SpringBootApplication
@ComponentScan(basePackages = {"top.year21.onlineedu"})
@MapperScan("top.year21.onlineedu.statistics.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling //开启springboot集成的定时任务
public class ServiceStatistics8008 {
    public static void main(String[] args){
        SpringApplication.run(ServiceStatistics8008.class,args);
    }
}
