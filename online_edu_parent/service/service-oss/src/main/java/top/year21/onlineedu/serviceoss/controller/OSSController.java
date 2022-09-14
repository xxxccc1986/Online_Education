package top.year21.onlineedu.serviceoss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.serviceoss.service.OSSService;
import top.year21.onlineedu.serviceoss.untils.OSSConfigurationClass;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/21 16:29
 */
@RestController
@RequestMapping("/serviceoss")
public class OSSController {

    @Autowired
    private OSSService ossService;

    @GetMapping("/oss")
    public String getOss(){
        return "oss的信息是："
                + OSSConfigurationClass.END_POINT + "\n"
                + OSSConfigurationClass.ACCESS_KEY_ID  + "\n"
                + OSSConfigurationClass.ACCESS_KEY_SECRET  + "\n"
                + OSSConfigurationClass.BUCKET_NAME;
    }

    /*
    上传文件的方法
     */
    @PostMapping("/upload")
    public JsonResult<String> uploadFile(MultipartFile file){
        //当上传成功后，返回存储的图片地址
        String filePathFromOSS = ossService.uploadFile(file);

        //将图片地址返回
        return new JsonResult<>(true,"上传成功",filePathFromOSS);
    }
}
