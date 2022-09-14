package top.year21.onlineedu.servicecommnet.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.commonvo.VOCourseDetails;

import java.util.List;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/31 23:29
 */
@Component
@FeignClient("service-edu")
public interface OpenFeignTransferEdu {

    @GetMapping("/serviceedu/frontCourse/orderDetails/{courseId}")
    public VOCourseDetails orderCourseDetailsById(@PathVariable("courseId") String courseId);

    //查询讲师指定字段信息
    @GetMapping("/serviceedu/column")
    public List<VOCourseDetails> selectTeacherColumn();

    //查询课程指定字段信息
    @GetMapping("/serviceedu/course/column")
    public List<VOCourseDetails> selectCourseColumn();
}
