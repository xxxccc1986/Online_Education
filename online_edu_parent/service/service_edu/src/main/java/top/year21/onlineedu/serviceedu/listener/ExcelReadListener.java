package top.year21.onlineedu.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import top.year21.onlineedu.exception.InsertException;
import top.year21.onlineedu.serviceedu.entity.EduSubject;
import top.year21.onlineedu.serviceedu.entity.excel.SubjectData;
import top.year21.onlineedu.serviceedu.service.IEduSubjectService;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/21 22:30
 */
public class ExcelReadListener extends AnalysisEventListener<SubjectData> {

    /*
    因为在ExcelReadListener是手动new出来的对象，没有交给spring进行管理，
    因此不能在ExcelReadListener内使用@Autowired自动装配在spring管理的的对象，
    需要在这一层使用eduSubjectService进行数据库操作必须通过上一层传入这个参数
    在ExcelReadListener中的eduSubjectService不传值的话是null的
     */
    private IEduSubjectService eduSubjectService;

    public ExcelReadListener() {}
    public ExcelReadListener(IEduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }


    //逐行读取excel的内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
            if (subjectData == null){
                throw new InsertException(30000,"文件内容为空");
            }
            //一行一行读取，每次读取都有两个值，第一个值为一级分类，第二个值为二级分类，
            //先判断一级分类是否重复
        EduSubject eduSubject = oneSubjectIsExists(eduSubjectService, subjectData.getOneSubjectName());
            if (eduSubject == null){ //若一级分类查询为空，则可以进行添加
                eduSubject = new EduSubject();
                eduSubject.setTitle(subjectData.getOneSubjectName());
                eduSubject.setParentId("0");
                eduSubjectService.save(eduSubject);
            }

            //获取一级分类的id，作为二级分类的parent_id
            String parentId = eduSubject.getId();

            //判断二级分类是否重复
        EduSubject twoSubject = twoSubjectIsExists(eduSubjectService, subjectData.getTwoSubjectName(), parentId);
            if (twoSubject == null){
                twoSubject = new EduSubject();
                twoSubject.setTitle(subjectData.getTwoSubjectName());
                twoSubject.setParentId(parentId);
                eduSubjectService.save(twoSubject);
            }
    }

    //读取完成之后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //判断一级分类不能重复添加
    public EduSubject oneSubjectIsExists(IEduSubjectService eduSubjectService,String oneSubjectName){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",oneSubjectName);
        queryWrapper.eq("parent_id","0");
        return eduSubjectService.getOne(queryWrapper);
    }

    //判断二级分类不能重复添加
    public EduSubject twoSubjectIsExists(IEduSubjectService eduSubjectService,String twoSubjectName,String parentId){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",twoSubjectName);
        queryWrapper.eq("parent_id",parentId);
        return eduSubjectService.getOne(queryWrapper);
    }
}
