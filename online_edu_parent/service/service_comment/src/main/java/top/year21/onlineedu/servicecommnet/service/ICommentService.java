package top.year21.onlineedu.servicecommnet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.year21.onlineedu.commonvo.VOCourseDetails;
import top.year21.onlineedu.servicecommnet.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import top.year21.onlineedu.servicecommnet.vo.VOComment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author year21
 * @since 2022-09-03
 */
public interface ICommentService extends IService<Comment> {

    void createCommnet(String content, String courseId,Integer rate,HttpServletRequest request);

    Map<String, Object> showComment(Integer pageNum, Integer pageSize, String courseId, String teacherId);

    void reportComment(String commentId,Integer status);

    Map<String,Object> getAllComment(Integer pageNum, Integer pageSize, VOComment voComment);

    List<VOCourseDetails> showTeacher();

    List<VOCourseDetails> showCourse();

}
