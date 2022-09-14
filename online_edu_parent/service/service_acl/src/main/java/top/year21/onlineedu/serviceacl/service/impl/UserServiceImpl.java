package top.year21.onlineedu.serviceacl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import top.year21.onlineedu.serviceacl.entity.User;
import top.year21.onlineedu.serviceacl.mapper.UserMapper;
import top.year21.onlineedu.serviceacl.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-09-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }
}
