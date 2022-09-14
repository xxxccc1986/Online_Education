package top.year21.onlineedu.servicevod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.exception.CommonException;
import top.year21.onlineedu.servicevod.entity.UploadConfigClass;
import top.year21.onlineedu.servicevod.entity.VodInitClass;
import top.year21.onlineedu.servicevod.service.VideoUploadService;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/26 0:57
 */
@RestController
@RequestMapping("/servicevod")
public class VideoController {

    @Autowired
    private VideoUploadService videoUploadService;

    @PostMapping("/videoUpload") 
    public JsonResult<String> uploadVideoToAliyun(@RequestPart MultipartFile file){
        String videoSourceId = videoUploadService.uploadVideo(file);
        return new JsonResult<>(true,"上传成功",videoSourceId);
    }

    @PostMapping("/videoDel/{videoSourceId}")
    public JsonResult<Void> delVideoInAliyun(@PathVariable("videoSourceId") String videoSourceId){
        videoUploadService.delUploadVideo(videoSourceId);
        return new JsonResult<>(true);
    }

    @GetMapping("/videovoucher/{videoSourceId}")
    public JsonResult<String> getVideoVoucherById(@PathVariable("videoSourceId") String videoSourceId){
        try {
            //1.创建初始化对象
            DefaultAcsClient defaultAcsClient = VodInitClass.initVodClient(UploadConfigClass.ACCESS_KEY_ID, UploadConfigClass.ACCESS_KEY_SECRET);
            //2.创建response对象和request对象
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //3.向request对象中设置视频id
            request.setVideoId(videoSourceId);

            //4.调用初始化对象中的方法，将request对象传入获取视频信息并赋值给response对象
            response = defaultAcsClient.getAcsResponse(request);

            //返回播放凭证
            String videoAuth = response.getPlayAuth();
            return new JsonResult<>(true,"查询成功",videoAuth);
        } catch (Exception e) {
            return new JsonResult<>(false,"获取视频凭证失败，错误信息是：",e.getMessage());
        }
    }
}
