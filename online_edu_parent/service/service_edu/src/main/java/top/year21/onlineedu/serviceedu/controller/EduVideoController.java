package top.year21.onlineedu.serviceedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.serviceedu.entity.EduVideo;
import top.year21.onlineedu.serviceedu.service.IEduVideoService;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-08-22
 */
@RestController
@RequestMapping("/serviceedu/video")

public class EduVideoController {

    @Autowired
    private IEduVideoService eduVideoService;

    //添加小节
    @PostMapping("/add")
    public JsonResult<Void> addNewBar(@RequestBody EduVideo eduVideo){
        eduVideoService.addNewBar(eduVideo);
        return new JsonResult<>(true);
    }

    //根据id删除小节
    @PostMapping("/del/{id}")
    public JsonResult<Void> delBarById(@PathVariable("id") String id){
        eduVideoService.delBarById(id);
        return new JsonResult<>(true);
    }

    //根据id修改小节
    @PostMapping("/update")
    public JsonResult<Void> updateById(@RequestBody EduVideo eduVideo){
        eduVideoService.updateBarById(eduVideo);
        return new JsonResult<>(true);
    }

    //根据id查询小节
    @GetMapping("/query/{id}")
    public JsonResult<EduVideo> queryById(@PathVariable("id")String id){
        EduVideo eduVideo = eduVideoService.queryBarById(id);
        return new JsonResult<>(true,"查询成功",eduVideo);
    }


}
