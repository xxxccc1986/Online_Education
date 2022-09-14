package top.year21.onlineedu.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.commonvo.VOCourseDetails;
import top.year21.onlineedu.serviceedu.entity.EduSubject;
import top.year21.onlineedu.serviceedu.entity.course.CourseChapter;
import top.year21.onlineedu.serviceedu.service.IEduChapterService;
import top.year21.onlineedu.serviceedu.service.IEduCourseService;
import top.year21.onlineedu.serviceedu.service.IEduSubjectService;
import top.year21.onlineedu.serviceedu.vo.VOFrontCourse;
import top.year21.onlineedu.serviceedu.vo.VOFrontCourseDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/30 21:33
 */
@RestController
@RequestMapping("/serviceedu/frontCourse")
public class FrontCourseController {

    @Autowired
    private IEduCourseService courseService;
    @Autowired
    private IEduSubjectService subjectService;
    @Autowired
    private IEduChapterService chapterService;


    //条件查询课程
    @PostMapping("/courseList/{pageNum}/{pageSize}")
    public JsonResult<Map<String,Object>> showCourseListByCondition(@PathVariable("pageNum") Integer pageNum,
                                                                    @PathVariable("pageSize") Integer pageSize,
                                                                    @RequestBody(required = false) VOFrontCourse course){
        HashMap<String,Object> courseList = courseService.showCourseListByCondition(pageNum,pageSize,course);

        return new JsonResult<>(true,"查询成功",courseList);
    }

    //subjectId通过判断一二级分类
    @GetMapping("/querySubject")
    public JsonResult<List<EduSubject>> queryOneSubjectList(@RequestParam(required = false) String subjectParentId){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(subjectParentId)){ //不为空表示查询二级分类
            wrapper.eq("parent_id",subjectParentId);
        }else { //为空表示查询一级分类
            wrapper.eq("parent_id",0);
        }
        List<EduSubject> subjectList = subjectService.list(wrapper);
        return new JsonResult<>(true,"查询成功",subjectList);
    }

    //根据课程id查询课程详情信息
    @GetMapping("/details/{courseId}")
    public JsonResult<Map<String,Object>> queryCourseDetailsById(@PathVariable("courseId") String courseId){
        //1.先根据课程id查询课程信息
        VOFrontCourseDetails courseDetails = courseService.queryCourseDetailsById(courseId);

        //2.根据课程id查询课程的所有章节和小节
        List<CourseChapter> courseChapterList = chapterService.getChapterListByCid(courseId);

        //将数据封装进map中返回
        HashMap<String, Object> details = new HashMap<>();
        details.put("courseDetails",courseDetails);
        details.put("courseChapterList",courseChapterList);

        return new JsonResult<>(true,"查询成功",details);
    }

    //根据课程id查询课程详情信息
    @GetMapping("/orderDetails/{courseId}")
    public VOCourseDetails orderCourseDetailsById(@PathVariable("courseId") String courseId) {
        //根据课程id查询课程信息
        VOFrontCourseDetails queryDetails = courseService.queryCourseDetailsById(courseId);

        //将查询得出的信息封装到订单所需的课程vo类中并返回
        VOCourseDetails courseDetails = new VOCourseDetails();
        BeanUtils.copyProperties(queryDetails,courseDetails);

        return courseDetails;
    }
}
