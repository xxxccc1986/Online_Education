package top.year21.onlineedu.serviceoss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.year21.onlineedu.serviceoss.service.OSSService;
import top.year21.onlineedu.serviceoss.untils.OSSConfigurationClass;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/21 16:31
 */
@Service
public class OSSServiceImpl implements OSSService{

    //上传头像到oss
    @Override
    public String uploadFile(MultipartFile file) {
        // Endpoint地址
        String endpoint = OSSConfigurationClass.END_POINT;
        // 阿里云账号AccessKey
        String accessKeyId = OSSConfigurationClass.ACCESS_KEY_ID;
        String accessKeySecret = OSSConfigurationClass.ACCESS_KEY_SECRET;
        // 填写Bucket名称
        String bucketName = OSSConfigurationClass.BUCKET_NAME;
        // 获取原始文件名称
        String objectName = file.getOriginalFilename();

        //获取文件后缀
        String suffix = objectName.substring(objectName.lastIndexOf("."));
        //拼接新的文件名用于上传,防止上传文件名字相同被覆盖
        String fileName = UUID.randomUUID().toString();
        String fileUploadName = fileName + suffix;

        //将文件按照日期进行分类
        //获取当前日期
        String datePath = new DateTime().toString("yyyy-MM-dd");

        //拼接最终上传的文件名
        String endUploadPath = datePath + "/" + fileUploadName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();
            /* 调用oss方法实现上传
           第一个参数为bucker名称
           第二个参数为上传到oss文件路径和文件名称
           第三个参数为文件的上传流
             */
            ossClient.putObject(bucketName, endUploadPath, inputStream);

            //将上传成功后的路径返回,需要手动拼接，oss没有方法实现
            //oss的访问路径为https://onlineedufile.oss-cn-guangzhou.aliyuncs.com/2.jpg
            String uploadPath = "https://"+ bucketName+ "." + endpoint + "/" + endUploadPath;

            return uploadPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
