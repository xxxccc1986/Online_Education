package top.year21.onlineedu.serviceacl.service;

import top.year21.onlineedu.serviceacl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author year21
 * @since 2022-09-04
 */
public interface IUserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);
}
