import request from '@/utils/request'


export function login(username, password) {
 // 登录
  return request({
    url: '/admin/acl/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

// 获取用户信息
export function getInfo(token) {
  return request({
    url: '/admin/acl/index/info',
    method: 'get',
    params: { token }
  })
}

// 退出
export function logout() {
  return request({
    url: '/admin/acl/index/logout',
    method: 'post'
  })
}

// 获取菜单
export function getMenu() {
  // debugger
  return request({
    url: '/admin/acl/index/menu',
    method: 'get'
  })
}

//获取ip信息
export function getIpInfo() {
  // debugger
  return request({
    url: '/admin/acl/index/getIpInfo',
    method: 'get'
  })
}
