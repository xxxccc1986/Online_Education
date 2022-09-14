<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="'https://onlineedufile.oss-cn-guangzhou.aliyuncs.com/demo.xlsx'">点击下载模版</a>
        </el-tag>

      </el-form-item>

      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API+'/serviceedu/subject/add'"
          name="file"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">上传</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
    name: 'add',
    data() {
        return {
            BASE_API: process.env.BASE_API, // 接口API地址
            importBtnDisabled: false, // 按钮是否禁用,
            loading: false
        }
    },
    created() {
        
    },
    methods: {
        //点击上传按钮进行上传表单的提交
        submitUpload(){
            this.importBtnDisabled = true
            this.loading = true
            //表单提交 原生js写法是这样 ==> document.getElementById("upload").submit()
            this.$refs.upload.submit()
        },
        //上传成功的回调方法
        fileUploadSuccess(){
            this.loading = false
            //提示信息
            this.$message({
                type:'success',
                message:'上传成功'
            })
            //跳转到课程分类列表页面
            this.$router.push({path:'/subject/list'})
        },
        //上传失败的回调方法
        fileUploadError(){
            this.loading = false
            //提示信息
            this.$message({
                type:'error',
                message:'上传失败'
            })
        },
    },
}
</script>