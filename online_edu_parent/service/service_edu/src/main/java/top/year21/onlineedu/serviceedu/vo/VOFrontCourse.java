package top.year21.onlineedu.serviceedu.vo;


import lombok.Data;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 前台页面封装搜索信息的vo对象
 * @date 2022/8/30 22:10
 */
@Data
public class VOFrontCourse {

    private String title;

//    private String teacherId;
    private String courseId;

    //一级类别id
    private String subjectParentId;

    //二级类别id
    private String subjectId;

    private String viewCountSort;

    private String gmtCreateSort;

    private String priceSort;
}
