package top.year21.onlineedu.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import top.year21.onlineedu.exception.InsertException;
import top.year21.onlineedu.commonvo.VOCourseDetails;
import top.year21.onlineedu.commonvo.VOUserCenter;
import top.year21.onlineedu.statistics.entity.Order;
import top.year21.onlineedu.statistics.mapper.OrderMapper;
import top.year21.onlineedu.statistics.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.year21.onlineedu.statistics.service.OpenFeignTransferEdu;
import top.year21.onlineedu.statistics.service.OpenFeignTransferUserCenter;
import top.year21.onlineedu.statistics.utils.OrderNoUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;


/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-08-31
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    private OpenFeignTransferEdu transferEdu;
    @Resource
    private OpenFeignTransferUserCenter transferUserCenter;

    //根据课程id创建订单
    @Override
    public String createOrder(String courseId, String userId) {

        //根据课程id远程调用查询课程信息
        VOCourseDetails courseDetails = transferEdu.orderCourseDetailsById(courseId);

        //根据课程id远程调用查询用户信息
        VOUserCenter userInfo = transferUserCenter.getUserInfoById(userId);

        //分别取出创建订单所需的信息

        String orderNo = OrderNoUtil.getOrderNo();
        String courseTitle = courseDetails.getTitle();
        String courseCover = courseDetails.getCover();
        String teacherName = courseDetails.getName();
        String nickname = userInfo.getNickname();
        String mobile = userInfo.getMobile();
        BigDecimal totalFee = courseDetails.getPrice();

        //创建订单对象并填充数据
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setCourseId(courseId);
        order.setCourseTitle(courseTitle);
        order.setCourseCover(courseCover);
        order.setTeacherName(teacherName);
        order.setMemberId(userId);
        order.setNickname(nickname);
        order.setMobile(mobile);
        order.setTotalFee(totalFee);

        //插入数据库
        int result = this.baseMapper.insert(order);

        if (result == 0){
            throw new InsertException(30001,"订单创建失败");
        }

        return order.getOrderNo();
    }

    //通过课程id和用户id查询是否购买此课程
    @Override
    public Boolean judgeIsBuyCourseOrNot(String courseId, String userId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId)
               .eq("member_id",userId)
               .eq("status",1);
        Long result = this.baseMapper.selectCount(wrapper);
        //返回查询结果
        return result != 0;

    }

}
