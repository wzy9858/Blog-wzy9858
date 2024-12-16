<template>
    <div class="manage-article-container">
        <!-- 顶栏标签 -->
        <div class="top-tab">
            <el-button type="success" size="small" @click="createArticle">
                新建文章
            </el-button>
            <el-button type="danger" size="small" @click="handleClick">
                删除所选
            </el-button>
        </div>
        
        <!-- 表格 -->
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
                        <el-button type="primary" size="small" plain @click="handleClick = true">
                            修改
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>

    <!-- 点击 -->
    <el-dialog v-model="handleClick" title="编辑" width="500" center>
        <div class="edit-container">
            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">作者</span>
                <el-input v-model="inputTitle" style="width: 160px;" placeholder="请输入文章标题" />
            </div>
            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">热度</span>
                <el-input v-model="inputTitle" style="width: 160px;" placeholder="请输入文章作者" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">文章标题</span>
                <el-input v-model="inputTitle" style="width: 160px;" placeholder="请输入文章标题" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">文章标签</span>
                <el-input v-model="inputTitle" style="width: 160px;" placeholder="请输入文章标签" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">创建时间</span>
                <el-input v-model="inputTitle" style="width: 160px;" placeholder="请输入创建时间" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">图片地址</span>
                <el-input v-model="inputTitle" style="width: 160px;" placeholder="请输入图片地址" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">置顶数值</span>
                <el-input v-model="inputTitle" style="width: 160px;" placeholder="请输入置顶数值" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">是否加密</span>
                <el-switch v-model="value1" />
            </div>

            <div style="padding: 0.5rem;">
                <el-button type="success" size="small" @click="handleClick">
                    保存文章
                </el-button>
                
                 <el-button type="success" size="small" @click="editArticle">
                    修改文章
                </el-button>
            </div>
        </div>
    </el-dialog>

</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
let router = useRouter()
// 是否加密
const value1 = ref(true)

const handleClick = ref(false)


//修改文章的按钮 应该传过去文章的id并且跳转到修改文章页面,
function editArticle(){
    router.push('/edit')
}

function createArticle(){
    handleClick.value = true
    // router.push('/edit')
}

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
.edit-container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.top-tab {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 1rem;
}

.manage-article-container {
    margin-top: 70px;
}
</style>