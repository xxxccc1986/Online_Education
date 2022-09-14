package top.year21.onlineedu.statistics.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/9/1 23:59
 */
@Component
@FeignClient("user-center")
public interface USerCenterTransfer {

    @GetMapping("/serviceusercenter/count/{day}")
    public Integer countRegisterNumInOneDay(@PathVariable("day")String day);
}
