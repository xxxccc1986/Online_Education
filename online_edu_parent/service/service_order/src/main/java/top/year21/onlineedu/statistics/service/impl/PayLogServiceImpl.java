package top.year21.onlineedu.statistics.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import top.year21.onlineedu.exception.CommonException;
import top.year21.onlineedu.statistics.entity.Order;
import top.year21.onlineedu.statistics.entity.PayLog;
import top.year21.onlineedu.statistics.mapper.PayLogMapper;
import top.year21.onlineedu.statistics.service.IOrderService;
import top.year21.onlineedu.statistics.service.IPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.year21.onlineedu.statistics.utils.HttpClient;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author year21
 * @since 2022-08-31
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements IPayLogService {

    @Autowired
    private IOrderService orderService;

    //创建微信支付二维码
    @Override
    public Map<String, Object> createCode(String orderNo) {
        try {
            //1.根据订单查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);
            //2.使用map设置生成二维码需要的参数
            Map<String,String> map = new HashMap<>();
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle()); //课程标题
            map.put("out_trade_no", orderNo); //订单号
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            map.put("trade_type", "NATIVE");
            //3.使用httpClient工具类发送请求到微信支付提供的地址，传递的参数必须是xml格式，
            HttpClient client =  new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //发送请求
            client.post();
            //4.得到发送请求返回结果
            String contentXml = client.getContent(); //返回的内容也是xml格式
            //将xml转为map返回
            Map<String,String> resultMap = WXPayUtil.xmlToMap(contentXml);
            //由于支付还需要其他在resultMap中不存在的信息，因此额外使用一个map封装最终返回结果
            Map<String,Object> endMap = new HashMap<>();
            endMap.put("out_trade_no", orderNo);
            endMap.put("course_id", order.getCourseId());
            endMap.put("total_fee", order.getTotalFee());
            endMap.put("result_code", resultMap.get("result_code"));
            endMap.put("code_url", resultMap.get("code_url"));

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
//            redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);
            return endMap;
        }catch (Exception e){
            throw new CommonException(30001,"生成二维码失败，错误信息是：" + e.getMessage());
        }

    }

    //查询订单状态
    @Override
    public Map<String, String> queryOrderStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //向payLog表中添加数据并修改order订单状态
    @Override
    public void insertDateAndUpdateStatus(Map<String, String> map) {
        //获取订单id
        String orderNo = map.get("out_trade_no");
        //根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);

        if(order.getStatus() == 1) return;
        order.setStatus(1);
        orderService.updateById(order);

        //记录支付日志
        PayLog payLog=new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map)); //map中的其他信息
        baseMapper.insert(payLog);//插入到支付日志表
    }
}
