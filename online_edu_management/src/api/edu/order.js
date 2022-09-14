import request from '@/utils/request'

export default{
    getOrders(pageNum,pageSize,voOrder,status){
        return request({
            url: `/serviceorder/order/allOrder/${pageNum}/${pageSize}/${status}`,
            method: 'post',
            data: voOrder,
        })
    },
    delOrder(ids){
        return request({
            url: `/serviceorder/order/del/${ids}`,
            method: 'post',
        })
    }
}