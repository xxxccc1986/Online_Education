package top.year21.onlineedu.statistics.service;

import top.year21.onlineedu.statistics.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author year21
 * @since 2022-08-31
 */
public interface IPayLogService extends IService<PayLog> {
    //创建二维码
    Map<String, Object> createCode(String orderNo);
    //查询订单状态
    Map<String, String> queryOrderStatus(String orderNo);
    //向payLog表中添加数据并修改order订单状态
    void insertDateAndUpdateStatus(Map<String, String> map);
}
