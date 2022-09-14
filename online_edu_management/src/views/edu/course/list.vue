<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="课程名称">
        <!-- clearable属性可以生成清除标志同时清除内容 -->
        <el-input
          v-model="queryCourse.title"
          clearable
          placeholder="请输入课程名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="发布状态">
        <el-select v-model="queryCourse.status"  placeholder="未发布">
          <el-option :value="Draft = 'Draft'"   label="未发布" />
          <el-option :value="Normal = 'Normal'" label="已发布" />
        </el-select>
      </el-form-item>
      
      <el-form-item>
        <el-button type="primary" @click="conditionQueryCourse()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据展示 -->
    <el-table :data="list" border style="width: 100%">
      <el-table-column sortable prop="id" label="序号" width="180">
      </el-table-column>
      <el-table-column prop="title" label="课程名称" width="180"> </el-table-column>
      <el-table-column prop="status" label="课程状态">
        <template slot-scope="scope">
          <!--  slot-scope="scope" 可以获取数据，scope相当于整个list对象-->
          {{ scope.row.status === 'Draft' ? "未发布" : "已发布" }}
        </template>
      </el-table-column>
      <el-table-column prop="lessonNum" label="课时数" width="180"> </el-table-column>
      <el-table-column prop="gmtCreate" label="添加时间"> </el-table-column>
      <el-table-column prop="viewCount" label="浏览数量"> </el-table-column>
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <!-- router-link 默认会被渲染成一个a标签 -->
          <router-link :to="'/course/info/' + scope.row.id">
          <el-button size="mini" type="primary">编辑课程信息</el-button>
          </router-link>
          <p></p>
          <router-link :to="'/course/chapter/' + scope.row.id">
          <el-button size="mini" type="info" >编辑课程大纲</el-button>
          </router-link>
          <p></p>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.id)">
            删除课程</el-button>
        </template>
        
      </el-table-column>
    </el-table>

    <!-- 分页条 -->
    <!-- @current-change是饿了么已经实现的，绑定了一个点击事件
      当currentPage改变时会触发，传的参数是当前页数，即:current-page绑定的值-->
    <el-pagination
      :current-page="pageNum"
      :page-size="pageSize"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total,prev, pager, next,jumper"
      @current-change="conditionQueryCourse"
    />
  </div>
</template>

<script>
import course from '@/api/edu/course'

export default {
    name:'list',
    data() {
        return {
            pageNum: 1,
            pageSize: 3,
            total: 0,
            list:[],
            queryCourse: {
              title:'',
              status:''
            },
        }
    },
    created(){
        this.conditionQueryCourse()
    },
    methods: {
        clear(){
            this.queryCourse = {}
            //刷新页面
            this.conditionQueryCourse();
        },
        //删除指定id的课程 
        handleDelete(id) {
            this.$confirm("此操作将删除该课程, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                //调用删除方法
                course.delCourseById(id)
                    .then(response => {
                    //删除成功弹窗
                    this.$message({
                    type: "success",
                    message: "删除成功!",
                    });
                  })
                //刷新页面
                this.conditionQueryCourse();
                })
        },
        conditionQueryCourse(page = 1){
          this.pageNum = page
          // if(this.queryCourse.title === '' && this.queryCourse.status === ''){
          //   this.$message.error("请先选择查询条件")
          //   return false
          // }
          course.conditionQueryCourse(page,this.pageSize,this.queryCourse)
              .then(response => {
                this.total = response.data.total
                this.list =  response.data.records
              })

        }
    },
}
</script>