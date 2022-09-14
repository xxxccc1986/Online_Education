<template>
  <div id="aCoursesList" class="bg-fa of">

    <el-row :gutter="0" >
        <el-col :span="3" :offset="3">
            <el-menu :default-active="activeVal" @select="chooseActiveTab" style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)" class="el-menu-vertical-demo" >
                <el-submenu index="1">
                    <template slot="title">
                    <i class="el-icon-shopping-cart-full"></i>
                    <span slot="title">我的订单</span>
                    </template>
                    <el-menu-item-group>
                    <el-menu-item index="1-1">已支付订单</el-menu-item>
                    <el-menu-item index="1-2">未支付订单</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
                <el-submenu index="2">
                    <template slot="title">
                    <i class="el-icon-postcard"></i>
                    <span slot="title">个人中心</span>
                    </template>
                    <el-menu-item-group>
                    <el-menu-item index="2-1">个人资料</el-menu-item>
                    <el-menu-item index="2-2">上传头像</el-menu-item>
                    <el-menu-item index="2-3">修改密码</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
            </el-menu>
        </el-col>

        <el-col :span="10" :offset="1">
            <div class="grid-content bg-purple">
                <!-- 通过双向绑定v-model="activeName" activeName的值与哪个pane的name属性值就选择哪个 -->
                <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card" class="border-card">
                    <div style="margin: 10px;"></div>
                    <el-tab-pane name="success" label="已支付订单">
                        <div v-if="successList.length === 0"
                        style=" height: 40px;
                                background-color: #FFFFFF;
                                display: flex;
                                flex-direction: row;
                                justify-content: center;">暂无任何订单</div>
                        <div v-for="order in successList" :key="order.id" >
                            <el-descriptions 
                            class="margin-top" 
                            title="订单信息" 
                            :column="3" 
                            size="size" border>
                                <template slot="extra">
                                <el-button type="danger" size="mini" @click="handleDelete(order.id)">删除</el-button>
                                </template>
                                <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-user"></i>
                                    用户名
                                </template>
                                {{order.nickname}}
                                </el-descriptions-item>                           
                                <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-money"></i>
                                    总金额
                                </template>
                                {{order.totalFee}}
                                </el-descriptions-item>
                                <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-notebook-2"></i>
                                    课程名
                                </template>
                                {{order.courseTitle}}
                                </el-descriptions-item>
                                <el-descriptions-item style="width:10%">
                                <template slot="label">
                                    <i class="el-icon-user-solid"></i>
                                    讲师名
                                </template>
                                {{order.teacherName}}
                                </el-descriptions-item>
                                <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-timer"></i>
                                    创建时间
                                </template>
                                {{order.gmtCreate}}
                                </el-descriptions-item>
                                 <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-timer"></i>
                                    支付时间
                                </template>
                                {{order.gmtModified}}
                                </el-descriptions-item>
                            </el-descriptions>                           
                            <br>     
                        </div>

                        <!-- 分页条 -->
                            <!-- @current-change是饿了么已经实现的，绑定了一个点击事件
                            当currentPage改变时会触发，传的参数是当前页数，即:current-page绑定的值-->
                            <el-pagination
                            :current-page="successPageNum"
                            :page-size="successPageSize"
                            :total="successTotal"
                            style="padding: 30px 0; text-align: center"
                            layout="total,prev, pager, next,jumper"
                            @current-change="getSuccessOrder"
                            />
                    </el-tab-pane>
                    <el-tab-pane name="notpay" label="未支付订单">
                        <div v-if="errorList.length === 0" 
                        style=" height: 40px;
                                background-color: #FFFFFF;
                                display: flex;
                                flex-direction: row;
                                justify-content: center;">暂无任何订单</div>
                        <div v-for="order in errorList" :key="order.id" >
                            <el-descriptions class="margin-top" title="订单信息" :column="3" :size="size" border>
                                <template slot="extra" >
                                <el-button type="primary" size="mini" @click="goToPay(order.orderNo)">去支付</el-button>
                                <el-button type="danger" size="mini" @click="handleDelete(order.id)">删除</el-button>
                                </template>
                                <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-user"></i>
                                    用户名
                                </template>
                                {{order.nickname}}
                                </el-descriptions-item>                           
                                <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-money"></i>
                                    总金额
                                </template>
                               {{order.totalFee}}
                                </el-descriptions-item>
                                <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-notebook-2"></i>
                                    课程名
                                </template>
                                {{order.courseTitle}}
                                </el-descriptions-item>
                                <el-descriptions-item style="width:10%">
                                <template slot="label">
                                    <i class="el-icon-user-solid"></i>
                                    讲师名
                                </template>
                                {{order.teacherName}}
                                </el-descriptions-item>
                                <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-timer"></i>
                                    创建时间
                                </template>
                                {{order.gmtCreate}}
                                </el-descriptions-item>
                                 <el-descriptions-item>
                                <template slot="label">
                                    <i class="el-icon-timer"></i>
                                    支付时间
                                </template>
                                {{order.gmtModified}}
                                </el-descriptions-item>
                            </el-descriptions>
                        </div>
                            <!-- 分页条 -->
                            <!-- @current-change是饿了么已经实现的，绑定了一个点击事件
                            当currentPage改变时会触发，传的参数是当前页数，即:current-page绑定的值-->
                            <el-pagination
                            :current-page="errorPageNum"
                            :page-size="errorPageSize"
                            :total="errorTotal"
                            style="padding: 30px 0; text-align: center"
                            layout="total,prev, pager, next,jumper"
                            @current-change="getNotPayOrder"
                            />
                    </el-tab-pane>

                    <el-tab-pane name="person" label="个人资料" >
                        <el-form :model="userInfo" style="width: 70%" :label-position="labelPosition" label-width="80px" >
                            <el-form-item label="用户名：">
                                <el-input v-model="userInfo.nickname" disabled></el-input>
                            </el-form-item>
                            <el-form-item label="手机号：">
                                <el-input v-model="userInfo.mobile"></el-input>
                            </el-form-item>
                            <el-form-item  label="性别：">
                                <el-radio-group v-model="userInfo.sex" >
                                    <el-radio  :label='1'>男</el-radio>
                                    <el-radio  :label='2'>女</el-radio>
                                </el-radio-group>
                            </el-form-item>
                            <el-form-item label="年龄：">
                                <el-input-number 
                                    v-model="userInfo.age" 
                                    :min="1">
                                </el-input-number>
                            </el-form-item>
                            <div style="float:right">
                                <el-button size="mini" type="primary" @click="getUserInfo">重置</el-button>
                                <el-button size="mini" type="success" @click="updateUserInfo">修改</el-button>
                            </div>               
                        </el-form>
                    </el-tab-pane>
                    <el-tab-pane name="avatar" label="上传头像">
                          <span style="padding:0 20px">当前头像：</span>
                          <div style="padding-left: 15%">
                              <el-image 
                                style="width: 200px; height: 200px;border-radius:50%; overflow:hidden;"
                                :src="this.userInfo.avatar? this.userInfo.avatar : defaultImg" >
                               </el-image>
                                <br>
                               <el-upload
                                    class="upload-demo"
                                    ref="upload"
                                    :action="'http://116.205.178.255:80/api/serviceoss/upload'"
                                    :file-list="fileList"
                                    :limit=1
                                    :on-success="successUpload"
                                    :auto-upload="false">
                                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                                    <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">修改头像</el-button>
                                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                                </el-upload>
                          </div>   
                    </el-tab-pane>
                    <el-tab-pane name="pwd" label="修改密码">
                        <div style="width:60%">
                            <el-form :model="pwdForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="原密码" prop="oldPwd">
                                    <el-input type="password" v-model="pwdForm.oldPwd" autocomplete="off"></el-input>
                                </el-form-item>
                                <el-form-item label="新密码" prop="newPwd">
                                    <el-input type="password" v-model="pwdForm.newPwd" autocomplete="off"></el-input>
                                </el-form-item>
                                <el-form-item label="确认密码" prop="checkPass">
                                    <el-input type="password" v-model="pwdForm.checkPass"></el-input>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" @click="updatePwd" size="mini">修改</el-button>
                                </el-form-item>
                            </el-form>
                        </div>  
                    </el-tab-pane>
                </el-tabs>
            </div>
        </el-col>
    </el-row>   
  </div>
</template>

<script>
import login from '@/api/login'
import cookie from 'js-cookie'
import order from '@/api/order'

export default {
    name:'usercenter',
    data() {
        var checkPass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } 
            else if (value !== this.pwdForm.newPwd) {
                callback(new Error('两次输入密码不一致!'));
            } 
            else {
                callback();
            }
        };
        return {
            labelPosition: 'right',
            userInfo: {
                id:'',
                age: '',
                avatar:'',
                mobile:'',
                nickname:'',
                sex: ''
                },
            activeVal:'2-1',
            activeName:'person',
            defaultImg: 'https://onlineedufile.oss-cn-guangzhou.aliyuncs.com/2022-08-23/21f4a13d-9161-4a65-b316-d00302f1eba2.jpg',
            fileList:[],
            pwdForm:{},
            rules:{
                oldPwd: [
                    { required: true, message: '请输入原密码', trigger: 'blur' },
                        ],
                newPwd: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                        ],
                checkPass: [
                    { required: true, message: '请再次输入新密码', trigger: 'blur' },
                    { validator: checkPass, trigger: 'blur' }
                           ]
            },
            size:'',
            successPageNum: 1, //当前页
            successPageSize: 2, //每页数量
            successList: [], //查询接口后返回的数据
            successTotal: 0, //总记录数

            errorPageNum: 1, //当前页
            errorPageSize: 1, //每页数量
            errorList: [], //查询接口后返回的数据
            errorTotal: 0, //总记录数
        }
    },
    created() {
        //获取用户信息
        this.getUserInfo()
    },
    methods:{
        //查询用户已支付订单
        getSuccessOrder(page = 1){
            this.successPageNum = page
            order.getOrders(this.userInfo.id,this.successPageNum,this.successPageSize,1)
                .then(response => {
                //回填信息
                this.successList = response.data.data.records
                this.successPageNum = response.data.data.current
                this.successPageSize = response.data.data.size
                this.successTotal = response.data.data.total
                })
        },
        //查询用户未支付订单
        getNotPayOrder(page = 1){
            this.errorPageNum = page
            order.getOrders(this.userInfo.id,this.errorPageNum,this.errorPageSize,0)
                .then(response => {
                //回填信息
                this.errorList = response.data.data.records
                this.errorPageNum = response.data.data.current
                this.errorPageSize = response.data.data.size
                this.errorTotal = response.data.data.total
                })
        },
        //支付订单
        goToPay(orderNo){
            //跳转到订单支付界面
            this.$router.push({path:'/order/'+ orderNo})   
        },
        //删除订单
        handleDelete(id){
            this.$confirm(`此操作将删除 ${id} 的订单信息, 是否继续?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                //调用删除方法
                order.delOrder(id)
                    .then(response => {
                    //删除成功弹窗
                    this.$message({
                    type: "success",
                    message: "删除成功!",
                    })
                    //刷新页面
                    if(this.activeVal === '1-1'){
                        this.getSuccessOrder()
                    }else {
                        this.getNotPayOrder();
                    }
                    
                    })
                
                }) 
        },
        //根据不同的index值变化选择的标签页，index是select钩子的回调属性
        chooseActiveTab(index){
            if(index === '1-1'){
                this.activeName = 'success'
            }
            if(index === '1-2'){
                this.activeName = 'notpay'
            }
            if(index === '2-1'){
                this.activeName = 'person'
            }
            if(index === '2-2'){
                this.activeName = 'avatar'
            }
            if(index === '2-3'){
                this.activeName = 'pwd'
            }   
        },
        //tab被选中时触发,动态选择导航被选中的，tab是被选中的标签 tab 实例
        handleClick(tab){
            if(tab.name === 'success'){
                this.activeVal = '1-1'
            }
            if(tab.name === 'notpay'){
                this.activeVal = '1-2'
            }
            if(tab.name === 'person'){
                this.activeVal = '2-1'
            }
            if(tab.name === 'avatar'){
                this.activeVal = '2-2'
            }
            if(tab.name === 'pwd'){
                this.activeVal = '2-3'
            }
        },
        //查询用户信息
        getUserInfo(){
            login.getInfoByToken()
                .then(response => {
                    this.userInfo = response.data.data
                    //获取用户支付成功订单
                    this.getSuccessOrder()
                    //获取用户未支付的订单
                    this.getNotPayOrder()
                })
         },
         //更新用户信息
         updateUserInfo(){
            login.updateUserInfo(this.userInfo)
                .then(response => {
                    //给予提示
                    this.$message.success("修改成功！")
                })
         },
         //上传头像
         submitUpload(){
           this.$refs.upload.submit();
         },
         //文件上传成功后的钩子函数
         successUpload(response){
            if(response.code === 20000){
                //给予提示
                this.$message.success("修改头像成功！")
                //获取头像访问地址
                let path = response.data
                //修改用户头像
                this.userInfo.avatar = path
                
                //请求参数有斜杠或反斜杠接口访问不到，因此需要前端将其base64编码后传递
                let dbPath = window.btoa(path)
                //更新用户图像数据库地址
                login.updatePhotoById(this.userInfo.id,dbPath)

                //删除图片列表
                this.$refs.upload.clearFiles()

                //刷新页面
                location.reload()
            }
         },
         //更新密码
         updatePwd(){
            if(this.pwdForm){
                this.$message.error("先填写需要填写的信息！")
                return false;
            }
            login.updatePwdById(this.userInfo.id,this.pwdForm.oldPwd,this.pwdForm.newPwd)
                .then(resposne => {
                    //清空token
                    cookie.set("onlineedu_token",'',{domain:'localhost'})
                    this.$message.success("密码更新成功！请重新登录")

                    //重定向到登录界面
                    this.$router.push({path:'/login'})
                })
                .catch(error => {
                    this.$message.error(error.response.data.message)
                })
         }
    }
}
</script>

<style>
  .el-row {
    margin-bottom: 20px;
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 100px;
  }
  .border-card{
    width: 120%;
    min-height: 390px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

</style>

