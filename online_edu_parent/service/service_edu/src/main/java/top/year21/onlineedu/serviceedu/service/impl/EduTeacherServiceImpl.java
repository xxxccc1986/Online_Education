package top.year21.onlineedu.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cache.annotation.Cacheable;
import top.year21.onlineedu.serviceedu.entity.EduTeacher;
import top.year21.onlineedu.serviceedu.mapper.EduTeacherMapper;
import top.year21.onlineedu.serviceedu.service.IEduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-08-18
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements IEduTeacherService {

    //查询排序后的讲师信息，用于首页展示
    @Override
    @Cacheable(value = "teacher",key = "'selectTeacher'")
    public List<EduTeacher> getSortTeacher() {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create")
                .ne("is_deleted",1)
                .last("limit 4");
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public HashMap<String, Object> getFrontTeacherList(Integer pageNum, Integer pageSize) {
        //添加查询条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        Page<EduTeacher> page = new Page<>(pageNum,pageSize);
        this.baseMapper.selectPage(page,wrapper);
        //从page中取出数据，
        HashMap<String, Object> map = new HashMap<>();
        List<EduTeacher> lists = page.getRecords();
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
}
