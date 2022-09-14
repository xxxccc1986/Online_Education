<template>
  <div style="padding: 10px">

    <!--  搜索区域  -->
    <div style="margin: 10px 0px">
      <!-- clearable属性给输入框添加清空输入的功能-->
      <el-input v-model="title" placeholder="请输入关键字" style="width: 25%"/>
      <el-button type="primary" style="margin-left: 5px" @click="conditionQuery" >查询</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="clear" >清空</el-button>
    </div>

    <!--  数据展示区域  -->
    <el-table :data="tableData"
              border
              style="width: 100%"
              stripe >
      <el-table-column prop="id" label="id"  sortable />
      <el-table-column prop="title" label="轮播图名称"  />
      <el-table-column prop="linkUrl" label="跳转地址"  />
      <el-table-column prop="sort" label="排序"  />
      <el-table-column label="轮播图" >
        <template slot-scope="scope">
            <img
             style="width: 250px; height: 100px"
            :src= "scope.row.imageUrl" />
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" >
        <template slot-scope="scope">
          <!--  scope.row 是取得当前这一行的所有数据，实际上获取了当前这个行的所有内容-->
          <el-button size="mini" type="primary" @click="handleEdit(scope.row.id)">
            编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="isDeleted(scope.row.id)">
            删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  分页区域  -->
    <div style="margin: 10px 0px">
    <!-- @current-change是饿了么已经实现的，绑定了一个点击事件
      当currentPage改变时会触发，传的参数是当前页数，即:current-page绑定的值-->
    <el-pagination
      :current-page="pageNum"
      :page-size="pageSize"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total,prev, pager, next,jumper"
      @current-change="handleCurrentChange"/>
    </div>
  </div>
</template>

<script >
import banner from "@/api/edu/banner"

export default {
  //定义组件名字
  name: 'list',
  //定义该组件的私有数据
  data(){
    return{
      title:'',
      pageNum: 1,
      pageSize: 2,
      total: 0,
      tableData: [],
    }
  },
  created() {
    this.init();
  },
  //定义该组件的业务处理方法
  methods:{
      //监听到页数变化
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.pageNum = val;

        this.init()
      },
    //条件查询
    conditionQuery(){
      banner.queryBannerByTitle(this.title,this.pageNum,this.pageSize)
        .then(response => {
          this.tableData = response.data.records
          this.total = response.data.total
        })
    },
    clear(){
      this.title = '',
      this.init()
    },
    //页面查询
    init(){
      banner.queryBannerList(this.pageNum,this.pageSize)
          .then(response => {
              this.tableData = response.data.records
              this.total = response.data.total
          })    
    },
    //编辑
    handleEdit(id){
      //跳转到隐藏路由
      this.$router.push({path:'/banner/update/' + id})
    },
    isDeleted(id){
      this.$confirm(`确定要删除id是${id}的轮播图吗？`,'提示',{
        confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      }).then(() => {
        //调用方法进行删除 
        this.handleDelete(id)   
      })
    },
    //删除
    handleDelete(id){
      banner.delBanner(id)
          .then(response => {
              //提示
              this.$message.success("删除成功")
              //刷新页面
              this.load()
          })
    },
    // handleSizeChange(){
    //     this.load();
    // },
    // handleCurrentChange(){
    //     this.load();
    // },
    
  }
}
</script>

