import request from '@/utils/request'

export default{
    getChapterById(id){
      return request({
            url: '/serviceedu/chapter/list/'+id,
            method: 'get',
        })
    },  
    addChapter(eduChapter){
        return request({
            url: '/serviceedu/chapter/add',
            method: 'post',
            data: eduChapter
        })
    },
    delChapter(id){
        return request({
            url: '/serviceedu/chapter/del/'+id,
            method: 'post',

        })
    },
    updateChapter(eduChapter){
        return request({
            url: '/serviceedu/chapter/update',
            method: 'post',
            data: eduChapter
        })
    },
    queryChapter(id){
        return request({
            url: '/serviceedu/chapter/query/'+id,
            method: 'get',
        })
    },
    
}