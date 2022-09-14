package top.year21.onlineedu.serviceedu.service;

import top.year21.onlineedu.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author year21
 * @since 2022-08-22
 */
public interface IEduVideoService extends IService<EduVideo> {

    void addNewBar(EduVideo eduVideo);

    void delBarById(String id);

    void updateBarById(EduVideo eduVideo);

    EduVideo queryBarById(String id);
}
