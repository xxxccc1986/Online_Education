import request from '@/utils/request'

export default{
    addCourse(VOCourse){
      return request({
            url: '/serviceedu/course/add',
            method: 'post',
            data : VOCourse,
        })
    },
    queryCourseInfoById(id){
        return request({
            url: '/serviceedu/course/query/'+id,
            method: 'get',
        })
    },
    updateCourseInfoById(voCourse){
        return request({
            url: '/serviceedu/course/update',
            method: 'post',
            data: voCourse
        })
    },
    queryVOPublishInfoById(id){
        return request({
            url: '/serviceedu/course/'+id,
            method: 'get',
        })
    },
    updateCourseStatusById(id){
        return request({
            url: '/serviceedu/course/status/' + id,
            method: 'post',
        })
    },
    delCourseById(id){
        return request({
            url: `/serviceedu/course/del/${id}`,
            method: 'post',
        })
    },
    conditionQueryCourse(pageNum,pageSize,queryCourse){
        return request({
            url: `/serviceedu/course/condition/${pageNum}/${pageSize}`,
            method: 'post',
            data:queryCourse
        })
    },
    
}