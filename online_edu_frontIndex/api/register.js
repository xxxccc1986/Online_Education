import request from '@/utils/request'

export default{
    userRegister(VoRegister){
        return request({
            url: '/serviceusercenter/register',
            method: 'post',
            data: VoRegister
        })
    },
    getCode(phone){
        return request({
            url: '/servicemsm/send/'+ phone,
            method: 'get'
        })
    }
}