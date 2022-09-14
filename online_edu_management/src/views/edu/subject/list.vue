<template>
  <div class="app-container" style="width:40%">
    <el-input v-model="filterText" placeholder="请输入关键字" clearable style="margin-bottom:30px;" />

    <!-- :data绑定是查询回来的数据  :props绑定的属性可以获取data中对应使用数据 -->
    <el-tree
      ref="tree2"
      :data="list"
      :props="defaultProps" 
      :filter-node-method="filterNode"
      class="filter-tree"/>

  </div>
</template>

<script>
import subject from '@/api/edu/subject.js'

export default {
    name: 'list',
    data() {
        return {
        filterText: '',
        list: [],
        defaultProps: {
            children: 'children',
            label: 'title',
        }
        }
    },
    created() {
        this.getSubjectList();
    },
    watch: {
        filterText(val) {
        this.$refs.tree2.filter(val)
        }
    },
    methods: {
        filterNode(value, data) {
            if (!value) return true
            return data.title.toLowerCase().indexOf(value) !== -1
            },
        getSubjectList(){
            subject.getSubjectList()
                .then(response => {
                    this.list = response.data
                })
        },
    }
}
</script>

