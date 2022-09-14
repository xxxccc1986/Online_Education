package top.year21.onlineedu.serviceedu.entity.course;

import lombok.Data;

import java.util.List;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 课程章节对象
 * @date 2022/8/23 17:57
 */
@Data
public class CourseChapter {
    private String id;
    private String title;
    private List<CourseBar> courseBarList;
}
