package top.year21.onlineedu.serviceedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.commonvo.VOCourseDetails;
import top.year21.onlineedu.serviceedu.entity.EduTeacher;
import top.year21.onlineedu.serviceedu.service.IEduTeacherService;
import top.year21.onlineedu.serviceedu.vo.VOTeacher;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-08-18
 */
@RestController
@RequestMapping("/serviceedu")
@Slf4j
public class EduTeacherController {
    @Autowired
    private IEduTeacherService teacherService;

    //查询所有讲师数据
    @GetMapping("/teachers")
    public JsonResult<List<EduTeacher>> queryAllTeachers(){
        List<EduTeacher> list = teacherService.list();
        return new JsonResult<>(true,"查询成功",list);
    }

    //删除讲师
    @PostMapping("/delTeacher/{id}")
    public JsonResult<Void> deleteTeacher(@PathVariable("id") String id){
        boolean result = teacherService.removeById(id);
        return new JsonResult<>(result);
    }

    //分页查询讲师数据
    @GetMapping("/queryByPages/{pageNum}/{pageSize}")
    public JsonResult<Page> queryByPages(@PathVariable("pageNum") Integer pageNum,
                                         @PathVariable("pageSize") Integer pageSize){
        Page<EduTeacher> page = new Page<>(pageNum, pageSize);
        teacherService.page(page,null);
        return new JsonResult<>(true,"查询成功",page);
    }

    //多条件组合分页查询讲师信息
    @PostMapping("/condition/{pageNum}/{pageSize}")
    public JsonResult<Page<EduTeacher>> queryByPagesWithCondition(@PathVariable("pageNum") Integer pageNum,
                                                      @PathVariable("pageSize") Integer pageSize,
                                                      @RequestBody(required = false) VOTeacher voTeacher){
        //创建page对象，查询的数据都封装在这个对象中
        Page<EduTeacher> page = new Page<>(pageNum, pageSize);
        //创建条件构造器对象
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        //获取传入的参数
        String name = voTeacher.getName();
        Integer level = voTeacher.getLevel();
        String begin = voTeacher.getBegin();
        String end = voTeacher.getEnd();

        //判断属性值是否为空，以决定是否添加该查询条件
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }

        //进行排序
        queryWrapper.orderByDesc("gmt_create");

        teacherService.page(page,queryWrapper);
        return new JsonResult<>(true,"查询成功",page);
    }

    //添加讲师
    @PostMapping("/add")
    public JsonResult<Void> addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean result = teacherService.save(eduTeacher);
        return new JsonResult<>(result);
    }

    //查询指定讲师信息
    @GetMapping("/query/{id}")
    public JsonResult<EduTeacher> queryById(@PathVariable("id") String id){
        EduTeacher teacher = teacherService.getById(id);
        return new JsonResult<>(true,"查询成功",teacher);
    }

    //更新指定讲师信息
    @PostMapping("/update/{id}")
    public JsonResult<Boolean> updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean result = teacherService.updateById(eduTeacher);
        return new JsonResult<>(result);
    }

    //查询指定字段信息
    @GetMapping("/column")
    public List<VOCourseDetails> selectTeacherColumn(){
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.select("id","name");
        List<EduTeacher> teachers = teacherService.list(wrapper);
        List<VOCourseDetails> teacherList = new ArrayList<>();
        for (EduTeacher t : teachers){
            VOCourseDetails details = new VOCourseDetails();
            details.setTeacherId(t.getId());
            details.setName(t.getName());
            teacherList.add(details);
        }
        return teacherList;
    }

}
