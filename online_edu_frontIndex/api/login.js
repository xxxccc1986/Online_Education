import request from '@/utils/request'

export default{
    userLogin(user){
        return request({
            url: '/serviceusercenter/login',
            method: 'post',
            data: user
        })
    },
    getInfoByToken(){
        return request({
            url: '/serviceusercenter/queryInfo',
            method: 'get'
        })
    },
    updateUserInfo(user){
        return request({
            url: `/serviceusercenter/update`,
            method: 'post',
            data: user
        })
    },
    updatePhotoById(uid,path){
        return request({
            url: `/serviceusercenter/updatePhoto/${uid}/${path}`,
            method: 'post',
        })
    },
    updatePwdById(uid,oldPwd,newPwd){
        return request({
            url: `/serviceusercenter/updatepwd/${uid}/${oldPwd}/${newPwd}`,
            method: 'post',
        })

    }
   
    
}