package top.year21.onlineedu.serviceacl.helper;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;



/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/9 16:38
 */
public class IpUtils {
    public static void main(String[] args) {
        getIpInfo();
    }
    public static JSONObject getIpInfo() {

        try {
            String text = sendPost("http://pv.sohu.com/cityjson/");
            String info = text.substring(text.indexOf("=") + 1,text.lastIndexOf(";"));
            return JSONObject.parseObject(info);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendPost(String url) throws IOException {
        String result = "";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            BasicResponseHandler handler = new BasicResponseHandler();
            StringEntity entity = new StringEntity("utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            result = httpClient.execute(httpPost, handler);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
