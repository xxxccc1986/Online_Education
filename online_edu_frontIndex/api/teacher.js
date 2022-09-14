import request from '@/utils/request'

export default{
    getSortTeacher(){
        return request({
            url: '/serviceedu/front/queryteaher',
            method:'get'
        })
    },
    getFrontTeacher(pageNum,pageSize){
        return request({
            url: `/serviceedu/frontTeacher/query/${pageNum}/${pageSize}`,
            method:'get'
        })
    },
    getTeacherByid(teacherId){
        return request({
            url: `/serviceedu/frontTeacher/query/${teacherId}`,
            method:'get'
        })
    }
}