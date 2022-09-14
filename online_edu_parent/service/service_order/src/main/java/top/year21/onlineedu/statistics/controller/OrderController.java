package top.year21.onlineedu.statistics.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import top.year21.onlineedu.JsonResult;
import top.year21.onlineedu.JwtUtils;
import top.year21.onlineedu.exception.CommonException;
import top.year21.onlineedu.statistics.entity.Order;
import top.year21.onlineedu.statistics.service.IOrderService;
import top.year21.onlineedu.statistics.vo.VOOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author year21
 * @since 2022-08-31
 */
@RestController
@RequestMapping("/serviceorder/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    //通过课程id创建订单
    @PostMapping("/createOrder/{courseId}")
    public JsonResult<String> createOrderByCourseId(@PathVariable("courseId")String courseId,
                                                    HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        String orderId = orderService.createOrder(courseId,userId);
        return new JsonResult<>(true,"订单创建成功",orderId);

    }

    //通过订单号查询订单详情
    @GetMapping("/query/{orderNo}")
    public JsonResult<Order> queryOrderDetailsById(@PathVariable("orderNo") String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        return new JsonResult<>(true,"查询成功",order);
    }

    //通过课程id和用户id查询是否购买此课程
    @GetMapping("/judgeIsBuyOrNot/{courseId}")
    public JsonResult<Boolean> judgeIsBuyCourseOrNot(@PathVariable("courseId")String courseId,
                                                     HttpServletRequest request){
        //通过token获取用户id
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        Boolean result = orderService.judgeIsBuyCourseOrNot(courseId,userId);
        return new JsonResult<>(true,"查询成功",result);
    }

    /* ********* 分界线，自上为处理前台系统请求，自下为处理后台系统请求 *********/

    //分页查询所有已支付订单和未支付订单
    @PostMapping("/allOrder/{pageNum}/{pageSize}/{status}")
    public JsonResult<Page<Order>> getAllOrder(@PathVariable("pageNum") Long pageNum,
                                               @PathVariable("pageSize") Long pageSize,
                                               @RequestBody(required = false) VOOrder voOrder,
                                               @PathVariable("status") Integer status){

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        Page<Order> orderPage = new Page<>(pageNum, pageSize);
        //取出封装的条件查询语句
        String nickname = voOrder.getNickname();
        String teacherName = voOrder.getTeacherName();
        String courseId = voOrder.getCourseId();
        Date startTime = voOrder.getStartTime();
        Date endTime = voOrder.getEndTime();
        //判断是否要添加为查询添加
        if (!StringUtils.isEmpty(nickname)){
            wrapper.eq("nickname",nickname);
        }
        if (!StringUtils.isEmpty(teacherName)){
            wrapper.eq("teacher_name",teacherName);
        }
        if (!StringUtils.isEmpty(courseId)){
            wrapper.eq("course_id",courseId);
        }
        if (!StringUtils.isEmpty(startTime)){
            wrapper.gt("gmt_create",startTime);
        }
        if (!StringUtils.isEmpty(endTime)){
            wrapper.lt("gmt_create",endTime);
        }

        if (status == 1){ //表示查询已支付的订单
            wrapper.eq("status",1);
        }else {
            wrapper.eq("status",0);
        }
        //执行查询并返回查询数据
        orderService.page(orderPage,wrapper);
        return new JsonResult<>(true,"查询成功",orderPage);
    }


    //删除订单
    @PostMapping("/del/{ids}")
    public JsonResult<Void> delOrder(@PathVariable("ids") String[] ids){
        List<String> delIds = new ArrayList<>(Arrays.asList(ids));
        boolean result = orderService.removeBatchByIds(delIds);
        if (!result){
            throw new CommonException(30001,"删除订单失败！");
        }
        return new JsonResult<>(true);
    }

    //分页查询所有已支付订单和未支付订单
    @PostMapping("/FontAllOrder/{uid}/{pageNum}/{pageSize}/{status}")
    public JsonResult<Page<Order>> getFontAllOrder(@PathVariable("uid") String uid,
                                                   @PathVariable("pageNum") Integer pageNum,
                                                   @PathVariable("pageSize") Integer pageSize,
                                                   @PathVariable("status") Integer status){

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",uid);
        Page<Order> orderPage = new Page<>(pageNum, pageSize);

        if (status == 1){ //表示查询已支付的订单
            wrapper.eq("status",1);
        }else {
            wrapper.eq("status",0);
        }
        //执行查询并返回查询数据
        orderService.page(orderPage,wrapper);
        return new JsonResult<>(true,"查询成功",orderPage);
    }








}
