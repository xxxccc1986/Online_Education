<template>
  <div class="app-container">

    <!-- 添加轮播图的表单 -->
    <el-form label-width="120px" style="width:20%">
      <el-form-item label="轮播图名称" >
        <el-input v-model="banner.title"/>
      </el-form-item>
      <el-form-item label="轮播图排序">
        <el-input-number v-model="banner.sort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="轮播图跳转地址">
        <el-input v-model="banner.linkUrl"/>
      </el-form-item>

      <el-form-item label="轮播图图片">
        <img v-if="banner.imageUrl" :src="banner.imageUrl" style="width: 300px; height: 200px"/>
        <img v-else :src="defaultPhoto" style="width: 300px; height: 200px"/>
        <el-upload
        ref="upload"
        :auto-upload="true"
        class="upload-demo"
        :action= "BASE_API +'/serviceoss/upload'"
        :on-success="cropSuccess"
        :limit="1"
        name="file"
        accept="image/jpeg,image/png">
        <el-button size="small" type="primary">点击上传</el-button>
                <!-- :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove" -->
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled"  type="primary" @click="addOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
//引入需要调用的js文件
import banner from "@/api/edu/banner";

export default {
  name:'add',
  data() {
    return {
      banner: {},
      //保存按钮是否禁用
      saveBtnDisabled: false,
      //获取dev.env.js里面的请求地址
      BASE_API: process.env.BASE_API,
      defaultPhoto: 'https://onlineedufile.oss-cn-guangzhou.aliyuncs.com/2.jpg'

    }
  },
  created() {
    this.init()
  },
  watch:{ //vue的监听功能
    $route(to,from){ //路由变化方式，当路由发生变化，init方法就会执行
      this.init()
    }
  },
  methods: {
    /*上传成功后的回调方法
    这个方法中封装了响应的赋值，此处的photo接收的就是上传成功后回传回来的访问地址
    */
    cropSuccess(response){
      //回显上传头像
      this.banner.imageUrl = response.data
    },
    init(){
      //判断url路径中是否有id值
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.queryBanner(id)
      }else {
        this.banner = {}
      }
    },
    //为了能够让修改和添加同时使用
    addOrUpdate(){
      //通过判断id值是否为空判断修改还是添加
      if(!this.banner.id){
      //添加
      this.addBanner()
      }else{
      //修改
      this.updateBanner(this.banner)
      }
    },
    //添加轮播图
    addBanner(){
      console.log(JSON.stringify(this.banner));
      banner.addBanner(this.banner)
                .then(response => {
                  //提示添加成功
                  this.$message({
                    type:'success',
                    message:'添加成功'
                  });
                  //回到数据展示的列表,通过路由跳转
                  this.$router.push({path:'/banner/list'})
                })
    },
    //根据id查询轮播图信息
    queryBanner(id){
      banner.queryBanner(id)
          .then(response => {
              this.banner = response.data
          })
    },
    //根据id修改轮播图信息
    updateBanner(){
      
      banner.updateBanner(this.banner)
              .then(response => {
                  //提示添加成功
                  this.$message({
                    type:'success',
                    message:'修改成功'
                  });
                  //刷新页面
                  this.queryBanner(this.banner.id)
          })
    }
  },
}
</script>
