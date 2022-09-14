import router, { constantRoutes } from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login', '/auth-redirect'] // 不重定向白名单

router.beforeEach(async(to, from, next) => { //to要去的路由 from即将离开的路由 next表示方法，放行或不放行
  // start progress bar
  NProgress.start()

  // debugger
  const hasToken = getToken() //获取登录的token信息

  if (hasToken) { //已登录
    if (to.path === '/login') { //表示要去login则不允许去
      next({ path: '/' }) //中断访问，跳转到/
      NProgress.done()
    } else { //不是去登录页面
      // debugger
      const hasRoles = store.getters.roles && store.getters.roles.length > 0 //获取store.getters文件下的信息内容
      // console.log("hasRoles的结果 ---> " + hasRoles);
      if (hasRoles) { //判断是否有角色
        next()  //有角色直接放行
      } else {
        try {
          // note: roles must be a object array! such as: ['admin'] or ,['developer','editor']
          const { roles } = await store.dispatch('GetInfo') // 异步操作，拉取用户信息
          // debugger
          //异步操作，根据登录用户名字返回动态路由列表
          const accessRoutes = await store.dispatch('permission/generateRoutes', roles)
          //进行动态路由+静态路由的合并
          let allRoute = constantRoutes.concat(accessRoutes)
          //更新配置的路由规则
          router.options.routes = allRoute;

          //这一步似乎添加动态路由没有起作用，所以使用上述的步骤来更新和添加动态路由
          router.addRoutes(accessRoutes)

          next({ ...to, replace: true }) //表示放行，允许访问to 要去的路由地址
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('FedLogOut')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else { //未登录 
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) { //在放行白名单中有找到要去的路由地址
      // in the free login whitelist, go directly
      next() //放行
    } else { //没在白名单中找到
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
