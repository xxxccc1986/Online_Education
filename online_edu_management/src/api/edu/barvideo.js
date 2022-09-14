import request from '@/utils/request'

export default{
    addEduVideo(eduVideo){
        return request({
            url: '/serviceedu/video/add',
            method: 'post',
            data: eduVideo
        })
    },
    delEduVideo(id){
        return request({
            url: '/serviceedu/video/del/'+id,
            method: 'post',
        })
    },
    updateEduVideo(eduVideo){
        return request({
            url: '/serviceedu/video/update',
            method: 'post',
            data: eduVideo
        })
    },
    queryEduVideo(id){
        return request({
            url: '/serviceedu/video/query/'+id,
            method: 'get',
        })
    },
    delAliyunVideoById(id){
        return request({
            url: '/servicevod/videoDel/'+id,
            method: 'post',
        })
    },
    
}