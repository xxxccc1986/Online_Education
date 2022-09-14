package top.year21.onlineedu.serviceoss.untils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/21 16:11
 */
@Component
public class OSSConfigurationClass{
    //从yml配置文件中读取下列配置内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyid;
    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;


    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    /*
    在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。
     */
    @PostConstruct
    public void init(){
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
        BUCKET_NAME = bucketname;
    }


}
