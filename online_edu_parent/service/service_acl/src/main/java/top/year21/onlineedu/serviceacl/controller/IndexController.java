package top.year21.onlineedu.serviceacl.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.serviceacl.helper.IpUtils;
import top.year21.onlineedu.serviceacl.service.IndexService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/4 1:07
 */
@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {
    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("/info")
    public JsonResult<Map<String, Object>> info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return new JsonResult<>(true,"获取成功",userInfo);
    }

    /**
     * 获取菜单
     */
    @GetMapping("/menu")
    public JsonResult<List<JSONObject>> getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return new JsonResult<>(true,"获取菜单成功",permissionList);
    }

    //退出登录
    @PostMapping("/logout")
    public JsonResult<Void> logout(){
        return new JsonResult<>(true);
    }

    //根据ip获取登录信息
    @GetMapping("/getIpInfo")
    public JsonResult<JSONObject>  getIpInfo(HttpServletRequest request){
        JSONObject ipInfo = IpUtils.getIpInfo();
        return new JsonResult<>(true,"查询成功",ipInfo);
    }
}
