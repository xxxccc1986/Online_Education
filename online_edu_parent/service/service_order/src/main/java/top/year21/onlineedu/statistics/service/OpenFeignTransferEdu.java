package top.year21.onlineedu.statistics.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.year21.onlineedu.commonvo.VOCourseDetails;


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
}
