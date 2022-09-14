package top.year21.onlineedu.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.year21.onlineedu.JwtUtils;
import top.year21.onlineedu.usercenter.entity.UserCenter;
import top.year21.onlineedu.usercenter.service.IUserCenterService;
import top.year21.onlineedu.usercenter.utils.HttpClientUtils;
import top.year21.onlineedu.usercenter.utils.WXUtils;
import java.net.URLEncoder;
import java.util.HashMap;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/29 18:48
 */
@Controller
@RequestMapping("/api/ucenter/wx")
@Slf4j
public class WxController {

    @Autowired
    private IUserCenterService userCenterService;

    //1.生成微信扫描二维码
    @GetMapping("/login")
    public String getWxCode(){

        // 微信开放平台授权baseUrl %s相当于一个占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_url进行URLEncoder编码
        String redirectUrl = WXUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //填充占位符
        String codeUrl = String.format(
                baseUrl,
                WXUtils.WX_OPEN_APP_ID,
                WXUtils.WX_OPEN_REDIRECT_URL,
                "success");

        //返回微信请求地址
        return "redirect:" + codeUrl;
    }


    @GetMapping("/callback")
    public String callback(String code,String state){
        try {
            //获取code值，这个code相当于一个临时票据
            //拼接请求地址
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //填充请求地址中的三个占位符，id和密匙和code值
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    WXUtils.WX_OPEN_APP_ID,
                    WXUtils.WX_OPEN_APP_SECRET,
                    code);

            //使用HttpClient 发送请求，通过code值向认证服务器获取access_token和openId
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            log.info("accessTokenInfo:" + accessTokenInfo);

            //将access_token和openId从返回的字符串中取出
            //先将此字符串转换为map对象
            Gson gson = new Gson();
            HashMap<String,String> hashMap = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = hashMap.get("access_token");
            String openid = hashMap.get("openid");

            //将这些信息插入数据库，但需先判断此账户的openId是否已经存在
            QueryWrapper<UserCenter> wrapper = new QueryWrapper<>();
            wrapper.eq("openid",openid);
            UserCenter queryUser = userCenterService.getOne(wrapper);
            //queryUser == null表示新注册用户
            if (queryUser == null){
                //通过access_token和openId从微信提供的接口中获取扫码人的信息
                //拼接即将要请求的地址访问微信的即可，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //填充参数
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                //通过HttpClientUtils模拟浏览器发送请求获取扫描人信息
                String userInfo = HttpClientUtils.get(userInfoUrl);
                log.info("userInfo:" + userInfo);

                //将扫码人信息字符串转为map对象，便于取数据
                Gson userGson = new Gson();
                HashMap<String,String> userMap = userGson.fromJson(userInfo, HashMap.class);
                String nickname = userMap.get("nickname");
                String headimgurl = userMap.get("headimgurl");
                //填充数据并插入数据库
                queryUser = new UserCenter();
                queryUser.setOpenid(openid);
                queryUser.setNickname(nickname);
                queryUser.setAvatar(headimgurl);
                userCenterService.save(queryUser);
            }

            //使用jwt框架生成token
            String token = JwtUtils.getJwtToken(queryUser.getId(), queryUser.getNickname());

            //注册成功后，跳转至首页显示信息
            return "redirect:http://localhost:3000?token=" + token;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
