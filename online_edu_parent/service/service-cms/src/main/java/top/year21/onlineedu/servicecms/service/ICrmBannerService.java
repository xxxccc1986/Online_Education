package top.year21.onlineedu.servicecms.service;


import top.year21.onlineedu.servicecms.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author year21
 * @since 2022-08-27
 */
public interface ICrmBannerService extends IService<Banner> {

    List<Banner> queryAllBanner();

}
