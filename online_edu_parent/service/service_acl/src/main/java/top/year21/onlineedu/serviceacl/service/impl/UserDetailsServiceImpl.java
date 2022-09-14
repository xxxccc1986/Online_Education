package top.year21.onlineedu.serviceacl.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.year21.onlineedu.serurity.entity.SecurityUser;

import top.year21.onlineedu.serviceacl.entity.User;
import top.year21.onlineedu.serviceacl.service.IPermissionService;
import top.year21.onlineedu.serviceacl.service.IUserService;

import java.util.List;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/4 15:39
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;

    /**
     * Description : 在进行登录时根据账户获取用户信息
     * @date 2022/9/4
     * @param username 用户名
     * @return org.springframework.security.core.userdetails.UserDetails
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        User user = userService.selectByUsername(username);

        // 判断用户是否存在
        if (null == user){
            //throw new UsernameNotFoundException("用户名不存在！");
        }
        // 返回UserDetails实现类
        top.year21.onlineedu.serurity.entity.User curUser = new top.year21.onlineedu.serurity.entity.User();
        BeanUtils.copyProperties(user,curUser);

        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }
}
