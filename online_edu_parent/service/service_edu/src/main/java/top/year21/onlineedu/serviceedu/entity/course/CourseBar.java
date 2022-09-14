package top.year21.onlineedu.serviceedu.entity.course;

import lombok.Data;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 课程小节对象
 * @date 2022/8/23 18:01
 */
@Data
public class CourseBar {
    private String id;
    private String title;
    private String videoSourceId; //视频id
}
