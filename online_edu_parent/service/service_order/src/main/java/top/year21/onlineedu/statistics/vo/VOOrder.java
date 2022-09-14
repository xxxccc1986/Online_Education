package top.year21.onlineedu.statistics.vo;

import lombok.Data;
import java.util.Date;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/9 14:35
 */
@Data
public class VOOrder {
    private String nickname;
    private String teacherName;
    private String courseId;
    private Date startTime;
    private Date endTime;
    private Integer status;
}
