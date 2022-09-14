package top.year21.onlineedu.servicecms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.servicecms.entity.Banner;
import top.year21.onlineedu.servicecms.service.ICrmBannerService;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-08-27
 */
@RestController
@RequestMapping("/servicecms/banneradmin")
public class BannerAdminController {

    @Autowired
    private ICrmBannerService bannerService;

    //分页查询banner
    @GetMapping("/query/{pageNum}/{pageSize}")
    public JsonResult<Page<Banner>> queryBannerByCondition(@PathVariable("pageNum") Integer pageNum,
                                                           @PathVariable("pageSize") Integer pageSize){
        Page<Banner> page = new Page<>(pageNum, pageSize);
        bannerService.page(page,null);
        return new JsonResult<>(true,"查询成功",page);
    }

    //添加banner
    @PostMapping("/add")
    public JsonResult<Void> addBanner(@RequestBody Banner banner){
        bannerService.save(banner);
        return new JsonResult<>(true);
    }

    //修改banner
    @PostMapping("/update")
    public JsonResult<Void> updateBanner(@RequestBody Banner banner){
        UpdateWrapper<Banner> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",banner.getId());
        bannerService.update(banner,updateWrapper);
        return new JsonResult<>(true);
    }

    //删除banner
    @PostMapping("/del/{id}")
    public JsonResult<Void> delBanner(@PathVariable("id") String id){
        bannerService.removeById(id);
        return new JsonResult<>(true);
    }

    //根据id查询banner
    @GetMapping("/query/{id}")
    public JsonResult<Banner> queryBannerById(@PathVariable("id") String id){
        Banner banner = bannerService.getById(id);
        return new JsonResult<>(true,"查询成功",banner);
    }

    @GetMapping("/queryByTitle/{title}/{pageNum}/{pageSize}")
    public JsonResult<Page<Banner>> queryByTitle(@PathVariable("title") String title,
                                           @PathVariable("pageNum") Integer pageNum,
                                           @PathVariable("pageSize") Integer pageSize){
        QueryWrapper<Banner> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",title);
        Page<Banner> page = new Page<>(pageNum, pageSize);
        bannerService.page(page,queryWrapper);
        return new JsonResult<>(true,"查询成功",page);
    }



}
