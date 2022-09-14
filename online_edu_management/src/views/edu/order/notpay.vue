<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item >
        <el-input
        placeholder="用户名"
        type="mini"
        v-model="voOrder.nickname">
        <i slot="prefix" class="el-input__icon el-icon-user"></i>
      </el-input>
      </el-form-item>
      <el-form-item >
        <el-select v-model="voOrder.teacherName" placeholder="课程讲师">
          <el-option
            v-for="teacher in teachers"
            :key="teacher.teacherId"
            :label="teacher.name"
            :value="teacher.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item >
        <el-select v-model="voOrder.courseId" placeholder="课程名称">
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
          v-model="voOrder.startTime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择开始时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="voOrder.endTime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择结束时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getOrderList()">查询</el-button>
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
      <el-table-column sortable prop="id" label="订单号"  min-width="110%">
      </el-table-column>
      <el-table-column prop="nickname" label="用户名"></el-table-column>
      <el-table-column prop="mobile" label="手机号"></el-table-column>
      <el-table-column prop="courseTitle" label="课程名称"></el-table-column>
      <el-table-column prop="teacherName" label="讲师名称"></el-table-column>
      <el-table-column prop="totalFee" label="支付金额" ></el-table-column>
      <el-table-column prop="gmtCreate" label="购买时间" min-width="100%"></el-table-column>
      <el-table-column prop="gmtModified" label="支付时间" min-width="100%"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.id)"
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
      @current-change="getOrderList"
    />
  </div>
</template>


<script>
//引入需要调用的js文件
import order from "@/api/edu/order"
import comment from "@/api/edu/comment"

export default {
  name: "list",
  data() {
    //定义变量和初始值
    return {
      pageNum: 1, //当前页
      pageSize: 5, //每页数量
      voOrder: {}, //条件查询封装对象
      list: [], //查询接口后返回的数据
      total: 0, //总记录数
      ids:[], //需要处理的id数组
      courses:[], //课程名称数组
      teachers:[], //老师名称数组
    };
  },
  created() {
    //在vue对象创建之后，数据转载之前执行，一般调用methods中定义的方法
    this.getOrderList()  

    //查询讲师和课程信息
    this.showTeacher()
    this.showCourse()
  },
  methods: {
    //创建具体的方法，调用teacher.js定义的方法
    //查询评论列表
    getOrderList(page = 1){
      this.pageNum = page
      order.getOrders(this.pageNum,this.pageSize,this.voOrder,0)
        .then(response => {
          //回填信息
          this.list = response.data.records
          this.pageNum = response.data.current
          this.pageSize = response.data.size
          this.total = response.data.total
        })
    },
    //清空查询条件
    clear() {
      this.voOrder = {}
      this.getOrderList()
    },
    handleDelete(id) {
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
            this.getOrderList();
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
       this.$confirm(`此操作将删除当前选择的订单信息, 是否继续?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //调用删除方法
        order.delOrder(this.ids)
            .then(response => {
            //删除成功弹窗
            this.$message({
              type: "success",
              message: "删除成功!",
              })
            //刷新页面
            this.getOrderList();
            })
          
          })  
      },
      showTeacher(){
        comment.showTeacher()
              .then(response => {
                this.teachers = response.data
                console.log(this.teachers);
              })
      },
      showCourse(){
        comment.showCourse()
              .then(response => {
                this.courses = response.data
                console.log(this.courses);
              })
      }

  }
};
</script>





