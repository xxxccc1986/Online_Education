package top.year21.onlineedu.statistics.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.statistics.service.IDailyService;

import java.util.Map;


/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/servicedaily")
public class DailyController {

    @Autowired
    private IDailyService dailyService;


    //统计某一天的注册人数
    @PostMapping("/countNum/{day}")
    public JsonResult<Void> countRegisterNum(@PathVariable("day") String day){
        dailyService.countRegisterNumInOneDay(day);
        return new JsonResult<>(true);
    }

    //根据查询类型显示数据
    @GetMapping("/showTableData/{type}/{begin}/{end}")
    public JsonResult<Map<String,Object>> showTableData(@PathVariable("type") String type,
                                                        @PathVariable("begin") String begin,
                                                        @PathVariable("end") String end){
            Map<String,Object> data = dailyService.showTableData(type,begin,end);
            return new JsonResult<>(true,"查询成功",data);
    }

}
