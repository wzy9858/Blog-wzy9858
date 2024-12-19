<template>
    <div class="manage-article-container">
        <!-- 顶栏标签 -->
        <div class="top-tab">
            <el-button type="success" size="small" @click="createArticleBtn">
                新建文章
            </el-button>
            <el-button type="danger" size="small" @click="handleClick">
                删除所选
            </el-button>
        </div>

        <!-- 表格 -->
        <div class="table">
            <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">

                <el-table-column type="selection" width="28" />

                <el-table-column fixed prop="id" label="id" width="90" />
                <el-table-column prop="owner" label="作者" width="90" />
                <el-table-column prop="articleTitle" label="标题" width="120" />

                <el-table-column prop="idEncrypted" label="是否加密" width="120" />

                <el-table-column prop="tags" label="标签" width="120" />

                <el-table-column prop="popularity" label="热度" width="90" />

                <el-table-column prop="isPinned" label="置顶值" width="90" />



                <el-table-column prop="homeDisplayImageUrl" label="主页图片地址" width="120" />



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
                <el-input v-model="article.owner" style="width: 160px;" placeholder="请输入文章作者" />
            </div>
            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">热度</span>
                <el-input v-model="article.popularity" style="width: 160px;" placeholder="请输入文章热度" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">文章标题</span>
                <el-input v-model="article.articleTitle" style="width: 160px;" placeholder="请输入文章标题" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">文章标签</span>
                <el-input v-model="article.tags" style="width: 160px;" placeholder="请输入文章标签" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">创建时间</span>
                <el-input v-model="article.createdAt" style="width: 160px;" placeholder="请输入创建时间" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">图片地址</span>
                <el-input v-model="article.homeDisplayImageUrl" style="width: 160px;" placeholder="请输入图片地址" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">置顶数值</span>
                <el-input v-model="article.isPinned" style="width: 160px;" placeholder="请输入置顶数值" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">是否加密</span>
                <el-switch v-model="value1" />
            </div>

            <div style="padding: 0.5rem;">
                <el-button type="success" size="small" @click="saveArticle">
                    保存文章
                </el-button>

                <el-button type="success" size="small" @click="editArticle">
                    编辑内容
                </el-button>

            </div>

        </div>

    </el-dialog>

</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus'
let router = useRouter()
// 是否加密
const value1 = ref(true)

const handleClick = ref(false)

let deleteId = []
function handleSelectionChange(selection) {
    deleteId = []
    selection.forEach((element) => {
        deleteId.push(element.id)
    });
    console.log(deleteId);
}


function formatDateTime(date) {
    const year = date.getFullYear(); // 获取年份
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // 获取月份，月份从0开始，所以+1
    const day = date.getDate().toString().padStart(2, '0'); // 获取日期
    const hours = date.getHours().toString().padStart(2, '0'); // 获取小时
    const minutes = date.getMinutes().toString().padStart(2, '0'); // 获取分钟
    const seconds = date.getSeconds().toString().padStart(2, '0'); // 获取秒
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`; // 拼接成字符串
}
// 使用示例
const date = new Date(); // 假设这是你给定的日期时间
const formattedDateTime = formatDateTime(date);
console.log(formattedDateTime); // 输出: 2024-12-17 13:57:50
let article = reactive({
    id: 0,
    articleTitle: "",
    articleContent: "",
    createdAt: formattedDateTime,
    // updatedAt: "2024-12-17 13:57:50",
    isEncrypted: 1,
    tags: "tag1",
    popularity: 0,
    homeDisplayImageUrl: "",
    isPinned: 1,
    owner: "菜鸟拯救世界"
})

import { createArticle } from '../../ts/axios/articleHttp';
import { toRefs } from 'vue';
// 保存文章
function saveArticle() {

    if (article.articleTitle == "") {
        ElMessage.error("标题不能为空")
    } else {
        if (value1.value = true) {
            article.isEncrypted = 1
        } else {
            article.isEncrypted = 0
        }

        createArticle(article).then(
            s => {
                // if()


                ElMessage.success("创建成功")
            }
        ).catch(
            e => {
                ElMessage.error("出错啦")
            }
        )
    }
}

//修改文章的按钮 应该传过去文章的id并且跳转到修改文章页面,
function editArticle() {
    router.push('/edit')
}

function createArticleBtn() {
    handleClick.value = true
    // router.push('/edit')
}

const inputTitle = ref('')


const tableData = [
    {
        id: 0,
        articleTitle: "",
        articleContent: "",
        createdAt: formattedDateTime,
        // updatedAt: "2024-12-17 13:57:50",
        isEncrypted: 1,
        tags: "tag1",
        popularity: 0,
        homeDisplayImageUrl: "",
        isPinned: 1,
        owner: "菜鸟拯救世界"
    }, {
        id: 1,
        articleTitle: "",
        articleContent: "",
        createdAt: formattedDateTime,
        // updatedAt: "2024-12-17 13:57:50",
        isEncrypted: 1,
        tags: "tag1",
        popularity: 0,
        homeDisplayImageUrl: "",
        isPinned: 1,
        owner: "菜鸟拯救世界"
    }

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