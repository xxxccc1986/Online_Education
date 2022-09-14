import request from '@/utils/request'

export default{
    getAllComment(pageNum,pageSize,VOComment){
        return request({
            url: `/servicecomment/getAllComment/${pageNum}/${pageSize}`,
            method:'post',
            data:VOComment
        })
    },
    delComment(commentIds){
        return request({
            url: `/servicecomment/del/${commentIds}`,
            method:'post'
        })
    },
    reportComment(commentId){
        return request({
            url:`/servicecomment/report/${commentId}/0`,
            method:'post'
        })
    },
    showTeacher(){
        return request({
            url:`/servicecomment/showTeacher`,
            method:'get'
        })
    },
    showCourse(){
        return request({
            url:`/servicecomment/showCourse`,
            method:'get'
        })
    }
}