package top.year21.onlineedu.serviceedu.service;

import top.year21.onlineedu.serviceedu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author year21
 * @since 2022-08-18
 */
public interface IEduTeacherService extends IService<EduTeacher> {

    //查询排序后的讲师信息，用于首页展示
    List<EduTeacher> getSortTeacher();
    //查询讲师信息用于首页显示讲师
    HashMap<String, Object> getFrontTeacherList(Integer pageNum, Integer pageSize);



}
