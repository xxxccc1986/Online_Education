import request from '@/utils/request'

export default{
    getHotCourse(){
        return request({
            url: '/serviceedu/front/querycourse',
            method:'get'
        })
    },
    getHtmlCourse(pageNum,pageSize,VOFrontCourse){
        return request({
            url: `/serviceedu/frontCourse/courseList/${pageNum}/${pageSize}`,
            method:'post',
            data: VOFrontCourse
        })
    },
    getOneSubject(){
        return request({
            url: '/serviceedu/frontCourse/querySubject',
            method:'get'
        })
    },
    getTwoSubject(subjectId){
        return request({
            url: '/serviceedu/frontCourse/querySubject?subjectParentId=' + subjectId,
            method:'get'
        })
    },
    getCourseDetailsById(courseId){
        return request({
            url: `/serviceedu/frontCourse/details/${courseId}`,
            method:'get'
        })
    },
    judgeCourseIsBuyOrNot(courseId){
        return request({
            url: `/serviceorder/order/judgeIsBuyOrNot/${courseId}`,
            method:'get'
        })
    }
}