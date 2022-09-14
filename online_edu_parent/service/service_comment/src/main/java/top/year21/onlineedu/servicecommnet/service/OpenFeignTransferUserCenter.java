package top.year21.onlineedu.servicecommnet.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.year21.onlineedu.commonvo.VOUserCenter;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/31 23:34
 */
@Component
@FeignClient("user-center")
public interface OpenFeignTransferUserCenter {

    //通过userId查询用户信息
    @GetMapping("/serviceusercenter/userInfo/{userId}")
    public VOUserCenter getUserInfoById(@PathVariable("userId") String userId);
}
