package top.year21.onlineedu.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.commonvo.VOCourseDetails;
import top.year21.onlineedu.serviceedu.entity.EduCourse;
import top.year21.onlineedu.serviceedu.entity.EduTeacher;
import top.year21.onlineedu.serviceedu.service.IEduCourseService;
import top.year21.onlineedu.serviceedu.vo.VOCourse;
import top.year21.onlineedu.serviceedu.vo.VOPublish;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-08-22
 */
@RestController
@RequestMapping("/serviceedu/course")
public class EduCourseController {

    @Autowired
    private IEduCourseService eduCourseService;

    //添加课程
    @PostMapping("/add")
    public JsonResult<String> addCourse(@RequestBody VOCourse voCourse){
        String courseId = eduCourseService.addCourse(voCourse);
        return new JsonResult<>(true,"添加成功",courseId);
    }

    //根据id查询指定课程信息
    @GetMapping("/query/{id}")
    public JsonResult<VOCourse> queryInfoById(@PathVariable("id") String id){
        VOCourse voCourse = eduCourseService.queryCourseInfoById(id);
        return new JsonResult<>(true,"查询成功",voCourse);
    }

    //根据id修改指定课程信息
    @PostMapping("/update")
    public JsonResult<Void> updateInfoById(@RequestBody VOCourse voCourse){
        eduCourseService.updateCourseInfoById(voCourse);
        return new JsonResult<>(true);
    }

    //根据指定id查询复合封装的课程信息对象
    @GetMapping("/{id}")
    public JsonResult<VOPublish> queryVOPublishById(@PathVariable("id") String id){
        VOPublish voPublish = eduCourseService.queryVOPublishById(id);
        return new JsonResult<>(true,"查询成功",voPublish);
    }

    //根据指定id修改课程状态为已发布
    @PostMapping("/status/{courseId}")
    public JsonResult<Void> updateCourseStatusById(@PathVariable("courseId") String courseId){
        eduCourseService.updateStatus(courseId);
        return new JsonResult<>(true);
    }


    //查询所有的课程
    @GetMapping("/conditionQuery/{pageNum}/{pageSize}")
    public JsonResult<Page<EduCourse>> queryCondition(@PathVariable("pageNum") Integer pageNum,
                                                      @PathVariable("pageSize") Integer pageSize){
        Page<EduCourse> page = eduCourseService.queryCondition(pageNum,pageSize);
        return new JsonResult<>(true,"查询成功",page);
    }

    //多条件组合分页查询课程信息
    @PostMapping("/condition/{pageNum}/{pageSize}")
    public JsonResult<Page<EduCourse>> queryByPagesWithCondition(@PathVariable("pageNum") Integer pageNum,
                                                                 @PathVariable("pageSize") Integer pageSize,
                                                                 @RequestBody(required = false) VOCourse VOCourse){
        //创建page对象，查询的数据都封装在这个对象中
        Page<EduCourse> page = new Page<>(pageNum, pageSize);
        //创建条件构造器对象
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        //获取传入的参数
        String title = VOCourse.getTitle();
        String status = VOCourse.getStatus();

        //判断属性值是否为空，以决定是否添加该查询条件
        if (!StringUtils.isEmpty(title)){
            queryWrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)){
            queryWrapper.eq("status",status);
        }

        //进行排序
        queryWrapper.orderByDesc("gmt_create");

        eduCourseService.page(page,queryWrapper);
        return new JsonResult<>(true,"查询成功",page);
    }

    //删除指定课程及其下属的所有章节和小节
    @PostMapping("/del/{courseId}")
    public JsonResult<Void> delCourseById(@PathVariable("courseId") String courseId){
        eduCourseService.delCourseById(courseId);
        return new JsonResult<>(true);
    }

    //查询指定字段信息
    @GetMapping("/column")
    public List<VOCourseDetails> selectCourseColumn(){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.select("id","title");
        List<EduCourse> courses = eduCourseService.list(wrapper);
        List<VOCourseDetails> courseList = new ArrayList<>();
        for (EduCourse c : courses){
            VOCourseDetails details = new VOCourseDetails();
            details.setId(c.getId());
            details.setTitle(c.getTitle());
            courseList.add(details);
        }
        return courseList;
    }

}
