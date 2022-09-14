package top.year21.onlineedu.serviceacl.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/4 16:54
 */
public interface IndexService {
    /**
     * 根据用户名获取用户登录信息
     * @param username 用户名
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * 根据用户名获取动态菜单
     * @param username 用户名
     */
    List<JSONObject> getMenu(String username);
}
