package top.year21.onlineedu.servicevod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.year21.onlineedu.exception.CommonException;
import top.year21.onlineedu.servicevod.entity.UploadConfigClass;
import top.year21.onlineedu.servicevod.entity.VodInitClass;
import top.year21.onlineedu.servicevod.service.VideoUploadService;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/26 1:01
 */
@Service
public class VideoServiceUploadServiceImpl implements VideoUploadService {


    //处理视频文件上传
    @Override
    public String uploadVideo(MultipartFile file) {
        String fileName = file.getOriginalFilename(); //上传文件的原始名称
        String title = fileName.substring(0,fileName.lastIndexOf(".")); //上传成功到阿里云显示的名称
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UploadStreamRequest request = new UploadStreamRequest(UploadConfigClass.ACCESS_KEY_ID,UploadConfigClass.ACCESS_KEY_SECRET, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            return response.getVideoId();
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            return response.getVideoId();
        }
    }

    //根据视频id删除视频
    @Override
    public void delUploadVideo(String videoSourceId){
        try {
            //1.创建初始化对象
            DefaultAcsClient client = VodInitClass.initVodClient(UploadConfigClass.ACCESS_KEY_ID, UploadConfigClass.ACCESS_KEY_SECRET);
            //2.创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //3.向request对象中设置需要删除的视频id
            request.setVideoIds(videoSourceId);
            //4.调用初始化对象方法将request传入删除视频
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
