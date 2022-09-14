<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="3" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>
    
  <el-row >
      <el-col :span="16" :offset="4">
      <el-button type="text" size="big" style="padding-bottom:30px">课程信息确认</el-button>
       <div class="ccInfo" style="padding-top:10px" >
         <!-- 课程封面 TODO -->
        <img :src="VOPublish.cover">
        <div class="main">
          <h2>课程名称：{{ VOPublish.title }}</h2>
          <p class="gray"><span>总共：{{ VOPublish.lessonNum }}课时</span></p>
          <p><span>所属分类：{{ VOPublish.oneSubject }} — {{ VOPublish.twoSubject }}</span></p>
          <p>课程讲师：{{ VOPublish.teacherName }}</p>
          <h3 class="red">总价为￥{{ VOPublish.price }}元</h3>
        </div>
      </div>
        </el-col>
  </el-row>
    

    <el-form label-width="40%">
      
      <el-form-item>
        <el-button @click="previous">返回修改</el-button>
        <el-button :disabled="false" type="primary" @click="next">确认并发布</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
import course from '@/api/edu/course'

export default {
    name:'info',
    data() {
        return {
            courseId: this.$route.params.id,
            VOPublish:{
              id:'',
              cover:'http://m.imeitou.com/uploads/allimg/2016060815/g1qemno1c4t.jpg?imageView2/1/w/80/h/80',
              title:'',
              lessonNum:'',
              oneSubject:'',
              twoSubject:'',
              teacherName:'',
              price:''
            },

        }
    },
    created() {
        this.queryVOPublishInfoById()
    },
    methods: {
        //上一步修改
        previous(){
           this.$router.push({path:'/course/chapter/'+ this.courseId})
        },
        //最终提交，修改状态
        next(){
          this.updateCourseStatusById()
        },
        //根据课程id查询用于显示确认的课程信息
        queryVOPublishInfoById(){
          course.queryVOPublishInfoById(this.courseId)
              .then(response => {
                this.VOPublish = response.data
              })
        },
        //根据课程id修改课程最终状态
        updateCourseStatusById(){
          course.updateCourseStatusById(this.courseId)
            .then(response => {
              this.$message.success("发布成功！")
                this.$router.push({path:"/course/list"})
            })
        },

    },
}
</script>

<style scope>
.el-row {
margin-bottom: 5px;
}
.el-col {
border-radius: 6px;
}
.ccInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 40px;
    position: relative;
}
.ccInfo img {
    background: #ffffff;
    width: 450px;
    height: 295px;
    display: block;
    float: left;
    border: 5px solid gray;
}
.ccInfo .main {
    margin-left: 480px;
}

.ccInfo .main h2 {
    font-size: 28px;
    margin-bottom: 30px;
    line-height: 1;
    font-weight: normal;
}
.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}

.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}
.ccInfo .main h3 {
    left: 500px;
    bottom: 40px;
    line-height: 1;
    font-size: 28px;
    color: #d32f24;
    font-weight: normal;
    position: absolute;
}
</style>

</style>