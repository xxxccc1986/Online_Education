<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="#" title="在线教育">
            <img src="~/assets/img/logo.png" width="100%" alt="在线教育">
          </a>
        </h1>
        <div class="h-r-nsl">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/course" tag="li" active-class="current">
              <a>课程</a>
            </router-link>
            <router-link to="/teacher" tag="li" active-class="current">
              <a>名师</a>
            </router-link>
            <!-- <router-link to="/usercenter" tag="li" active-class="current">
              <a>xxx</a>
            </router-link>
            <router-link to="/qa" tag="li" active-class="current">
              <a>xxx</a>
            </router-link> -->
          </ul>
          <!-- / nav -->
            <ul class="h-r-login"> 
                <!-- 取反为true代表此时id中有值，因此不显示 -->
                <li v-if="!userInfo.id" id="no-login">
                    <a href="/login" title="登录">
                        <em class="icon18 login-icon">&nbsp;</em>
                        <span class="vam ml5">登录</span>
                    </a>
                    |
                    <a href="/register" title="注册">
                        <span class="vam ml5">注册</span>
                    </a>
                </li>
                <!-- 为true代表此时id中有值，因此显示 -->
                <li v-if="userInfo.id" id="is-login-two" class="h-r-user">
                        <a href="javascript:void(0);">
                        <img
                            :src="userInfo.avatar"
                            width="40"
                            height="40"
                            class="vam picImg"
                            alt>
                           <el-dropdown>
                             <span class="el-dropdown-link" style="padding-top: 10px">
                              {{ userInfo.nickname }}<i class="el-icon-arrow-down el-icon--right"></i>
                            </span>
                              <el-dropdown-menu slot="dropdown">
                                <router-link to="/usercenter" tag="li">
                                  <el-dropdown-item > 个人中心 </el-dropdown-item>
                                </router-link>
                                <a href="javascript:void(0);" title="退出登录" @click="logout()">
                                  <el-dropdown-item> 退出登录 </el-dropdown-item>
                                </a>                          
                              </el-dropdown-menu>
                            </el-dropdown>
                    </a>
                    <!-- <a href="javascript:void(0);" title="退出" @click="logout()" class="ml5">退出</a> -->
                </li>
                <!-- /未登录显示第1 li；登录后显示第2，3 li -->
            </ul>
          <aside class="h-r-search"  >
              <label class="h-r-s-box">
                <el-input  v-model="title"  placeholder="请输入课程名" circle> </el-input>
                <el-button type="text" @click="quertyCourse" class="s-btn" style="padding-left: 5px;padding-top: 5px">
                  <em class="icon18">&nbsp;</em>
                </el-button> 
              </label>
          </aside>      
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
    <!-- /公共头引入 -->
      
    <nuxt/>

    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh30">
            <span class="fsize18 f-fM c-999">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="http://year21.top/" title="个人博客" target="_blank">个人博客</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a href="#" title="联系我们" target="_blank">联系我们</a>|
                <a href="#" title="帮助中心" target="_blank">帮助中心</a>|
                <a href="#" title="资源下载" target="_blank">资源下载</a>|
                <span>服务热线：010-xxxxxx(北京) 0755-xxxxx(深圳)</span>
                <span>Email：masicjokersic@gmail.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©2022 All Servers By Year21 </span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wx-icon.png" alt>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wb-icon.png" alt>
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
import "~/assets/css/reset.css";
import "~/assets/css/theme.css";
import "~/assets/css/global.css";
import "~/assets/css/web.css";
import '~/assets/css/base.css'
import '~/assets/css/activity_tab.css'
import '~/assets/css/bottom_rec.css'
import '~/assets/css/nice_select.css'
import '~/assets/css/order.css'
import '~/assets/css/swiper-3.3.1.min.css'
import "~/assets/css/pages-weixinpay.css"

import cookie from 'js-cookie'
import login from '@/api/login'

export default {
    name:'index',
    data() {
      return {
        token:'',
        userInfo:{
          id:'',
          age: '',
          avatar:'',
          mobile:'',
          nickname:'',
          sex:'' 
        },
        sessionNickname: '',
        sessionAvatar:'',
        title:''
      }
    },
    created() {
      //获取路径中的token值
      this.token = this.$route.query.token
      if(this.token){ //有值表示是从微信登录
        this.setTokenInCookie()
      }else{ //没有表示账户登录
        this.getUserInfo()
      }
    },
    methods: {
      //从cookie中获取信息
      getUserInfo(){
        if(cookie.get('onlineedu_token')){ //当token为不为空代表登录
          login.getInfoByToken()
              .then(response => {
                  this.userInfo = response.data.data                 
              })
        }
        
        //在cookie中只能存储字符串，因此必须把取出的字符串转化为json对象
        // let user = cookie.get('userInfo')
        // if(user){
        //   this.userInfo = JSON.parse(user)
        // }
      },
      //退出登录
      logout(){
        //清空cookie
        cookie.set("userInfo",'',{domain:'localhost'})
        cookie.set('onlineedu_token','',{domian:'localhost'})

        this.$message.success("退出成功")

        window.location.href = '/'
      },
      //将token值放入cookie中
      setTokenInCookie(){
        cookie.set("onlineedu_token",this.token,{domain:'localhost'})
        cookie.set("userInfo",'',{domain:'localhost'})
        //根据token查询用户信息
        login.getInfoByToken()
          .then(response => {
            if(response.data.code === 20000){
              console.log("进入setTokenInCookie");
              //获取数据后，直接填充至页面
              this.userInfo = response.data.data

              // this.userInfo = JSON.stringify(response.data.data)
              // cookie.set('userInfo',this.userInfo,{domian:'localhost'})
              // //由于在created方法中函数的加载顺序不一定按顺序，为了防止缓存问题，在这里进行一次页面判断
              // this.getUserInfo()
            }
        })   
      },
      //查询课程
      quertyCourse(){
        if(!this.title){
          this.$message.error("请先输入需要搜索的课程名字！")
          return false
        }
        this.$router.push({path:'/course',
                          query:{
                              title:this.title,
                                }
                          })

      }
    },

};
</script>

<style>
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>