package top.year21.onlineedu.servicecommnet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import top.year21.onlineedu.JwtUtils;
import top.year21.onlineedu.commonvo.VOCourseDetails;
import top.year21.onlineedu.commonvo.VOUserCenter;
import top.year21.onlineedu.exception.CommonException;
import top.year21.onlineedu.exception.InsertException;
import top.year21.onlineedu.servicecommnet.entity.Comment;
import top.year21.onlineedu.servicecommnet.mapper.CommentMapper;
import top.year21.onlineedu.servicecommnet.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.year21.onlineedu.servicecommnet.service.OpenFeignTransferEdu;
import top.year21.onlineedu.servicecommnet.service.OpenFeignTransferUserCenter;
import top.year21.onlineedu.servicecommnet.vo.VOComment;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-09-03
 */
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private OpenFeignTransferEdu transferEdu;
    @Resource
    private OpenFeignTransferUserCenter transferUserCenter;

    //创建课程评论 业务层
    @Override
    public void createCommnet(String content,String courseId,Integer rate,HttpServletRequest request) {
        //获取课程详细信息
        VOCourseDetails courseDetails = transferEdu.orderCourseDetailsById(courseId);
        //获取用户信息
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        VOUserCenter userInfo = transferUserCenter.getUserInfoById(userId);

        //拼凑插入数据库的对象
        Comment comment = new Comment();
        comment.setCourseId(courseId);
        comment.setTeacherId(courseDetails.getTeacherId());
        comment.setMemberId(userId);
        comment.setNickname(userInfo.getNickname());
        comment.setAvatar(userInfo.getAvatar());
        comment.setContent(content);
        comment.setRate(rate);

        //执行插入操作
        int result = this.baseMapper.insert(comment);

        if (result == 0){
            throw new InsertException(30001,"创建评论数据失败！");
        }

    }

    //查看课程评论
    @Override
    public Map<String, Object> showComment(Integer pageNum, Integer pageSize, String courseId, String teacherId) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId).eq("teacher_id",teacherId).orderByDesc("gmt_create");
        this.baseMapper.selectPage(page,wrapper);

        //为了分页效果，将数据从page中取出
        HashMap<String, Object> map = new HashMap<>();
        List<Comment> lists = page.getRecords();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        //保存到map中并返回
        map.put("lists",lists);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }

    //**************以此为分界线，自上前台请求处理方法，自下后台请求处理方法*********************

    //处理评论举报
    @Override
    public void reportComment(String commentId,Integer status) {
        int result = this.baseMapper.reportComment(commentId,status);
        if (result == 0){
            throw new CommonException(30001,"处理举报评论失败！");
        }
    }

    //获取所有的评论
    @Override
    public Map<String,Object> getAllComment(Integer pageNum, Integer pageSize, VOComment voComment) {
        Page<Comment> page = new Page<>(pageNum, pageSize);

        //表示为条件查询
        if (voComment != null){
            QueryWrapper<Comment> wrapper = new QueryWrapper<>();

            //获取所有的查询条件
            String username = voComment.getUsername();
            String courseId = voComment.getCourseId();
            String teacherId = voComment.getTeacherId();
            Date startTime = voComment.getStartTime();
            Date endTime = voComment.getEndTime();
            Integer isReported = voComment.getIsReported();

            if (!StringUtils.isEmpty(username)){
                wrapper.like("nickname",username);
            }
            if (!StringUtils.isEmpty(courseId)){
                wrapper.like("course_id",courseId);
            }
            if (!StringUtils.isEmpty(teacherId)){
                wrapper.like("teacher_id",teacherId);
            }
            if (!StringUtils.isEmpty(startTime)){
                wrapper.gt("gmt_create",startTime);
            }
            if (!StringUtils.isEmpty(endTime)){
                wrapper.lt("gmt_create",endTime);
            }
            if (!StringUtils.isEmpty(isReported)){
                wrapper.eq("is_reported",1);
            }
            this.baseMapper.selectPage(page,wrapper);
        }else {
            this.baseMapper.selectPage(page,null);
        }

        //获取查询的列表并初始化需要封装数据的集合
        List<Comment> records = page.getRecords();
        List<VOComment> voComments = new ArrayList<>();
        //根据要返回的
        for(Comment comment : records){
            VOComment vo = new VOComment();
            //获取课程id、用户名、评论内容、评论时间
            String courseId = comment.getCourseId();
            String nickname = comment.getNickname();
            String content = comment.getContent();
            Date gmtCreate = comment.getGmtCreate();
            //通过课程id远程调用查询课程名称和讲师名称
            VOCourseDetails courseDetails = transferEdu.orderCourseDetailsById(courseId);
            //封装返回的查询数据
            vo.setId(comment.getId());
            vo.setUsername(nickname);
            vo.setCourseName(courseDetails.getTitle());
            vo.setTeacherName(courseDetails.getName());
            vo.setContent(content);
            vo.setRate(comment.getRate());
            vo.setGmtCreate(gmtCreate);
            voComments.add(vo);
        }

        //封装需要返回的查询结果
        HashMap<String, Object> map = new HashMap<>();
        map.put("comments",voComments);
        map.put("current",page.getCurrent());
        map.put("pages",page.getPages());
        map.put("size",page.getSize());
        map.put("total",page.getTotal());

        return map;
    }

    //远程调用查询讲师指定字段信息
    @Override
    public List<VOCourseDetails> showTeacher() {
        return transferEdu.selectTeacherColumn();
    }

    //远程调用查询课程指定字段信息
    @Override
    public List<VOCourseDetails> showCourse() {
        return transferEdu.selectCourseColumn();
    }
}
