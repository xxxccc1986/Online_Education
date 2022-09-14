package top.year21.onlineedu.serviceedu.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/31 1:58
 */
@Data
public class VOFrontCourseDetails {
    private String id;
    private String title;
    private String cover;
    private BigDecimal price;
    private String teacherId;
    private String buyCount;
    private String lessonNum;
    private String viewCount;
    private String name;
    private String career;
    private String avatar;
    private String description;

}
