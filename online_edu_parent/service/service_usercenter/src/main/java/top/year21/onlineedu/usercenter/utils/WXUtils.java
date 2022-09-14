package top.year21.onlineedu.usercenter.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/29 18:40
 */
@Component
public class WXUtils {

    @Value("${wx.open.appid}")
    private String appId;

    @Value("${wx.open.appsecret}")
    private String appSecret;

    @Value("${wx.open.redirecturl}")
    private String redirectUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;


    @PostConstruct
    public void init(){
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}
