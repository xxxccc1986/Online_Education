package top.year21.onlineedu.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.year21.onlineedu.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import top.year21.onlineedu.serviceedu.vo.VOCourse;
import top.year21.onlineedu.serviceedu.vo.VOFrontCourse;
import top.year21.onlineedu.serviceedu.vo.VOFrontCourseDetails;
import top.year21.onlineedu.serviceedu.vo.VOPublish;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author year21
 * @since 2022-08-22
 */
public interface IEduCourseService extends IService<EduCourse> {

    String addCourse(VOCourse voCourse);

    VOCourse queryCourseInfoById(String id);

    void updateCourseInfoById(VOCourse voCourse);

    VOPublish queryVOPublishById(String id);

    void updateStatus(String courseId);

    Page<EduCourse> queryCondition(Integer pageNum, Integer pageSize);

    void delCourseById(String courseId);

    //查询排序后的课程信息，用于首页展示
    List<EduCourse> getHotCourse();

    //条件查询显示课程信息
    HashMap<String, Object> showCourseListByCondition(Integer pageNum, Integer pageSize, VOFrontCourse course);

    VOFrontCourseDetails queryCourseDetailsById(String courseId);
}
