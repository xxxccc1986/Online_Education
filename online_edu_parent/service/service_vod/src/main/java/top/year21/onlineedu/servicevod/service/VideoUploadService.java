package top.year21.onlineedu.servicevod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/26 1:00
 */
public interface VideoUploadService {


    String uploadVideo(MultipartFile file);

    void delUploadVideo(String videoSourceId);

}
