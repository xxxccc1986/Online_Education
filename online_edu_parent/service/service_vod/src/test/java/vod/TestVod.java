package vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/25 23:42
 */
public class TestVod {


    public static void main(String[] args) throws Exception{
        getPlayAuth();
//        uploadVideo();
    }

    //测试视频上传
    public static void  uploadVideo(){
        String accessKeyId = "LTAI5tHuNsemaiLnP3yro4vD";
        String accessKeySecret = "ed1Zxok1SklTEI40HhRKkJeRcQdyWc";
        String title = "bluesky-uploadBySdk"; //设置上传之后的文件名称
        String fileName = "C:/Users/hcxs1986/Desktop/解压专用文件夹/bluesky.mp4"; //本地文件路径和名称
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，（注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }


    //根据视频id获取视频地址
    public static void GetPlayUrl() throws Exception{
        //1.创建初始化对象
        DefaultAcsClient defaultAcsClient = VodInitObject.initVodClient("LTAI5tHuNsemaiLnP3yro4vD", "ed1Zxok1SklTEI40HhRKkJeRcQdyWc");
        //2.创建response对象和request对象
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetPlayInfoRequest request = new GetPlayInfoRequest();

        //3.向request对象中设置视频id
        request.setVideoId("3e0d11654ac6483f9f0311f8a92fecff");

        //4.将初始化对象的方法，传入request对象，获取数据并封装到response对象中
        response = defaultAcsClient.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    //根据视频id获取视频凭证
    public static void getPlayAuth() throws Exception{
        //1.创建初始化对象
        DefaultAcsClient defaultAcsClient = VodInitObject.initVodClient("LTAI5tHuNsemaiLnP3yro4vD", "ed1Zxok1SklTEI40HhRKkJeRcQdyWc");
        //2.创建response对象和request对象
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

        //3.向request对象中设置视频id
        request.setVideoId("4b73b4382ae940f0aebe1aad57edaba2");

        //4.调用初始化对象中的方法，将request对象传入获取视频信息并赋值给response对象
        response = defaultAcsClient.getAcsResponse(request);

        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
    }
}
