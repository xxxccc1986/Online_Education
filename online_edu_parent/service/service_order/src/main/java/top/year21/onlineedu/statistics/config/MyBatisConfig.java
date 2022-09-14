package top.year21.onlineedu.statistics.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/18 16:20
 */
@Configuration
@MapperScan("top.year21.onlineedu.order.mapper")
public class MyBatisConfig {

    /**
     * Description : 注册MyBatisPlus的分页插件
     **/
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        //拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return interceptor;
    }
}
