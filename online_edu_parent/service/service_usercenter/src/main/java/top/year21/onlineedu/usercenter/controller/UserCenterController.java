package top.year21.onlineedu.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.JwtUtils;
import top.year21.onlineedu.PasswordEncryptedUtils;
import top.year21.onlineedu.commonvo.VOUserCenter;
import top.year21.onlineedu.exception.CommonException;
import top.year21.onlineedu.usercenter.entity.UserCenter;
import top.year21.onlineedu.usercenter.entity.VORegister;
import top.year21.onlineedu.usercenter.service.impl.UserCenterServiceImpl;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-08-28
 */
@RestController
@RequestMapping("/serviceusercenter")
public class UserCenterController {

    @Autowired
    private UserCenterServiceImpl userCenterService;

    //登录
    @PostMapping("/login")
    public JsonResult<String> userLogin(@RequestBody UserCenter user){
        //调用service方法实现登录并在登录成功后返回根据指定规则生成的token
        String userToken = userCenterService.userLogin(user);
        return new JsonResult<>(true,"登录成功",userToken);
    }

    //注册
    @PostMapping("/register")
    public JsonResult<Void> userRegister(@RequestBody VORegister voRegister){
        userCenterService.userRegister(voRegister);
        return new JsonResult<>(true);
    }

    //根据token查询用户信息
    @GetMapping("/queryInfo")
    public JsonResult<UserCenter> queryInfoByToken(HttpServletRequest request){
        //通过request对象获取用户id
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        //根据用户id查询数据并返回
        UserCenter loginUser = userCenterService.getById(userId);
        return new JsonResult<>(true,"查询成功",loginUser);
    }

    //通过userId查询用户信息
    @GetMapping("/userInfo/{userId}")
    public VOUserCenter getUserInfoById(@PathVariable("userId") String userId){
        UserCenter queryUser = userCenterService.getById(userId);
        VOUserCenter voUserCenter = new VOUserCenter();
        BeanUtils.copyProperties(queryUser,voUserCenter);
        return voUserCenter;
    }

    //通过userId修改用户信息
    @PostMapping("/update")
    public JsonResult<Void> updateUserInfoById(@RequestBody UserCenter user){
        //根据id查询用户信息
        UpdateWrapper<UserCenter> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",user.getId());
        if (!userCenterService.update(user,wrapper)){
            throw new CommonException(30001,"更新用户信息失败!");
        }
        return new JsonResult<>(true);
    }

    //根据userId修改用户头像
    @PostMapping("/updatePhoto/{userId}/{path}")
    public JsonResult<Void> updatePhotoById(@PathVariable("userId") String userId,
                                            @PathVariable("path") String path){
        String dbPath = decode(path);
        userCenterService.updatePhotoById(userId,dbPath);
        return new JsonResult<>(true);
    }

    @PostMapping("/updatepwd/{userId}/{oldPwd}/{newPwd}")
    public JsonResult<Void> updatePwd(@PathVariable("userId") String userId,
                                      @PathVariable("oldPwd") String oldPwd,
                                      @PathVariable("newPwd") String newPwd){
        //先根据userId查找用户信息
        UserCenter user = userCenterService.getById(userId);
        //取出密码和盐值
        String salt = user.getSalt();
        String dbPassword = user.getPassword();

        //先判断密码是否正确
        String md5Password = PasswordEncryptedUtils.getPasswordByMD5(oldPwd, salt);
        if (!md5Password.equals(dbPassword)){
            throw new CommonException(30001,"原密码错误！");
        }

        //加密新密码
        String newPassword = PasswordEncryptedUtils.getPasswordByMD5(newPwd, salt);

        //更新密码
        user.setPassword(newPassword);
        boolean result = userCenterService.updateById(user);

        if (!result){
            throw new CommonException(30001,"密码更新失败！");
        }

        return new JsonResult<>(true);
    }


    //查询某一天的注册人数
    @GetMapping("/count/{day}")
    public Integer countRegisterNumInOneDay(@PathVariable("day")String day){
        return  userCenterService.countRegisterNumInOneDay(day);
    }



    /** 对base64码进行解码
     * @param code 需要解码的base64字符串
     * @return 解码之后的字符串
     * */
    public static String decode(String code){
        String str="";
        try {
            str=new String(Base64.decodeBase64(code),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

}
