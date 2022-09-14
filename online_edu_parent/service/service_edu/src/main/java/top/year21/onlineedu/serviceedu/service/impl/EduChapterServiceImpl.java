package top.year21.onlineedu.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.year21.onlineedu.exception.InsertException;
import top.year21.onlineedu.serviceedu.entity.EduChapter;
import top.year21.onlineedu.serviceedu.entity.EduVideo;
import top.year21.onlineedu.serviceedu.entity.course.CourseBar;
import top.year21.onlineedu.serviceedu.entity.course.CourseChapter;
import top.year21.onlineedu.serviceedu.mapper.EduChapterMapper;
import top.year21.onlineedu.serviceedu.service.IEduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.year21.onlineedu.serviceedu.service.IEduVideoService;

import java.util.ArrayList;
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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements IEduChapterService {

    @Autowired
    private IEduVideoService eduVideoService;

    //查询课程章节
    @Override
    public List<CourseChapter> getChapterListByCid(String courseId) {
        //用于封装最终返回所有课程章节对象的集合
        List<CourseChapter> courseChapterList = new ArrayList<>();

        //根据指定id查询课程章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        chapterWrapper.orderByAsc("sort");

        //得到指定id对应的课程章节集合
        List<EduChapter> chapterList = this.baseMapper.selectList(chapterWrapper);


        //遍历查询课程小节
        for (EduChapter chapter : chapterList) {
            //创建一个用个封装从数据库得到的章节对象到值对象CourseChapter中
            CourseChapter courseChapter = new CourseChapter();
            BeanUtils.copyProperties(chapter,courseChapter);

            //根据章节id查询所有小节集合
            QueryWrapper<EduVideo> barWrapper = new QueryWrapper<>();
            barWrapper.eq("chapter_id",chapter.getId());
            barWrapper.orderByAsc("sort");

            //得到指定课程章节的课程小节集合

            List<EduVideo> videoList = eduVideoService.list(barWrapper);

            //先创建一个用于封装课程小节对象的集合
            List<CourseBar> courseBarList = new ArrayList<>();

            for(EduVideo video : videoList){
                //创建一个用个封装从数据库得到的小节对象到值对象CourseBar中
                CourseBar courseBar = new CourseBar();
                BeanUtils.copyProperties(video,courseBar);
                //没查出一个就加入到courseBarList集合中
                courseBarList.add(courseBar);
            }

            //设置值对象courseChapter的第三个参数值
            courseChapter.setCourseBarList(courseBarList);
            //加入到封装courseChapter值对象的集合中
            courseChapterList.add(courseChapter);
        }

        return courseChapterList;
    }

    //根据指定课程id添加章节
    @Override
    public void addNewChapterById(EduChapter eduChapter) {
        int result = this.baseMapper.insert(eduChapter);
        if (result ==0 ){
            throw new InsertException(30001,"添加章节失败！");
        }
    }

    //根据指定课程id修改章节
    @Override
    public void updateChapterById(EduChapter eduChapter) {
        UpdateWrapper<EduChapter> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",eduChapter.getId());
        int update = this.baseMapper.update(eduChapter, updateWrapper);
        if (update ==0 ){
            throw new InsertException(30001,"更新章节失败！");
        }
    }

    //根据指定课程id删除章节
    @Override
    @Transactional
    public void delChapterById(String id){
        //先判断是否存在小节需要删除
        UpdateWrapper<EduVideo> wrapper = new UpdateWrapper<>();
        wrapper.eq("chapter_id",id);
        List<EduVideo> eduVideos =eduVideoService.list(wrapper);
        if (eduVideos.size() != 0){
            for(EduVideo video : eduVideos){
                eduVideoService.delBarById(video.getId());
            }
        }
        //再删除章节
        int result = this.baseMapper.deleteById(id);
        if (result == 0){
            throw new RuntimeException("删除指定章节失败,章节id是：" + id);
        }
    }

    //根据指定课程id查询章节
    @Override
    public EduChapter queryChapterById(String id) {
        return this.baseMapper.selectById(id);
    }


}
