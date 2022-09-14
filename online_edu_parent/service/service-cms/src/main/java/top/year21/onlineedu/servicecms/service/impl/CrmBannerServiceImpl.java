package top.year21.onlineedu.servicecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.cache.annotation.Cacheable;
import top.year21.onlineedu.servicecms.entity.Banner;
import top.year21.onlineedu.servicecms.mapper.CrmBannerMapper;
import top.year21.onlineedu.servicecms.service.ICrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-08-27
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, Banner> implements ICrmBannerService {

    //查询所有banner
    @Override
    /*@Cacheable注解表示将此查询方法的返回结果缓存在redis中
    value = "banner",key = "'selectIndexList'"两个属性构成了此缓存在redis中的名字
    且key的值还需要额外再添加一对''
     */
    @Cacheable(value = "banner",key = "'selectIndexList'")
    public List<Banner> queryAllBanner() {
        QueryWrapper<Banner> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
        //last方法，拼接sql语句
        queryWrapper.last("limit 2");
        return this.baseMapper.selectList(queryWrapper);
    }
}
