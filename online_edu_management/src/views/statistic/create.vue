<template>
  <div class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">

      <el-form-item label="日期">
        <el-date-picker
          v-model="day"
          type="date"
          placeholder="选择要统计的日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>

      <el-button
        :disabled="btnDisabled"
        type="primary"
        @click="countRegister">生成</el-button>
    </el-form>

  </div>
</template>

<script>
import statistic from '@/api/statistic/static'
export default {
    name:'create',
    data() {
        return {
            btnDisabled:false,
            day:'',
        }
    },
    methods: {
        countRegister(){
            statistic.countRegister(this.day)
                    .then(response => {
                    // if(response.data === 20000){
                        this.$message.success("生成成功！")
                        this.$router.push({path:'/statistic/show'})
                    // }
                   
                    })
        }
    },
}
</script>