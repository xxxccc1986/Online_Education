<template>
  <div class="dashboard-container">
  <el-col :span="8">
      <el-card shadow="always" style="border-radius: 4px">
        <div class="dashboard-text">
          角色名字:{{ name }}
        </div>
        <div class="dashboard-text">
          角色身份:
          <span v-for="role in roles" :key="role">
            {{ role }}
          </span>
        </div>
        <div class="dashboard-text" style="font-size:17px;color: red;font-weight:bold">
          登录ip:
          <span style="padding-right: 10px;">
            {{ipInfo.ip}}
          </span>
          登录地点:
          <span >
            {{ipInfo.province}}{{ipInfo.city}}
          </span>
        </div>
      </el-card>
  </el-col> 
  </div>
</template>

<script>
import {getIpInfo} from '@/api/login'
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  data() {
    return {
      // ipInfo:[]
      ipInfo:{
        ip:'',
        province:'',
        city:''
      }
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },
  mounted() {
    getIpInfo()
      .then(response => {
          this.ipInfo = response.data
        })
      .catch(error => {
        console.log(error);
      })
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
