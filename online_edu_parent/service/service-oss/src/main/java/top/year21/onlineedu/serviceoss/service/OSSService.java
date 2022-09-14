package top.year21.onlineedu.serviceoss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/21 16:31
 */
public interface OSSService {
    String uploadFile(MultipartFile file);
}
