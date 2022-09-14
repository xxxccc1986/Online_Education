package top.year21.onlineedu.servicecms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.servicecms.entity.Banner;
import top.year21.onlineedu.servicecms.service.ICrmBannerService;

import java.util.List;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/8/27 19:43
 */
@RestController
@RequestMapping("/servicecms/bannerfront")
public class BannerFrontController {

    @Autowired
    private ICrmBannerService bannerService;

    //查询所有banner
    @GetMapping("/queryAll")
    public JsonResult<List<Banner>> queryBanners(){
        List<Banner> banners = bannerService.queryAllBanner();
        return new JsonResult<>(true,"查询成功",banners);
    }
}
