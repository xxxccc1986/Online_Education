package top.year21.onlineedu.serviceedu.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.serviceedu.entity.EduCourse;
import top.year21.onlineedu.serviceedu.entity.EduTeacher;
import top.year21.onlineedu.serviceedu.service.IEduCourseService;
import top.year21.onlineedu.serviceedu.service.IEduTeacherService;

import java.util.List;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 这个控制层本应该写在service_cms模块中，
 * 但由于使用到了teacher和course类，为了方便就写这这个service_edu中方便调用
 * @date 2022/8/27 21:59
 */
@RestController
@RequestMapping("/serviceedu/front")
public class FrontIndexController {

    @Autowired
    private IEduCourseService eduCourseService;
    @Autowired
    private IEduTeacherService eduTeacherService;

    //查询创建时间最新的四名老师
    @GetMapping("/queryteaher")
    public JsonResult<List<EduTeacher>> queryTeacherByCondition(){
        List<EduTeacher> list = eduTeacherService.getSortTeacher();
        return new JsonResult<>(true,"查询成功",list);
    }

    //查询前8条热门课程
    @GetMapping("/querycourse")
    public JsonResult<List<EduCourse>> queryCourseByCondition(){
        List<EduCourse> list =  eduCourseService.getHotCourse();
        return new JsonResult<>(true,"查询成功",list);
    }

}
