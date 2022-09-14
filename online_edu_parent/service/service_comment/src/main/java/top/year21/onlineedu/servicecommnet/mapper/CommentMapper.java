package top.year21.onlineedu.servicecommnet.mapper;

import org.apache.ibatis.annotations.Param;
import top.year21.onlineedu.servicecommnet.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author year21
 * @since 2022-09-03
 */
public interface CommentMapper extends BaseMapper<Comment> {
    //处理评论举报
    int reportComment(@Param("commentId") String commentId,@Param("status") Integer status);
}
