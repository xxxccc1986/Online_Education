import request from '@/utils/request'

export default{
    getVideoAuth(videoId){
        return request({
            url: `/servicevod/videovoucher/${videoId}`,
            method:'get'
        })
    },
}