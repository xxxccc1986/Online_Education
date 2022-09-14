import request from '@/utils/request'

const api_name = '/admin/acl/permission'

export default {
  getNestedTreeList() {
    return request({
      url: `${api_name}/queryAll`,
      method: 'get'
    })
  },
  removeById(id) {
    return request({
      url: `${api_name}/delAll/${id}`,
      method: "post"
    })
  },
  saveLevelOne(menu) {
    return request({
      url: `${api_name}/save`,
      method: "post",
      data: menu
    })
  },
  update(menu) {
    return request({
      url: `${api_name}/update`,
      method: "post",
      data: menu
    })
  },
  toAssign(roleId) {
    return request({
      url: `${api_name}/toAssign/${roleId}`,
      method: 'get'
    })
  },
  doAssign(roleId, permissionId) {
    return request({
      url: `${api_name}/givePermission`,
      method: "post",
      params: {roleId, permissionId}
    })
  }
}
