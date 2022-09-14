package top.year21.onlineedu.usercenter.mapper;

import org.apache.ibatis.annotations.Param;
import top.year21.onlineedu.usercenter.entity.UserCenter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author year21
 * @since 2022-08-28
 */
public interface UserCenterMapper extends BaseMapper<UserCenter> {

    Integer countRegisterNumInOneDay(@Param("day") String day);

    int updatePhotoById(@Param("userId") String userId, @Param("path") String path);
}
