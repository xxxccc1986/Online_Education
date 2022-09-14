package top.year21.onlineedu.statistics.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.exception.CommonException;
import top.year21.onlineedu.statistics.service.IPayLogService;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-08-31
 */
@RestController
@RequestMapping("/serviceorder/paylog")
@Slf4j
public class PayLogController {

    @Autowired
    private IPayLogService payLogService;

    //创建订单支付二维码
    @GetMapping("/createCode/{orderNo}")
    public JsonResult<Map<String,Object>> createCode(@PathVariable("orderNo") String orderNo){
        //返回的map集合中包含了二维码地址，还要其他需要的信息
        Map<String,Object> info = payLogService.createCode(orderNo);
        log.info("=====创建二维码map：" + info);
        return new JsonResult<>(true,"创建成功",info);
    }

    //通过订单号查询订单状态
    @GetMapping("/queryStatus/{orderNo}")
    public JsonResult<Void> queryOrderStatus(@PathVariable("orderNo") String orderNo){
        //查询指定的订单
        Map<String,String> map = payLogService.queryOrderStatus(orderNo);
        log.info("*****查询订单状态map：" + map);
        if (map == null) {//map为空表示支付失败
            throw new CommonException(30001, "支付失败");
        }

        //map不为空表示支付成功
        if("SUCCESS".equals(map.get("trade_state"))){
            //向支付表中添加数据并更新此订单状态
            payLogService.insertDateAndUpdateStatus(map);
            return new JsonResult<>(true);
        }

        return new JsonResult<>(false);
    }

}
