package top.year21.onlineedu.servicesms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.servicesms.service.MsgService;
import top.year21.onlineedu.servicesms.utils.RandomUtil;
import java.util.concurrent.TimeUnit;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/28 16:21
 */
@RestController
@RequestMapping("/servicemsm")
public class MsgController {

    @Autowired
    private MsgService msgService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/send/{phone}")
    public JsonResult<Void> sendMsg(@PathVariable("phone") String phone){
        //从redis中获取验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return new JsonResult<>(true);
        }
        //如果不能从redis中取到验证码则调用方法生成验证码
        code = RandomUtil.getFourBitRandom();
        //调用service发送短信的方法
        Boolean result = msgService.sendMessage(phone,code);
        if (result){
            /*发送成功则将验证码放入到redis中并设置过期时间
            set()中的参数说明，第一个是key，第二个是value，第三个是过期时间，第四个是时间单位
             */
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return new JsonResult<>(true,"短信发送成功",null);
        }else {
            return new JsonResult<>(false,"短信发送失败",null);
        }
    }

}
