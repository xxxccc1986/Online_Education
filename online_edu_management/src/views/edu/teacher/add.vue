<template>
  <div class="app-container">

    <!-- 添加讲师的表单 -->
    <el-form label-width="120px" style="width:40%">
      <el-form-item label="讲师名称" >
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="6" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <el-form-item label="讲师头像">

      <!-- 头衔缩略图 -->
      <pan-thumb :image="String(teacher.avatar)"/>
      <!-- 文件上传按钮 -->
      <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
      </el-button>

      <!--
      v-show：是否显示上传组件
      :key：类似于id，如果一个页面多个图片上传控件，可以做区分
      :url：后台上传的url地址
      @close：关闭上传组件
      @crop-upload-success：上传成功后的回调 -->
      <image-cropper
                    v-show="imagecropperShow"
                    :width="300"
                    :height="300"
                    :key="imagecropperKey"
                    :url="BASE_API+'/serviceoss/upload'"
                    field="file"
                    @close="close"
                    @crop-upload-success="cropSuccess"/>

      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="addOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
//引入需要调用的js文件
import teacherApi from "@/api/edu/teacher.js";
import ImageCropper from "@/components/ImageCropper"
import PanThumb from "@/components/PanThumb"

export default {
  name:'add',
  components:{
    ImageCropper,
    PanThumb
  },
  data() {
    return {
      teacher: {},
      //保存按钮是否禁用
      saveBtnDisabled: false,
      //上传弹框是否显示
      imagecropperShow: false,
      //图片唯一标识
      imagecropperKey: 0,
      //获取dev.env.js里面的请求地址
      BASE_API: process.env.BASE_API,

    }
  },
  created() {
    this.init()
    //显示一个默认头像
    this.teacher.avatar = 'https://onlineedufile.oss-cn-guangzhou.aliyuncs.com/2022-08-21/7249bfa8-0a35-4d25-afed-2c5303acf5bd.png'
  },
  watch:{ //vue的监听功能
    $route(to,from){ //路由变化方式，当路由发生变化，init方法就会执行
      this.init()
    }
  },
  methods: {
    //关闭上传弹框的方法
    close(){
      this.imagecropperShow = false;
      //上传组件初始化，解决二次上传的缓存bug
      this.imagecropperKey += 1
    },
    /*上传成功后的回调方法
    这个方法中封装了响应的赋值，此处的photo接收的就是上传成功后回传回来的访问地址
    */
    cropSuccess(photo){
      //回显上传头像
      this.teacher.avatar = photo
      //关闭上传弹窗
      this.imagecropperShow = false
      //上传组件初始化，解决二次上传的缓存bug
      this.imagecropperKey += 1
    },
    init(){
      console.log('created')
      //判断url路径中是否有id值
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.getTeacherInfo(id)
      }else {
        this.teacher = {}
      }
    },
    //为了能够让修改和添加同时使用
    addOrUpdate(){
      //通过判断id值是否为空判断修改还是添加
      if(!this.teacher.id){
      //添加
      this.addTeacher();
      }else{
      //修改
      this.updateInfoById(this.teacher)
      }
    },
    //添加讲师
    addTeacher(){
      teacherApi.addTeacher(this.teacher)
                .then(response => {
                  //提示添加成功
                  this.$message({
                    type:'success',
                    message:'添加成功'
                  });
                  //回到数据展示的列表,通过路由跳转
                  this.$router.push({path:'/teacher/list'})
                })
    },
    //根据id查询讲师信息
    getTeacherInfo(id){
      teacherApi.getTeacherInfo(id)
          .then(response => {
              this.teacher = response.data
          })
    },
    //根据id修改讲师信息
    updateInfoById(teacher){
      teacherApi.updateInfoById(teacher)
              .then(response => {
              //提示添加成功
                  this.$message({
                    type:'success',
                    message:'修改成功'
                  });
                  //回到数据展示的列表,通过路由跳转
                  this.$router.push({path:'/teacher/list'})
          })
    }
  },
}
</script>
