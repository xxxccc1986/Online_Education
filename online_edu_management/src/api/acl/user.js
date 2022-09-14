import request from '@/utils/request'

const api_name = '/admin/acl/user'

export default {

    getPageList(page, limit, searchObj) {
        return request({
            url: `${api_name}/${page}/${limit}`,
            method: 'get',
            params: searchObj // url查询字符串或表单键值对
        })
    },
    getById(id) {
        return request({
          url: `${api_name}/get/${id}`,
          method: 'get'
        })
      },
    
      save(user) {
        return request({
          url: `${api_name}/save`,
          method: 'post',
          data: user
        })
      },
    
      updateById(user) {
        return request({
          url: `${api_name}/update`,
          method: 'post',
          data: user
        })
      },
      getAssign(userId){
        return request({
          url: `${api_name}/toAssign/${userId}`,
          method: 'get'
        })
      },
      saveAssign(userId, roleId){
        return request({
          url: `${api_name}/doAssign`,
          method: 'post',
          params:{userId, roleId}
        })
      },
      removeById(id){
        return request({
          url: `${api_name}/remove/${id}`,
          method: 'post'
        })
      },
      removeRows(idList){
        return request({
          url: `${api_name}/batchRemove`,
          method: 'post',
          data:idList
        })
      }
}
