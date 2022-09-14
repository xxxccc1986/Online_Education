package top.year21.onlineedu.serviceedu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.serviceedu.entity.EduChapter;
import top.year21.onlineedu.serviceedu.entity.course.CourseChapter;
import top.year21.onlineedu.serviceedu.service.IEduChapterService;

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
@RequestMapping("/serviceedu/chapter")
public class EduChapterController {

    @Autowired
    private IEduChapterService eduChapterService;

    //查询指定课程所有章节
    @GetMapping("/list/{courseId}")
    public JsonResult<List<CourseChapter>> getCourseChaptersByCid(@PathVariable("courseId") String courseId){
        List<CourseChapter> chapterList = eduChapterService.getChapterListByCid(courseId);
        return new JsonResult<>(true,"查询成功",chapterList);
    }

    //添加指定课程章节
    @PostMapping("/add")
    public JsonResult<Void> addChapterById(@RequestBody EduChapter eduChapter){
        eduChapterService.addNewChapterById(eduChapter);
        return new JsonResult<>(true);
    }

    //修改指定课程章节
    @PostMapping("/update")
    public JsonResult<Void> updateChapterById(@RequestBody EduChapter eduChapter){
        eduChapterService.updateChapterById(eduChapter);
        return new JsonResult<>(true);
    }

    //删除指定课程章节
    @PostMapping("/del/{id}")
    public JsonResult<Void> delChapterById(@PathVariable("id") String id){
        eduChapterService.delChapterById(id);
        return new JsonResult<>(true);
    }

    //查询指定课程章节
    @GetMapping("/query/{id}")
    public JsonResult<EduChapter> queryChapterById(@PathVariable("id") String id){
        EduChapter chapter = eduChapterService.queryChapterById(id);
        return new JsonResult<>(true,"查询成功",chapter);
    }



}
