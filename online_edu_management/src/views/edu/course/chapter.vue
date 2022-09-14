<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-row>
      <el-col :span="10" :offset="4">
      <el-button type="text" size="big" @click="openAddChapterbutton">添加章节</el-button>
      </el-col >
      <!-- 添加章节表单 -->
      <el-dialog :visible.sync="addChapterbutton" title="添加章节">
          <el-form :model="eduChapter" label-width="80px">
              <el-form-item label="章节标题" style="width:50%">
                  <el-input v-model="eduChapter.title"/>
              </el-form-item>
              <el-form-item label="章节排序">
                  <el-input-number v-model="eduChapter.sort" :min="0" controls-position="right"/>
              </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="addChapterbutton = false">取 消</el-button>
              <el-button type="primary" @click="addOrUpdate">确 定</el-button>
          </div>
      </el-dialog>

    </el-row >

    <el-row >
      <el-col :span="15" :offset="4">
            <!-- 章节 -->
        <ul class="chanpterList">
            <li
                v-for="eduChapter in chapterList"
                :key="eduChapter.id">
                <p>
                    {{ eduChapter.title }}

                    <span class="acts">
                        <el-button type="text" @click="openBarChapterbutton(null,eduChapter.id)">添加课时</el-button>
                        <el-button style="" type="text" @click="openUpdateChapter(eduChapter.id)">修改</el-button>
                        <el-button type="text" @click="delChapter(eduChapter.id)">删除</el-button>
                    </span>
                </p>

                <!-- 视频 -->
                <ul class="chanpterList videoList">
                    <li
                        v-for="eduVideo in eduChapter.courseBarList"
                        :key="eduVideo.id">
                        <p>{{ eduVideo.title }}
                            <span class="acts">
                                <el-button type="text" @click="openBarChapterbutton(eduVideo.id,eduChapter.id)">编辑</el-button>
                                <el-button type="text" @click="delBar(eduVideo.id)">删除</el-button>
                            </span>
                        </p>
                    </li>
                </ul>
            </li>
        </ul>
      </el-col>
  </el-row>

        <!-- 修改章节表单 -->
      <el-dialog :visible.sync="updateChapterbutton" title="修改章节">
          <el-form :model="eduChapter" label-width="80px">
              <el-form-item label="章节标题" style="width:50%">
                  <el-input v-model="eduChapter.title"/>
              </el-form-item>
              <el-form-item label="章节排序">
                  <el-input-number v-model="eduChapter.sort" :min="0" controls-position="right"/>
              </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="updateChapterbutton = false">取 消</el-button>
              <el-button type="primary" @click="addOrUpdate">确 定</el-button>
          </div>
      </el-dialog>

      <!-- 添加或修改课时表单 -->
      <el-dialog :visible.sync="openBarbutton" title="课时设置">
          <el-form :model="eduVoder" label-width="80px">
              <el-form-item label="课时标题" style="width:50%">
                  <el-input v-model="eduVoder.title"/>
              </el-form-item>
              <el-form-item label="课时排序">
                  <el-input-number v-model="eduVoder.sort" :min="0" controls-position="right"/>
              </el-form-item>
              <el-form-item label="是否免费">
                    <el-radio-group v-model="eduVoder.isFree">
                      <el-radio :label="0">付费</el-radio>
                      <el-radio :label="1">免费</el-radio>
                    </el-radio-group>
                  <!-- <el-input-number v-model="eduVoder.isFree" :min="0" controls-position="right"/> -->
              </el-form-item>
              <el-form-item label="上传视频">
                    <el-upload
                      ref="upload"
                      :auto-upload="true"
                      :on-success="fileUploadSuccess"
                      :on-error="fileUploadError"
                      :before-remove="beforeRemove"
                      :on-remove="removeVideo"
                      :disabled="importBtnDisabled"
                      :limit="1"
                      :file-list="fileList"
                      :action="BASE_API+'/servicevod/videoUpload'"
                      name="file"
                      accept="video/*">
                      <el-button slot="trigger" type="primary">选取视频</el-button>
                    </el-upload> 
              </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="openBarbutton = false">取 消</el-button>
              <el-button type="primary" @click="addBarOrUpdateBar">确 定</el-button>
          </div>
      </el-dialog>

    <el-form label-width="42%" >
          <el-form-item>
            <el-button @click="previous">上一步</el-button>
            <el-button :disabled="false" type="primary" @click="next">保存并下一步</el-button>
    </el-form-item>
</el-form> 
</div>
</template>


<script>
import chapter from '@/api/edu/chapter'
import barvideo from '@/api/edu/barvideo'


export default {
    name:'info',
    data() {
        return {
            courseId: this.$route.params.id,
            chapterList: [],
            eduChapter: {
              id:'',
              courseId:'',
              title:'',
              sort:''
            },
            eduVoder: {
                id: '',
                title:'',
                courseId:'',
                chapterId:'',
                isFree:'1',
                videoSourceId:'',
                videoOriginalName:'',
            },
            courseId:this.$route.params.id,
            addChapterbutton: false,
            updateChapterbutton: false,
            openBarbutton: false,
            BASE_API: process.env.BASE_API, // 接口API地址
            importBtnDisabled: false, // 按钮是否禁用,
            loading: false,
            fileList: [],

        }
    },
    created() {
        this.getChapterList()
    },
    methods: {
        //初始化添加章节的内容
        openAddChapterbutton(){
          this.addChapterbutton = true
          this.eduChapter.title = ''
          this.eduChapter.sort = ''
          this.eduChapter.id = ''
        },
        //通过判断是否有chapterId来判断章节是添加还是更新
        addOrUpdate(){
            if(this.eduChapter.id === ''){
              this.addChapter()
            }else {
            console.log(this.eduChapter.id);
              this.updateChapter()
            }
        },
        //添加课程章节
        addChapter(){
          this.eduChapter.courseId = this.courseId
          chapter.addChapter(this.eduChapter)
              .then(resopnse => { 
                this.addChapterbutton = false;
                this.getChapterList()
              })
        },
        //修改课程章节
        updateChapter(){
          this.eduChapter.courseId = this.courseId
          chapter.updateChapter(this.eduChapter)
            .then(resopnse => { 
                this.updateChapterbutton = false;
                this.getChapterList()
              })
        },
        //打开修改课程章节弹窗并填充数据
        openUpdateChapter(id){
          //打开模态框
          this.updateChapterbutton = true

          //根据id查询章节信息
          this.getChapterById(id)
          
        },
        //删除章节
        delChapter(id){
          this.$confirm("此操作将删除该章节(包括此章节内的所有小节), 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
              })
              .then(() => {
                  //调用删除方法
                chapter.delChapter(id)
                    .then(response => {
                    //删除成功弹窗
                    this.$message({
                      type: "success",
                      message: "删除成功!",
                      });
                    //刷新页面
                    this.getChapterList()
                    })
                  
                  })
        },
        //获取章节的所有内容
        getChapterList(){
            chapter.getChapterById(this.courseId)
                .then(resopnse => {
                  this.chapterList = resopnse.data
                })
        },
        //根据id查询章节信息
        getChapterById(id){
          chapter.queryChapter(id)
            .then(response => {
              this.eduChapter = response.data
            })
        },
        //打开小节的内容弹窗并判断是否需要填充数据
        openBarChapterbutton(eduVideoId,chapterId){
          this.openBarbutton = true
          
          //eduVideoId为空表示添加操作
          if(eduVideoId === null && chapterId !== ''){
            this.eduVoder.title = ''
            this.eduVoder.sort = ''
            this.eduVoder.isFree = 1
            this.eduVoder.videoSourceId = ''
            this.eduVoder.videoOriginalName= ''
            this.eduVoder.id = ''
            this.eduVoder.chapterId = chapterId
            this.eduVoder.courseId = this.courseId
            this.fileList =[]
          }else { //eduVideoId不为空表示更新编辑操作
              barvideo.queryEduVideo(eduVideoId)
                .then(response => {
                  this.eduVoder = response.data
                  this.fileList = [{name: `${this.eduVoder.videoOriginalName}`}]
                }) 
          }
        },
        //判断课时是添加还是更新
        addBarOrUpdateBar(){
            if(this.eduVoder.id === ''){//id为空表示是添加操作
              this.addNewBar()
            }else {//id不为空表示是更新操作
              this.updateBar()
            }
        },
        //添加课程小节
        addNewBar(){
          barvideo.addEduVideo(this.eduVoder)
            .then(response => {
              this.$message.success("添加成功")
              this.openBarbutton = false
              this.getChapterList()
            })
        },
        //更新课程小节
        updateBar(){
          barvideo.updateEduVideo(this.eduVoder)
            .then(response => {
              this.$message.success("更新成功")
              this.openBarbutton = false
              this.getChapterList()
            })
        },
        //删除课程小节
        delBar(id){
          this.$confirm("此操作将删除该课时, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
              })
              .then(() => {
                  //调用删除方法
                barvideo.delEduVideo(id)
                    .then(response => {
                    //删除成功弹窗
                    this.$message({
                      type: "success",
                      message: "删除成功!",
                      });
                    //刷新页面
                    this.getChapterList()
                    })
                  
                  })
        },
        //删除视频之前的钩子方法
        beforeRemove(file){
            return this.$confirm(`确定移除 ${ file.name }？`)  
        },
        //删除视频的方法
        removeVideo(){
          barvideo.delAliyunVideoById(this.eduVoder.videoSourceId)
            .then(response => {
              //提示信息
              this.$message.success("移除成功")
              //清空文件列表
              this.fileList = []
              //清空eduVoder的文件名字和文件id
              this.eduVoder.videoSourceId = ''
              this.eduVoder.videoOriginalName= ''
            })
        },
        //视频上传成功的回调方法
        fileUploadSuccess(response,file){
            this.loading = false
            //提示信息
            this.$message({
                type:'success',
                message:'上传成功'
            })
            this.eduVoder.videoSourceId = response.data
            this.eduVoder.videoOriginalName = file.name
        },
        //视频上传失败的回调方法
        fileUploadError(){
            this.loading = false
            //提示信息
            this.$message({
                type:'error',
                message:'上传失败'
            })
            //清空文件列表
            this.fileList = []
            //清空eduVoder的文件名字和文件id
            this.eduVoder.videoSourceId = ''
            this.eduVoder.videoOriginalName= ''
        },
        
        //上一步的方法
        previous(){
           this.$router.push({path:'/course/info/' + this.courseId})
        },
        //下一步的方法
        next(){
           this.$router.push({path:'/course/publish/' + this.courseId})
        },
        


    },
}
</script>

<style scoped>
    .el-row {
    margin-bottom: 5px;
    }
    .el-col {
    border-radius: 6px;
    }
    .chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
    }
    .chanpterList li{
    position: relative;
    }
    .chanpterList p{
    float: left;
    font-size: 18px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
    }
    .chanpterList .acts {
        float: right;
        font-size: 14px;
    }

    .videoList{
    padding-left: 40px;
    }
    .videoList p{
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
    }
</style>