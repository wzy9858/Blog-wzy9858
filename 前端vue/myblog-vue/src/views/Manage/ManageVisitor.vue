<template>
    <div class="visitor-manage-container">

        <!-- 顶栏标签 -->
        <div class="top-tab">
            <el-button type="danger" size="small" @click="handleClick">
                删除所选
            </el-button>
        </div>

        <div class="table">
            <el-table :data="tableData" style="width: 100%">

                <el-table-column type="selection" width="28" />

                <el-table-column fixed prop="date" label="id" width="90" />

                <el-table-column prop="name" label="名字" width="120" />

                <el-table-column prop="state" label="设备" width="120" />

                <el-table-column prop="city" label="城市" width="120" />

                <el-table-column prop="address" label="ip" width="600" />

                <el-table-column prop="zip" label="Zip" width="120" />

                <el-table-column fixed="right" label="操作" min-width="65">
                    <template #default>
                        <el-button type="primary" size="small" @click="handleClick = true">
                            备注
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

    </div>

    <!-- 点击事件 -->
    <el-dialog v-model="handleClick" title="访客备注" width="500" center>
        <div class="center-all">
            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">备注</span>
                <el-input v-model="inputTitle" style="width: 160px;" placeholder="请输入文章标题" />
            </div>
            <div style="padding: 0.5rem;">
                <el-button type="success" size="small" @click="handleClick">
                    保存
                </el-button>
            </div>
        </div>

    </el-dialog>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const handleClick = ref(false)
const inputTitle = ref('')

const tableData = [
    {
        date: '2016-05-03',
        name: 'Tom',
        state: 'California',
        city: 'Los Angeles',
        address: 'No. 189, Grove St, Los Angeles',
        zip: 'CA 90036',
        tag: 'Home',
    },
    {
        date: '2016-05-02',
        name: 'Tom',
        state: 'California',
        city: 'Los Angeles',
        address: 'No. 189, Grove St, Los Angeles',
        zip: 'CA 90036',
        tag: 'Office',
    },

]

</script>

<style scoped>
.top-tab {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 1rem;
}

.visitor-manage-container {
    margin-top: 70px;
}
</style>