import request from '@/utils/request'

export default{
    getBanner(){
        return request({
            url: '/servicecms/bannerfront/queryAll',
            method: 'get'
        })
    }
}