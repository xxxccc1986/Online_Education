package top.year21.onlineedu.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import top.year21.onlineedu.serviceedu.entity.EduSubject;
import top.year21.onlineedu.serviceedu.entity.excel.SubjectData;
import top.year21.onlineedu.serviceedu.entity.subject.OneSubject;
import top.year21.onlineedu.serviceedu.entity.subject.TwoSubject;
import top.year21.onlineedu.serviceedu.listener.ExcelReadListener;
import top.year21.onlineedu.serviceedu.mapper.EduSubjectMapper;
import top.year21.onlineedu.serviceedu.service.IEduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-08-21
 */
@Service
@Slf4j
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements IEduSubjectService {

    @Autowired(required = false)
    private EduSubjectMapper eduSubjectMapper;

    //添加课程分类
    @Override
    public void addFile(MultipartFile file,IEduSubjectService eduSubjectService) {
        //获取上传文件的流形式
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new ExcelReadListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //查询课程分类(树形)
    @Override
    public List<OneSubject> selectAllSubject() {
        //用于封装最终返回数据的一级实体类
        List<OneSubject> oneDataEntity = new ArrayList<>();

        //查询所有一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectList = eduSubjectMapper.selectList(oneWrapper);

        //逐个遍历一级分类获取二级分类
        for (int i = 0; i < oneSubjectList.size(); i++) {

            //当前i索引指向集合中的一级分类对象
            EduSubject one = oneSubjectList.get(i);

            //查询所有二级分类
            QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
            twoWrapper.eq("parent_id",one.getId());

            //用于封装二级分类数据的二级实体类
            List<TwoSubject> twoDataEntity  = new ArrayList<>();

            //二级分类对象集合
            List<EduSubject> towSubjectList = eduSubjectMapper.selectList(twoWrapper);

            //遍历查找当前一级分类下的二级分类
            for (int j = 0; j < towSubjectList.size(); j++) {

                //当前符合一级分类为parent_id的二级分类对象
                EduSubject two = towSubjectList.get(j);

                //将每个二级分类对象赋值给二级封装对象并加入二级封装对象集合
                TwoSubject tSubject = new TwoSubject();
//                原生写法
//                tSubject.setId(two.getId());
//                tSubject.setTitle(two.getTitle());

                //spring提供的工具类，此方法是将two的属性值复制到tSubject中
                BeanUtils.copyProperties(two,tSubject);
                twoDataEntity.add(tSubject);
            }

            //将每个一级分类对象赋值给一级封装对象并加入一级封装对象集合
            OneSubject oSubject = new OneSubject();
//            oSubject.setId(one.getId());
//            oSubject.setTitle(one.getTitle());
//            oSubject.setChildren(twoDataEntity);
            //spring提供的工具类，此方法是将one的属性值复制到oSubject中,
            // 但只能赋值对应的属性，若属性不存在则不能赋值
            BeanUtils.copyProperties(one,oSubject);
            oSubject.setChildren(twoDataEntity);
            oneDataEntity.add(oSubject);
        }
        return oneDataEntity;
    }



}
