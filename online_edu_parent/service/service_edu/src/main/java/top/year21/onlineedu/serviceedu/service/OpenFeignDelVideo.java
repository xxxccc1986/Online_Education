package top.year21.onlineedu.serviceedu.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.serviceedu.service.impl.OpenFeignDelVideoFallback;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/26 19:05
 */
@Component
//表示到nacos服务注册中心上查找这个名称的服务提供者，负责此接口服务降级的类是OpenFeignDelVideoFallback
@FeignClient(value = "aliyun-video-upload",fallback = OpenFeignDelVideoFallback.class)
public interface OpenFeignDelVideo {

    //通过这个rest地址去调用服务提供者中相应的方法执行业务
    @PostMapping("/servicevod/videoDel/{videoSourceId}")
    public JsonResult<Void> delVideoInAliyun(@PathVariable("videoSourceId") String videoSourceId);
}
