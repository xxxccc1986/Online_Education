package top.year21.onlineedu.usercenter.service;

import top.year21.onlineedu.usercenter.entity.UserCenter;
import com.baomidou.mybatisplus.extension.service.IService;
import top.year21.onlineedu.usercenter.entity.VORegister;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author year21
 * @since 2022-08-28
 */
public interface IUserCenterService extends IService<UserCenter> {

    String userLogin(UserCenter user);

    void userRegister(VORegister voRegister);

    Integer countRegisterNumInOneDay(String day);

    void updatePhotoById(String userId, String path);
}
