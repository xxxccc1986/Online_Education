package top.year21.onlineedu.serviceacl.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.year21.onlineedu.serviceacl.entity.Role;
import top.year21.onlineedu.serviceacl.entity.User;
import top.year21.onlineedu.serviceacl.service.IPermissionService;
import top.year21.onlineedu.serviceacl.service.IRoleService;
import top.year21.onlineedu.serviceacl.service.IUserService;
import top.year21.onlineedu.serviceacl.service.IndexService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/4 16:54
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return
     */
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        //在经过登录拦截器之后根据用户名获取用户信息
        User user = userService.selectByUsername(username);
        if (null == user) {
            //throw new GuliException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }

        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        result.put("name", user.getUsername());
        result.put("avatar", "http://1.14.176.219:8080/file/down/06afff28-a224-433b-9750-79634e4899c0.jpg");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);

        //根据用户id获取用户菜单权限
        List<JSONObject> permissionList = permissionService.selectPermissionByUserId(user.getId());
        return permissionList;
    }


}

