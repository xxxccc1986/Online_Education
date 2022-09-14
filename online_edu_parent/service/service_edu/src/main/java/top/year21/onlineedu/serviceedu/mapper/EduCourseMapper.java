package top.year21.onlineedu.serviceedu.mapper;

import org.apache.ibatis.annotations.Param;
import top.year21.onlineedu.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.year21.onlineedu.serviceedu.vo.VOFrontCourseDetails;
import top.year21.onlineedu.serviceedu.vo.VOPublish;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author year21
 * @since 2022-08-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id查询相关信息
    VOPublish getVOPublishById(@Param("id")String id);

    //根据指定id修改课程状态为已发布
    int updateStatus(@Param("courseId") String courseId);

    //根据课程id查询课程详情信息
    VOFrontCourseDetails queryCourseDetails(@Param("courseId") String courseId);
}
