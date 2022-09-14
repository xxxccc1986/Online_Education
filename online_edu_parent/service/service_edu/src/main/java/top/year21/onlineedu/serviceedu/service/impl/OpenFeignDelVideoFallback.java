package top.year21.onlineedu.serviceedu.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.serviceedu.service.OpenFeignDelVideo;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: OpenFeignDelVideo远程调用接口的fallback兜底类
 * @date 2022/8/26 23:37
 */
@Slf4j
@Component
public class OpenFeignDelVideoFallback implements OpenFeignDelVideo {
    @Override
    public JsonResult<Void> delVideoInAliyun(String videoSourceId){
        log.info("删除阿里云的视频失败，删除失败的视频id是：" + videoSourceId);
        return new JsonResult<>(false);
    }
}
