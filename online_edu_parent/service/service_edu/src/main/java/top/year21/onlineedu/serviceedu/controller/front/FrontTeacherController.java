package top.year21.onlineedu.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.serviceedu.entity.EduCourse;
import top.year21.onlineedu.serviceedu.entity.EduTeacher;
import top.year21.onlineedu.serviceedu.service.IEduCourseService;
import top.year21.onlineedu.serviceedu.service.IEduTeacherService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/30 14:44
 */
@RestController
@RequestMapping("/serviceedu/frontTeacher")
public class FrontTeacherController {

    @Autowired
    private IEduTeacherService eduTeacherService;
    @Autowired
    private IEduCourseService eduCourseService;

    //分页查询讲师
    @GetMapping("/query/{pageNum}/{pageSize}")
    public JsonResult<Map<String,Object>> queryTeacherOnIndex(@PathVariable("pageNum")Integer pageNum,
                                                              @PathVariable("pageSize")Integer pageSize){
        HashMap<String,Object> teacherList = eduTeacherService.getFrontTeacherList(pageNum,pageSize);
        return new JsonResult<>(true,"查询成功",teacherList);
    }

    @GetMapping("/query/{teacherId}")
    public JsonResult<HashMap<String,Object>> queryTeacherById(@PathVariable("teacherId") String teacherId){
        //先查询讲师信息
        EduTeacher teacher = eduTeacherService.getById(teacherId);

        //再查询相关讲师的课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacher.getId());
        List<EduCourse> courses = eduCourseService.list(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacher",teacher);
        map.put("courses",courses);
        return new JsonResult<>(true,"查询成功",map);
    }
}
