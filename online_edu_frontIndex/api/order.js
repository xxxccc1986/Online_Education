import request from '@/utils/request'

export default{
    //生成订单
    createOrder(courseId){
            return request({
                url:`/serviceorder/order/createOrder/${courseId}`,
                method:'post'
            })
    },
    //查询订单信息
    queryOrder(orderNo){
        return request({
            url:`/serviceorder/order/query/${orderNo}`,
            method:'get'
        })
    },
    //创建支付二维码
    createOrderCode(orderNo){
        return request({
            url:`/serviceorder/paylog/createCode/${orderNo}`,
            method:'get'
        })
    },
    //查询订单支付状态
    queryOrderPayStatus(orderNo){
        return request({
            url:`/serviceorder/paylog/queryStatus/${orderNo}`,
            method:'get'
        })
    },
    //根据订单状态获取订单
    getOrders(uid,pageNum,pageSize,status){
        return request({
            url: `/serviceorder/order/FontAllOrder/${uid}/${pageNum}/${pageSize}/${status}`,
            method: 'post'
        })
    },
    //根据id删除订单
    delOrder(ids){
        return request({
            url: `/serviceorder/order/del/${ids}`,
            method: 'post',
        })
    }
}
