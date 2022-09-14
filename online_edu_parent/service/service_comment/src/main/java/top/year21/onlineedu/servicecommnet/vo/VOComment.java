package top.year21.onlineedu.servicecommnet.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/9 0:19
 */
@Data
public class VOComment {
    private String id;
    private String username;
    private String courseId;
    private String courseName;
    private String teacherId;
    private String teacherName;
    private String content;
    private Integer rate;
    private Date gmtCreate;
    private Date startTime;
    private Date endTime;
    private Integer isReported;
}
