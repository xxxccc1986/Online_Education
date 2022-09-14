package top.year21.onlineedu.serviceedu.service;

import top.year21.onlineedu.serviceedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import top.year21.onlineedu.serviceedu.entity.course.CourseChapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author year21
 * @since 2022-08-22
 */
public interface IEduChapterService extends IService<EduChapter> {

    //查询指定id的课程章节
    List<CourseChapter> getChapterListByCid(String courseId);

    void addNewChapterById(EduChapter eduChapter);

    void updateChapterById(EduChapter eduChapter);

    void delChapterById(String id);

    EduChapter queryChapterById(String id);


}
