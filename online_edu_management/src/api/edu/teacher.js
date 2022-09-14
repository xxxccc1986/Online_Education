import request from '@/utils/request'

export default {
    //查询讲师列表(条件查询)
    getTeacherList(pageNum,pageSize,voTeacher){
        return request({
            url: '/serviceedu/condition/' + pageNum +"/" + pageSize,
            //url: `/serviceedu/condition/${pageNum}/${pageSize}`,
            method: 'post',
            //后端使用了@RequestBody接收此参数，必须用data传输
            //data表示把对象转换json进行传输到接口中
            data: voTeacher
        })
    },
    //添加讲师
    addTeacher(voTeacher){
        return request({
            url: '/serviceedu/add',
            method: 'post',
            data: voTeacher
        })
    },
    //删除讲师
    delTeacher(id){
        return request({
            url: `/serviceedu/delTeacher/${id}`,
            method: 'post'
        })
    },
    //添加讲师
    addTeacher(teacher){
        return request({
            url: '/serviceedu/add',
            method: 'post',
            data: teacher,
        })
    },
    //查询指定讲师
    getTeacherInfo(id){
        return request({
            url: '/serviceedu/query/' + id,
            method: 'get',
        })
    },
    //修改指定id讲师的信息
    updateInfoById(teacher){
        return request({
            url: '/serviceedu/update/' + teacher.id,
            method: 'post',
            data: teacher,
        })
    },
    //查询所有讲师
    getAllTeachers(){
        return request({
            url: '/serviceedu/teachers',
            method: 'get',
        })
    },
}
