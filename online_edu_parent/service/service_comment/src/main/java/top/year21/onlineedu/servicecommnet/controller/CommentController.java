package top.year21.onlineedu.servicecommnet.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.commonvo.VOCourseDetails;
import top.year21.onlineedu.servicecommnet.entity.Comment;
import top.year21.onlineedu.servicecommnet.service.ICommentService;
import top.year21.onlineedu.servicecommnet.vo.VOComment;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-09-03
 */
@RestController
@RequestMapping("/servicecomment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    //创建课程评论
    @GetMapping("/create/{courseId}/{content}/{rate}")
    public JsonResult<Void> createComment(@PathVariable("content") String content,
                                          @PathVariable("courseId") String courseId,
                                          @PathVariable("rate") Integer rate,
                                          HttpServletRequest request){
        commentService.createCommnet(content,courseId,rate,request);
        return new JsonResult<>(true);
    }

    //查看课程评论
    @GetMapping("/show/{pageNum}/{pageSize}/{courseId}/{teacherId}")
    public JsonResult<Map<String, Object>> showComment(@PathVariable("pageNum") Integer pageNum,
                                                       @PathVariable("pageSize") Integer pageSize,
                                                       @PathVariable("courseId") String courseId,
                                                       @PathVariable("teacherId") String teacherId){
        Map<String, Object> comments = commentService.showComment(pageNum,pageSize,courseId,teacherId);
        return new JsonResult<>(true,"查询成功",comments);
    }

    //评论举报处理
    @PostMapping("/report/{commentId}/{updateStatus}")
    public JsonResult<Void> reportComment(@PathVariable("commentId") String commentId,
                                          @PathVariable("updateStatus") Integer status){
        commentService.reportComment(commentId,status);
        return new JsonResult<>(true);
    }

    //查询所有评论
    @PostMapping("/getAllComment/{pageNum}/{pageSize}")
    public JsonResult<Map<String,Object>> getAllComment(@PathVariable("pageNum") Integer pageNum,
                                                        @PathVariable("pageSize") Integer pageSize,
                                                        @RequestBody(required = false) VOComment voComment){
        Map<String,Object> map = commentService.getAllComment(pageNum,pageSize,voComment);
        return new JsonResult<>(true,"查询成功",map);
    }

    //删除评论
    @PostMapping("/del/{commentIds}")
    public JsonResult<Void> delComment(@PathVariable("commentIds") String[] commentIds){
        List<String> ids = new ArrayList<>(Arrays.asList(commentIds));
        commentService.removeBatchByIds(ids);
        return new JsonResult<>(true);
    }

    //展示所有讲师信息
    @GetMapping("/showTeacher")
    public JsonResult<List<VOCourseDetails>> showTeacher(){
        List<VOCourseDetails> list = commentService.showTeacher();
        return new JsonResult<>(true,"查询成功",list);
    }

    //展示所有课程信息
    @GetMapping("/showCourse")
    public JsonResult<List<VOCourseDetails>> showCourse(){
        List<VOCourseDetails> list = commentService.showCourse();
        return new JsonResult<>(true,"查询成功",list);
    }

}
