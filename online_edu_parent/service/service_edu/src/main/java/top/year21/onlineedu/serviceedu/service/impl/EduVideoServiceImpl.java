package top.year21.onlineedu.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.exception.InsertException;
import top.year21.onlineedu.serviceedu.entity.EduChapter;
import top.year21.onlineedu.serviceedu.entity.EduVideo;
import top.year21.onlineedu.serviceedu.mapper.EduVideoMapper;
import top.year21.onlineedu.serviceedu.service.IEduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.year21.onlineedu.serviceedu.service.OpenFeignDelVideo;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-08-22
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements IEduVideoService {

    @Autowired
    private OpenFeignDelVideo openFeignDelVideo;

    //添加小节
    @Override
    public void addNewBar(EduVideo eduVideo) {
        int result = this.baseMapper.insert(eduVideo);
        if (result ==0 ){
            throw new InsertException(30001,"添加小节失败！");
        }
    }

    //根据id删除小节
    @Override
    public void delBarById(String id){
        //根据id查询视频id
        EduVideo eduVideo = this.baseMapper.selectById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断是否视频id是否有值决定是否删除
        if (!"".equals(videoSourceId)){
            JsonResult<Void> result = openFeignDelVideo.delVideoInAliyun(videoSourceId);
            Boolean resultStatus = result.getResult();
            if (!resultStatus){
                throw new RuntimeException("删除失败，执行hystrix熔断器");
            }
        }
        //删除完视频再删除小节
        int result = this.baseMapper.deleteById(id);
        if (result == 0){
            throw new RuntimeException("删除指定小节失败");
        }
    }

    //根据id修改小节
    @Override
    public void updateBarById(EduVideo eduVideo) {
        UpdateWrapper<EduVideo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",eduVideo.getId());
        int update = this.baseMapper.update(eduVideo, updateWrapper);
        if (update ==0 ){
            throw new InsertException(30001,"更新小节失败！");
        }
    }

    //根据id查询小节
    @Override
    public EduVideo queryBarById(String id) {
        return this.baseMapper.selectById(id);
    }

}
