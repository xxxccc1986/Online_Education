package top.year21.onlineedu.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import top.year21.onlineedu.JwtUtils;
import top.year21.onlineedu.PasswordEncryptedUtils;
import top.year21.onlineedu.exception.CommonException;
import top.year21.onlineedu.usercenter.entity.UserCenter;
import top.year21.onlineedu.usercenter.entity.VORegister;
import top.year21.onlineedu.usercenter.mapper.UserCenterMapper;
import top.year21.onlineedu.usercenter.service.IUserCenterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-08-28
 */
@Service
public class UserCenterServiceImpl extends ServiceImpl<UserCenterMapper, UserCenter> implements IUserCenterService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //用于登录
    @Override
    public String userLogin(UserCenter user) {

        //获取传入账户和密码
        String phone = user.getMobile();
        String inputPassword = user.getPassword();

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(inputPassword)){
            throw new CommonException(30001,"账户名或密码为空，登录失败");
        }

        //根据账户名查询信息
        QueryWrapper<UserCenter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",phone);
        UserCenter queryUser = this.baseMapper.selectOne(queryWrapper);

        //判断账户是否存在
        if (queryUser == null || queryUser.getIsDisabled()){
            throw new CommonException(30001,"账户名不存在，登录失败");
        }

        //根据查询的账户取得盐值和密码
        String salt = queryUser.getSalt();
        String dbPassword = queryUser.getPassword();
        //将输入的密码加密后与数据库密码对比
        String md5Pwd = PasswordEncryptedUtils.getPasswordByMD5(inputPassword, salt);
        if (!dbPassword.equals(md5Pwd)){
            throw new CommonException(30001,"密码错误，登录失败");
        }

        //如果不为空则代表登录成功
        String id = queryUser.getId();
        String nickname = queryUser.getNickname();
        //生成token并返回
        return JwtUtils.getJwtToken(id, nickname);
    }

    //用户注册
    @Override
    public void userRegister(VORegister voRegister) {
        //获取输入的手机号
        String phone = voRegister.getMobile();

        //判断手机号是否已被注册
        QueryWrapper<UserCenter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",phone);
        Long count = this.baseMapper.selectCount(queryWrapper);
        if (count != 0){
            throw new CommonException(30001,"手机号已注册！");
        }

        //根据手机号获取redis中的验证码是否可用
        String redisCode = redisTemplate.opsForValue().get(phone);
        if (StringUtils.isEmpty(redisCode)){
            throw new CommonException(30001,"验证码已过期,注册失败，请重新获取验证码");
        }

        //获取输入的验证码
        String inputCode = voRegister.getCode();
        //判断验证码是否正确
        if (!inputCode.equals(redisCode)){
            throw new CommonException(30001,"验证码错误,注册失败");
        }

        //获取输入的密码进行加密操作
        String inputPwd = voRegister.getPassword();
        //将此时的时间戳作为盐值
        String salt = UUID.randomUUID().toString().toUpperCase();

        //进行加密
        String pwdByMd5 = PasswordEncryptedUtils.getPasswordByMD5(inputPwd, salt);

        //创建一个User对象封装这些数据
        UserCenter userCenter = new UserCenter();
        //填充属性
        userCenter.setMobile(phone);
        userCenter.setPassword(pwdByMd5);
        userCenter.setNickname(voRegister.getNickName());
        userCenter.setAvatar("https://onlineedufile.oss-cn-guangzhou.aliyuncs.com/2022-08-23/ee578e07-753c-4ddc-8ff3-73ff79f749d8.jpg");
        //保存盐值用于登录验证
        userCenter.setSalt(salt);

        //执行插入操作
        this.baseMapper.insert(userCenter);

    }

    //查询某一天的注册人数
    @Override
    public Integer countRegisterNumInOneDay(String day) {
        return this.baseMapper.countRegisterNumInOneDay(day);
    }

    //根据用户id修改头像
    @Override
    public void updatePhotoById(String userId, String path) {
       int result =  this.baseMapper.updatePhotoById(userId,path);
       if (result == 0){
           throw new CommonException(30001,"更新头像失败！");
       }
    }
}
