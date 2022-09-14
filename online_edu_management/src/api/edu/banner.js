import request from '@/utils/request'

export default{
    queryBannerList(pageNum,PageSize){
      return request({
        url:`/servicecms/banneradmin/query/${pageNum}/${PageSize}`,
        method: 'get'
      })
    },
    addBanner(banner){
      return request({
        url:'/servicecms/banneradmin/add',
        method: 'post',
        data: banner
      })
    },
    updateBanner(banner){
      return request({
        url:'/servicecms/banneradmin/update',
        method: 'post',
        data: banner
      })
    },
    delBanner(id){
      return request({
        url:`/servicecms/banneradmin/del/${id}`,
        method: 'post'
      })
    },
    queryBanner(id){
      return request({
        url:`/servicecms/banneradmin/query/${id}`,
        method: 'get'
      })
    },
    queryBannerByTitle(title,pageNum,pageSize){
      return request({
        url:`/servicecms/banneradmin//queryByTitle/${title}/${pageNum}/${pageSize}`,
        method: 'get'
      })
    }
}