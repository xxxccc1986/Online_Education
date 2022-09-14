package top.year21.onlineedu.servicesms.service.impl;

import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;
import top.year21.onlineedu.servicesms.service.MsgService;
import top.year21.onlineedu.servicesms.utils.HttpUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/28 16:24
 */
@Service
public class MsgServiceImpl implements MsgService {
    @Override
    public Boolean sendMessage(String phone, String code) {
        //判断验证码是否为空
        if ("".equals(phone)){
            return false;
        }

        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "e9323c6105394949a1c1a7dbb05253f0";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile",phone);
        querys.put("param", "**code**:" + code);
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "63698e3463bd490dbc3edc46a20c55f5");
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取请求头中的响应状态码判断短信发送情况
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200){
                return true;
            }

            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
