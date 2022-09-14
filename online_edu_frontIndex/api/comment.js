import request from '@/utils/request'

export default{
    createComment(content,courseId,rate){
        return request({
            url:`/servicecomment/create/${courseId}/${content}/${rate}`,
            method:'get'
        })
    },
    showCommnet(pageNum,pageSize,courseId,teacherId){
        return request({
            url:`/servicecomment/show/${pageNum}/${pageSize}/${courseId}/${teacherId}`,
            method:'get'
        })
    },
    reportComment(commentId){
        return request({
            url:`/servicecomment/report/${commentId}/1`,
            method:'post'
        })
    }
}