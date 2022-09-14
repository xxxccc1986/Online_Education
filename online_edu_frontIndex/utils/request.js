import axios from 'axios'
import cookie from 'js-cookie'
// 创建axios实例
const service = axios.create({
  // baseURL: 'http://192.168.231.134:9001/api/', // api的base_url
  // baseURL: 'http://localhost:8010', // api的base_url
  baseURL: 'http://116.205.178.255:80/api/',
  timeout: 20000, // 请求超时时间

})

// http request 拦截器
service.interceptors.request.use(
  config => {
  //判断cookie中是否存储名为onlineedu_token
  if (cookie.get('onlineedu_token')) {
    //把获取的cookie值放到header中
    config.headers['token'] = cookie.get('onlineedu_token');
  }
    return config
  },
  err => {
  return Promise.reject(err);
})
// http response 拦截器
// service.interceptors.response.use(
//   response => {
//     //debugger
//     if (response.data.code == 28004) {
//         console.log("response.data.resultCode是28004")
//         // 返回 错误代码-1 清除ticket信息并跳转到登录页面
//         //debugger
//         window.location.href="/login"
//         return
//     }else{
//       if (response.data.code !== 20000) {
//         //25000：订单支付中，不做任何提示
//         if(response.data.code != 25000) {
//           Message({
//             message: response.data.message || 'error',
//             type: 'error',
//             duration: 5 * 1000
//           })
//         }
//       } else {
//         return response;
//       }
//     }
//   },
//   error => {
//     return Promise.reject(error.response)   // 返回接口返回的错误信息
// });

export default service