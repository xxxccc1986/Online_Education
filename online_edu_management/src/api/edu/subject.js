import request from '@/utils/request'

export default {
    getSubjectList(){
        return request({
            url: '/serviceedu/subject/list',
            method: 'get',
        })
    }
}