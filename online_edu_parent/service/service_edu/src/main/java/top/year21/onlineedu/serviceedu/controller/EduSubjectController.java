package top.year21.onlineedu.serviceedu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.serviceedu.entity.EduSubject;
import top.year21.onlineedu.serviceedu.entity.subject.OneSubject;
import top.year21.onlineedu.serviceedu.service.IEduSubjectService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-08-21
 */
@RestController
@RequestMapping("/serviceedu/subject")
public class EduSubjectController {

    @Autowired
    private IEduSubjectService eduSubjectService;

    //添加课程分类
    @PostMapping("/add")
    public JsonResult<Void> addSubject(MultipartFile file) throws IOException {
        //获取上传文件
        eduSubjectService.addFile(file,eduSubjectService);
        return new JsonResult<>(true);
    }

    //课程查询
    @GetMapping("/query/{id}")
    public JsonResult<EduSubject> getbyId(@PathVariable("id") String id){
        EduSubject subject = eduSubjectService.getById(id);
        return new JsonResult<>(true,"查询成功",subject);
    }

    //课程分类显示
    @GetMapping("/list")
    public JsonResult<List<OneSubject>> getSubjectList(){
        List<OneSubject> subjects = eduSubjectService.selectAllSubject();
        return new JsonResult<>(true,"查询成功",subjects);
    }

}
