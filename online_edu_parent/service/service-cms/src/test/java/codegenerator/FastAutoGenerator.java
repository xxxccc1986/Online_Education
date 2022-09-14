package codegenerator;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/27 19:27
 */
public class FastAutoGenerator {
    public static void main(String[] args) {
        // 设置我们需要创建在哪的路径
        com.baomidou.mybatisplus.generator.FastAutoGenerator.create("jdbc:mysql://localhost:3306/online_edu?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false", "root", "root")
                .globalConfig(builder -> {
                    builder.author("year21") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C://Users//hcxs1986//IdeaProjects//online_edu_parent//service//service_usercenter//src//main//java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("top.year21.onlineedu") // 设置父包名
                            .moduleName("usercenter") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C://Users//hcxs1986//IdeaProjects//online_edu_parent//service//service_usercenter//src//main//resources//mybatis//mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("usercenter_member") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker 引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
