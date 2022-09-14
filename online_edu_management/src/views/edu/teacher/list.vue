<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="姓名">
        <!-- clearable属性可以生成清除标志同时清除内容 -->
        <el-input
          v-model="voTeacher.name"
          placeholder="请输入名字"
        ></el-input>
      </el-form-item>
      <el-form-item label="头衔">
        <el-select v-model="voTeacher.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="开始">
        <el-date-picker
          v-model="voTeacher.begin"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束">
        <el-date-picker
          v-model="voTeacher.end"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getList()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据展示 -->
    <el-table :data="list" border style="width: 100%">
      <el-table-column sortable prop="id" label="id" width="180">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="180"> </el-table-column>
      <el-table-column prop="career" label="头衔">
        <template slot-scope="scope">
          <!--  slot-scope="scope" 可以获取数据，scope相当于整个list对象-->
          {{ scope.row.level === 1 ? "高級讲师" : "首席讲师" }}
        </template>
      </el-table-column>
      <el-table-column prop="intro" label="简介"> </el-table-column>
      <el-table-column prop="sort" label="排序"> </el-table-column>
      <el-table-column prop="gmtCreate" label="添加时间"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <!-- router-link 默认会被渲染成一个a标签 -->
          <router-link :to="'/teacher/edit/' + scope.row.id">
          <el-button size="mini" type="primary" >编辑</el-button>
          </router-link>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.id,scope.row.name)"
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
      @current-change="getList"
    />
  </div>
</template>


<script>
//引入需要调用的js文件
import teacher from "@/api/edu/teacher.js";

export default {
  name: "list",
  data() {
    //定义变量和初始值
    return {
      pageNum: 1, //当前页
      pageSize: 4, //每页数量
      voTeacher: {}, //条件查询封装对象
      list: [], //查询接口后返回的数据
      total: 0, //总记录数
    };
  },
  created() {
    //在vue对象创建之后，数据转载之前执行，一般调用methods中定义的方法
    this.getList();
  },
  methods: {
    //创建具体的方法，调用teacher.js定义的方法
    //查询讲师列表
    getList(page = 1) {
      this.pageNum = page;
      teacher.getTeacherList(this.pageNum, this.pageSize,this.voTeacher)
        .then((response) => {
          this.list = response.data.records;
          this.total = response.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //清空查询条件
    clear() {
      this.voTeacher = {};
      this.getList();
    },
    handleDelete(id,name) {
      this.$confirm(`此操作将删除 ${name} 的讲师信息, 是否继续?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
      .then(() => {
          //调用删除方法
        teacher.delTeacher(id)
            .then(response => {
            //删除成功弹窗
            this.$message({
              type: "success",
              message: "删除成功!",
              })
            //刷新页面
            this.getList();
            })
          })
    },
  },
};
</script>





