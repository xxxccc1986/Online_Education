<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item >
        <el-input
        placeholder="用户名"
        type="mini"
        v-model="voComment.username">
        <i slot="prefix" class="el-input__icon el-icon-user"></i>
      </el-input>
      </el-form-item>
      <el-form-item >
        <el-select v-model="voComment.teacherId" placeholder="课程讲师">
          <el-option
            v-for="teacher in teachers"
            :key="teacher.teacherId"
            :label="teacher.name"
            :value="teacher.teacherId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item >
        <el-select v-model="voComment.courseId" placeholder="课程名称">
          <el-option
            v-for="course in courses"
            :key="course.id"
            :label="course.title"
            :value="course.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="voComment.startTime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择开始时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="voComment.endTime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择结束时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getCommentList()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

    <el-button type="danger" size="mini" @click="batchDel">批量处理</el-button>

    <!-- 数据展示 -->
    <el-table :data="list" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column sortable prop="id" label="id" width="180">
      </el-table-column>
      <el-table-column prop="username" label="评价用户" width="180"> </el-table-column>
      <el-table-column prop="courseName" label="课程名称" width="180"> </el-table-column>
      <el-table-column prop="teacherName" label="讲师名称" width="180"> </el-table-column>
      <el-table-column prop="content" label="评价内容"> </el-table-column>
      <el-table-column prop="rate" label="评价星级"> 
        <template slot-scope="scope">
          <el-rate
            v-model="scope.row.rate"
            :disabled=true
            :colors="colors">
          </el-rate>
        </template>
      </el-table-column>
      <!-- min-width="100%"可以设置表头字段的宽度 -->
      <el-table-column prop="gmtCreate" label="评价时间" min-width="100%"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.id,scope.row.username)"
            >删除</el-button>
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
      @current-change="getCommentList"
    />
  </div>
</template>


<script>
//引入需要调用的js文件
import comment from "@/api/edu/comment";

export default {
  name: "list",
  data() {
    //定义变量和初始值
    return {
      pageNum: 1, //当前页
      pageSize: 5, //每页数量
      voComment: {}, //条件查询封装对象
      list: [], //查询接口后返回的数据
      total: 0, //总记录数
      ids:[], //需要处理的id数组
      courses:[], //课程名称数组
      teachers:[], //老师名称数组
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'] //评论星级的显示
    };
  },
  created() {
    //在vue对象创建之后，数据转载之前执行，一般调用methods中定义的方法
    this.getCommentList()  

    //查询讲师和课程信息
    this.showTeacher()
    this.showCourse()
  },
  methods: {
    //创建具体的方法，调用teacher.js定义的方法
    //查询评论列表
    getCommentList(page = 1){
      this.pageNum = page
      comment.getAllComment(this.pageNum,this.pageSize,this.voComment)
        .then(response => {
          //回填信息
          this.list = response.data.comments
          this.pageNum = response.data.current
          this.pageSize = response.data.size
          this.total = response.data.total
        })
    },
    //清空查询条件
    clear() {
      this.voComment = {}
      this.getCommentList()
    },
    handleDelete(id,username) {
      this.$confirm(`此操作将删除 ${username} 的评论信息, 是否继续?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //调用删除方法
        comment.delComment(id)
            .then(response => {
            //删除成功弹窗
            this.$message({
              type: "success",
              message: "删除成功!",
              })
            //刷新页面
            this.getCommentList();
            })
          
          })    
    },
    //选择器触发方法
    handleSelectionChange(selection){
      //map方法可以将多个行数据对象中的id抽取出来形成一个新的数组
      this.ids = selection.map(selection => selection.id)
    },
    //批量删除
    batchDel(){
       this.$confirm(`此操作将删除当前选择的评论信息, 是否继续?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //调用删除方法
        comment.delComment(this.ids)
            .then(response => {
            //删除成功弹窗
            this.$message({
              type: "success",
              message: "删除成功!",
              })
            //刷新页面
            this.getCommentList();
            })
          
          })  
      },
      showTeacher(){
        comment.showTeacher()
              .then(response => {
                //填充讲师下拉列表
                this.teachers = response.data
              })
      },
      showCourse(){
        comment.showCourse()
              .then(response => {
                //填充课程下拉列表
                this.courses = response.data
              })
      }

  }
};
</script>





