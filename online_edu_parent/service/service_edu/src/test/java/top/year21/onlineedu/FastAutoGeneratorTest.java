package top.year21.onlineedu;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/18 15:46
 */
public class FastAutoGeneratorTest {
    public static void main(String[] args) {
        // 设置我们需要创建在哪的路径
//        String path = "/Users/luxiaogen/Documents/RoadTo2w/Java/尚硅谷/MyBatisPlus-2022/demo";
        // 这里我是mysql8 5版本可以换成 jdbc:mysql://localhost:3306/mybatis_plus?characterEncoding=utf-8&useSSL=false
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/online_edu?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false", "root", "root")
                .globalConfig(builder -> {
                    builder.author("year21") // 设置作者
                             .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C://Users//hcxs1986//IdeaProjects//online_edu_parent//service//service_acl//src//main//java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("top.year21.onlineedu") // 设置父包名
                            .moduleName("serviceacl") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C://Users//hcxs1986//IdeaProjects//online_edu_parent//service//service_acl//src//main//resources//mybatis//mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("acl_permission","acl_role","acl_role_permission","acl_user","acl_user_role") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_","edu_","statistics_","acl_"); // 设置过滤表前缀
                }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker 引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
