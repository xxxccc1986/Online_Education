package top.year21.onlineedu.serviceedu.controller;

import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;

import java.util.HashMap;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/20 16:10
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {

    @PostMapping("/login")
    public JsonResult<String> userLogin(){
        return new JsonResult<>(true,"登录成功","admin");
    }

    @GetMapping("/info")
    public JsonResult<HashMap<String, Object>> userLoginInfo(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","http://m.imeitou.com/uploads/allimg/2016060815/g1qemno1c4t.jpg");
        return new JsonResult<>(true,"登录成功",map);
    }
}
