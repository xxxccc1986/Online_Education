package top.year21.onlineedu.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.year21.onlineedu.exception.InsertException;
import top.year21.onlineedu.serviceedu.entity.EduChapter;
import top.year21.onlineedu.serviceedu.entity.EduCourse;
import top.year21.onlineedu.serviceedu.entity.EduCourseDescription;
import top.year21.onlineedu.serviceedu.entity.EduTeacher;
import top.year21.onlineedu.serviceedu.mapper.EduCourseMapper;
import top.year21.onlineedu.serviceedu.service.IEduChapterService;
import top.year21.onlineedu.serviceedu.service.IEduCourseDescriptionService;
import top.year21.onlineedu.serviceedu.service.IEduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.year21.onlineedu.serviceedu.vo.VOCourse;
import top.year21.onlineedu.serviceedu.vo.VOFrontCourse;
import top.year21.onlineedu.serviceedu.vo.VOFrontCourseDetails;
import top.year21.onlineedu.serviceedu.vo.VOPublish;

import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-08-22
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements IEduCourseService {

    //需要使用课程简介信息的业务类来完成数据的插入
    @Autowired
    private IEduCourseDescriptionService descriptionService;
    @Autowired
    private IEduChapterService eduChapterService;

    //添加课程
    @Override
    public String addCourse(VOCourse voCourse) {
        //1.添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(voCourse,eduCourse);
        int result= baseMapper.insert(eduCourse);

        if (result == 0){
            throw new InsertException(30000,"出现异常,课程添加失败");
        }

        //获取已经插入数据库的课程信息主键
        String courseId = eduCourse.getId();

        //2.添加课程简介信息
        EduCourseDescription description = new EduCourseDescription();
        //设置课程简介信息对象id，用于和课程信息对象维持关联
        description.setId(courseId);
        description.setDescription(voCourse.getDescription());
        boolean insertResult = descriptionService.save(description);
        if (!insertResult){
            throw new InsertException(30000,"出现异常,课程简介信息添加失败");
        }

        return courseId;

    }

    //根据id查询信息
    @Override
    public VOCourse queryCourseInfoById(String id) {
        //创建一个对象封装查询出的信息
        VOCourse voCourse = new VOCourse();

        //根据id查询课程信息并填充至封装对象
        EduCourse eduCourse = this.baseMapper.selectById(id);
        BeanUtils.copyProperties(eduCourse,voCourse);
        //根据id课程简介信息并填充至封装对象
        EduCourseDescription description = descriptionService.getById(id);
        voCourse.setDescription(description.getDescription());

        return voCourse;
    }

    //根据id修改信息
    @Override
    public void updateCourseInfoById(VOCourse voCourse) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(voCourse,eduCourse);

        //根据id修改课程信息
        int result = this.baseMapper.updateById(eduCourse);

        if (result == 0){
            throw new InsertException(30001,"修改课程信息失败");
        }

        //根据id修改课程简介信息
        EduCourseDescription description = new EduCourseDescription();
        description.setId(voCourse.getId());
        description.setDescription(voCourse.getDescription());
        boolean updateResult = descriptionService.updateById(description);
        if (!updateResult){
            throw new InsertException(30001,"修改课程信息失败");
        }

    }

    //根据指定id查询复合封装的课程信息对象
    @Override
    public VOPublish queryVOPublishById(String id) {
        return this.baseMapper.getVOPublishById(id);
    }

    //根据指定id修改课程状态为已发布
    @Override
    public void updateStatus(String courseId) {
        int result = this.baseMapper.updateStatus(courseId);
        if (result == 0){
            throw new RuntimeException("修改失败");
        }
    }

    //条件查询
    @Override
    public Page<EduCourse> queryCondition(Integer pageNum, Integer pageSize) {
        Page<EduCourse> page = new Page<>(pageNum, pageSize);
        return this.baseMapper.selectPage(page, null);
    }


    //删除课程
    @Override
    @Transactional
    public void delCourseById(String courseId){
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<EduChapter> chapters = eduChapterService.list(queryWrapper);
        //表示当前课程下有章节和小节，需要批量删除
        if (chapters.size() != 0){
            for(EduChapter chapter : chapters){
                eduChapterService.delChapterById(chapter.getId());
            }
        }
        //以及删除课程描述
        boolean remove = descriptionService.removeById(courseId);
        if (!remove){
            throw new RuntimeException("删除指定课程描述失败，课程id是：" + courseId);
        }
        //当chapters.size()长度为0时，表示这个课程没有章节和小节，因此只需要删除当前课程即可
        int result = this.baseMapper.deleteById(courseId);
        if (result == 0){
            throw new RuntimeException("删除指定课程失败，课程id是：" + courseId);
        }

    }

    //查询排序后的课程信息，用于首页展示
    @Override
    @Cacheable(value = "course",key = "'selectCourse'")
    public List<EduCourse> getHotCourse() {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_count")
                .ne("is_deleted",1)
                .last("limit 8");
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public HashMap<String, Object> showCourseListByCondition(Integer pageNum, Integer pageSize, VOFrontCourse course) {

        //创建wrapper对象
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        //取出封装对象的各个数据
        String title = course.getTitle();
        String subjectParentId = course.getSubjectParentId();//一级id
        String subjectId = course.getSubjectId(); //二级id
        String viewCountSort = course.getViewCountSort();//关注度排序
        String createSort = course.getGmtCreateSort(); //时间排序
        String priceSort = course.getPriceSort(); //价格排序

        //判断数据是否为空决定是否需要添加至查询条件中
        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(viewCountSort)){
            wrapper.orderByDesc("view_count");
        }
        if (!StringUtils.isEmpty(createSort)){
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(priceSort)){
            wrapper.orderByDesc("price");
        }

        //创建page对象
        Page<EduCourse> page = new Page<>(pageNum, pageSize);

        //执行查询，数据封装在page对象中
        this.baseMapper.selectPage(page,wrapper);

        //为了分页效果，将数据从page中取出
        HashMap<String, Object> map = new HashMap<>();
        List<EduCourse> lists = page.getRecords();
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

    //根据课程id查询课程详情信息
    @Override
    public VOFrontCourseDetails queryCourseDetailsById(String courseId) {
        return this.baseMapper.queryCourseDetails(courseId);
    }
}
