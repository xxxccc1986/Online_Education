package top.year21.onlineedu.serviceedu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 课程相关值对象
 * @date 2022/8/22 22:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VOCourse {
    private String id;
    private String teacherId;
    private String subjectId;
    private String subjectParentId;
    private String title;
    private String status;
    private BigDecimal price;
    private Integer lessonNum;
    private String cover;
    private String description;
}
