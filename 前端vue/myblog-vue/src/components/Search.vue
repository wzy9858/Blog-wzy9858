<template>
  <div class="search-container">
    <!-- 搜索输入框带按钮 -->
    <el-input 
      v-model="searchKey"
      placeholder="请输入关键词"
      clearable
      @keyup.enter="handleSearch"
      class="search-input"
    >
      <template #append>
        <el-button 
          type="primary" 
          icon="Search"
          @click="handleSearch"
        >搜索</el-button>
      </template>
    </el-input>


     <!-- 带点击功能的表格 -->
     <el-table 
      :data="tableData" 
      v-loading="loading"
      empty-text="未找到相关文章"
      stripe
      style="width: 100%"
      @row-click="handleRowClick"
      class="clickable-table"
    >
      <el-table-column prop="articleTitle" label="标题" width="200" />
      <el-table-column prop="tags" label="标签" width="120" />
      <el-table-column prop="createdAt" label="创建时间" sortable />
      <el-table-column prop="updatedAt" label="更新时间" sortable />
      <el-table-column label="封面图" width="140">
        <template #default="{ row }">
          <el-image 
            :src="row.homeDisplayImageUrl"
            :preview-src-list="[row.homeDisplayImageUrl]"
            preview-teleported
            style="width: 100px; height: 60px"
          />
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { search } from '../ts/axios/visitorHttp'
import { useRouter } from 'vue-router';
// 响应式数据
const searchKey = ref('')
const tableData = ref([])
const loading = ref(false)
let router = useRouter();
// 表格行点击处理
const handleRowClick = (row) => {
  console.log("点击了row"+row.id);
  
  if (row?.id) {
    router.push({
      path: '/article',
      query: { id: row.id }
    })
  } else {
    ElMessage.error('文章ID获取失败')
  }
}
function handleSearch(){
  if (!searchKey.value.trim()) {
    ElMessage.warning('请输入搜索关键词')
    return
  }
  // console.log(searchKey.value);
  loading.value = true
  search(searchKey.value).then(
    s => {
      tableData.value = s.data || []
    }

  ).catch().finally(
    loading.value = false
  )
}

</script>

<style scoped>
.search-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
}

.search-input {
  margin-bottom: 30px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  
  :deep(.el-input-group__append) {
    background-color: #409eff;
    color: white;
    border: none;
  }
}
</style>