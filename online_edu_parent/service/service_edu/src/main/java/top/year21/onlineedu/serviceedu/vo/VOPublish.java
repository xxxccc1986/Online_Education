package top.year21.onlineedu.serviceedu.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/25 15:03
 */
@Data
public class VOPublish {
    private String id;
    private String title;
    private String lessonNum;
    private String oneSubject;
    private String twoSubject;
    private String teacherName;
    private String cover;
    private BigDecimal price;
}
