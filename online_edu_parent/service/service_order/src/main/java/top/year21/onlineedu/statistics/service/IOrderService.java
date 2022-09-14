package top.year21.onlineedu.statistics.service;

import top.year21.onlineedu.statistics.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author year21
 * @since 2022-08-31
 */
public interface IOrderService extends IService<Order> {

    String createOrder(String courseId, String userId);

    Boolean judgeIsBuyCourseOrNot(String courseId, String userId);
}
