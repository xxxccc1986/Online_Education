<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="user">
        <!-- ,{validator: checkPhone, trigger: 'blur'} -->
        <el-form-item class="input-prepend restyle" prop="mobile" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' }]">
          <div >
            <el-input type="text" placeholder="手机号" v-model="user.mobile"/>
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>

        <el-form-item class="input-prepend" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
            <el-input type="password" placeholder="密码" v-model="user.password"/>
            <i class="iconfont icon-password"/>
          </div>
        </el-form-item>

        <div class="btn">
          <input type="button" class="sign-in-button" value="登录" @click="login">
          <input type="button" class="sign-in-button" value="返回首页" @click="toIndex">
        </div>
      </el-form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
        <h6>社交帐号登录</h6>
        <ul>
          <li>
            <a id="weixin" class="weixin" target="_blank" href="http://116.205.178.255:80/api/api/ucenter/wx/login">
              <i class="iconfont icon-weixin"/>
            </a>
          </li>
          <li><a id="qq" class="qq" @click.prevent="qqLogin" href="#"><i class="iconfont icon-qq"/></a></li>
        </ul>
      </div>
    </div>

  </div>
</template>

<script>
import '~/assets/css/sign.css'
import '~/assets/css/iconfont.css'
import login from '@/api/login'
import cookie from 'js-cookie'


export default {
  layout: 'sign',
  data () {
    return {
      user:{
        mobile:'',
        password:''
      },
      userInfo:{}
    }
  },
  methods: {
    //用户登录
    login(){
      //第一步
      if(this.user.mobile === '' || this.user.password === ''){
        this.$message.error("手机号或密码不能为空！！！")
        return false;
      }
      login.userLogin(this.user)
        .then(response => {
          if(response.data.code === 20000){ 
            //第二步，获取生成的token字符串并放入cookie中
            let token = response.data.data
            //第一个参数为cookie名称，第二个参数为cookie值，第三个参数为此cookie的作用域范围，即只在localhost下传递
            cookie.set('onlineedu_token',token,{domian:'localhost'})

            //第三步，根据token获取用户信息并放入cookie中
            login.getInfoByToken()
                .then(response => {
                  if(response.data.code === 20000){
                    this.userInfo = JSON.stringify(response.data.data)
                    cookie.set('userInfo',this.userInfo,{domian:'localhost'})
                  }
                })   
            //提示
            this.$message.success("登录成功")
              
            //跳转至首页
            this.$router.push({path:"/"})

          }    
        })
        .catch(error =>{
            //提示
            this.$message.error(error.response.data.message)
        })
    },
    qqLogin(){
        this.$message.error("该功能尚未接入，敬请期待！")
      },
    toIndex(){
      //跳转至首页
      this.$router.push({path:"/"})
    }

  }
  }
</script>
<style>
   .el-form-item__error{
    z-index: 9999999;
  }
</style>