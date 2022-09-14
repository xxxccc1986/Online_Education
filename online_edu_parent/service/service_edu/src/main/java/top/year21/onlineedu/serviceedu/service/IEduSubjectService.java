package top.year21.onlineedu.serviceedu.service;

import org.springframework.web.multipart.MultipartFile;
import top.year21.onlineedu.serviceedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import top.year21.onlineedu.serviceedu.entity.subject.OneSubject;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author year21
 * @since 2022-08-21
 */
public interface IEduSubjectService extends IService<EduSubject> {

    void addFile(MultipartFile file,IEduSubjectService eduSubjectService);

    List<OneSubject> selectAllSubject();
}
