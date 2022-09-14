package top.year21.onlineedu.servicevod.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/26 1:05
 */
@Component
public class UploadConfigClass {
    @Value("${aliyun.vod.file.keyid}")
    private String keyid;
    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;


    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @PostConstruct
    public void init(){
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
    }
}
