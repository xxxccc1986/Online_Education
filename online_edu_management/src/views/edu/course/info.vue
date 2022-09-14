<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="30%" >
      <el-form-item label="课程标题">
        <el-input v-model="voCourse.title" style="width: 50%"  placeholder=" 示例：如何高效学习？"/>
      </el-form-item>

      <!-- 所属分类 TODO -->
      <el-form-item label="课程分类">
        <el-select v-model="voCourse.subjectParentId" placeholder="一级分类" @change="selectTwoSubject">
          <el-option
            v-for="oSubject in oneSubjects"
            :key="oSubject.id"
            :label="oSubject.title"
            :value="oSubject.id">
          </el-option>
        </el-select>
        <el-select v-model="voCourse.subjectId" placeholder="二级分类">
          <el-option
            v-for="tSubject in towSubjects"
            :key="tSubject.id"
            :label="tSubject.title"
            :value="tSubject.id">
          </el-option>
        </el-select>
      </el-form-item>
      

      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select v-model="voCourse.teacherId" placeholder="请选择">
          <el-option
            v-for="teacher in teachers"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="voCourse.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>

      <!-- 课程简介-->
      <el-form-item label="课程简介">
         <tinymce :height="300" style="width: 70%" v-model="voCourse.description"/>
        <!-- <el-input v-model="voCourse.description" style="width: 60%"  placeholder=" 示例：高效学习是指花费最合理的时间学习最多的知识"/> -->
      </el-form-item>

      <!-- 课程封面 TODO -->
       <el-form-item label="课程封面">
            <el-upload
              class="avatar-uploader"
              :action="BASE_API+'/serviceoss/upload'"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="this.voCourse.cover" :src="this.voCourse.cover" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="voCourse.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
      </el-form-item>

      <el-form-item>
        <el-button :disabled="false" type="primary" @click="addOrUpdate" >保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
import course from '@/api/edu/course'
import teacher from '@/api/edu/teacher'
import subject from '@/api/edu/subject'
import Tinymce from '@/components/Tinymce'

export default {
    name:'info',
    components:{
        Tinymce
    },
    data() {
        return {
            voCourse: {id:'',subjectId: '',cover:''},
            courseId: '',
            teachers: [],
            oneSubjects: [],
            towSubjects: [],
            BASE_API: process.env.BASE_API,
            
        }
    },
    created() {
        //初始化讲师列表
        this.getAllTeachers()
        //初始化一级分类课程列表
        this.getAllSubjects()
        
    },
    methods: {
      //根据voCourse的id来判断是添加还是更新操作
      addOrUpdate(){
        if(this.voCourse.id === ''){//为空代表是添加操作
          //阻止空页面和内容添加
          console.log(this.voCourse);
          if(!this.voCourse.subjectId === ''){
            this.$message.error("请先添加数据再执行下一步！！！")
            return false
          }
          this.addCourse()
        }else { //不为空代表是更新操作
          this.updateCourseInfoById()
        }
      },
      //添加课程
      addCourse(){
        course.addCourse(this.voCourse)
          .then(response => {
            this.$message({
              type:'success',
              message: '添加成功！'
            })
            this.courseId = response.data
            this.$router.push({path:`/course/chapter/${this.courseId}`})
          })
        },
        //根据id查询课程信息
        queryCourseInfoById(){
          course.queryCourseInfoById(this.courseId)
              .then(response => {
                  this.voCourse = response.data
                  //根据课程一级分类查询二级分类列表并填充至二级分类数组
                  let tempId = this.voCourse.subjectParentId
                  console.log(this.oneSubjects.length);
                  for(let i=0;i<this.oneSubjects.length;i++){
                      let tempSubjectParent = this.oneSubjects[i]
                      //如果当前voCourse对象的tempId和SubjectParentId相同代表是同一个一级分类
                      if(tempSubjectParent.id === tempId){
                        //将当前一级分类的二级分类赋值给towSubjects
                        this.towSubjects = tempSubjectParent.children 
                        console.log(JSON.stringify(this.towSubjects));
                      }
                  }
                  
              })
        },
        //更新信息
        updateCourseInfoById(){
          this.voCourse.id = this.courseId
          course.updateCourseInfoById(this.voCourse)
              .then(response => {
                this.$message.success("修改成功")
                this.$router.push({path:`/course/chapter/${this.courseId}`})
              })
        },
        //获取所有讲师名单
        getAllTeachers(){
          teacher.getAllTeachers()
              .then(response => {
                this.teachers = response.data
              })
        },
        //获取所有课程名单
        getAllSubjects(){
          subject.getSubjectList()
                .then(resopnse => {
                  this.oneSubjects = resopnse.data
                  //必须保证this.oneSubjects不为空的情况才能进行课程信息的回显
                  if(this.$route.params && this.$route.params.id ){
                    //获取url中的参数
                    this.courseId = this.$route.params.id
                    //根据id查询信息
                    this.queryCourseInfoById()
                  }
                })
        },
        //二级联动，根据一级分类的选择动态显示二级分类内容
        selectTwoSubject(){
            //每次一级分类改变就清空二级分类页面显示内容
            this.voCourse.subjectId = ''
            for(let i = 0;i< this.oneSubjects.length;i++){
                  let tempCourse = this.oneSubjects[i]
                  if(tempCourse.id === this.voCourse.subjectParentId){
                      this.towSubjects = tempCourse.children;
                  }
            }
        },
        //上传成功后的钩子函数
        handleAvatarSuccess(resopnse){
            this.voCourse.cover = resopnse.data
            this.$message({
              type:'success',
              message:'添加成功'
            })
        },
        //上传之前的钩子函数
        beforeAvatarUpload(file){
            const isJPG = file.type === 'image/jpeg'
            const isLt2M = file.size / 1024 / 1024 < 2

            if (!isJPG) {
              this.$message.error('上传头像图片只能是 JPG 格式!')
            }
            if (!isLt2M) {
              this.$message.error('上传头像图片大小不能超过 2MB!')
            }
            return isJPG && isLt2M
        }

    },
}
</script>

<style scope>
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
  .tinymce-container {
  line-height: 29px;
  }

</style>