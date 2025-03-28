<template>
    <div class="manage-article-container">
        <!-- 顶栏标签 -->
        <div class="top-tab">
            <el-button type="success" size="small" @click="createArticleInfoBtn">
                新建文章
            </el-button>
            <el-button type="danger" size="small" @click="deleteArticle">
                删除所选
            </el-button>
        </div>

        <!-- 表格 -->
        <div class="table">
            <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">

                <el-table-column type="selection" width="28" />

                <el-table-column fixed prop="id" label="id" width="50" />
                <el-table-column prop="owner" label="作者" width="90" />
                <el-table-column prop="articleTitle" label="标题" width="120" />

                <el-table-column prop="idEncrypted" label="是否加密" width="120" />

                <el-table-column prop="tags" label="标签" width="120" />

                <el-table-column prop="popularity" label="热度" width="90" />

                <el-table-column prop="isPinned" label="置顶值" width="90" />

                <el-table-column prop="homeDisplayImageUrl" label="主页图片地址" width="320" />

                <el-table-column fixed="right" label="操作" min-width="65">
                    <template #default="{ row }">
                        <el-button type="primary" size="small" plain @click="editArticleInfo(row)">
                            修改
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div class="split-page">
            <el-pagination background layout="prev, pager, next" :total=pagination.total :page-size=pagination.pageSize
                @current-change="changePage" />
            <!-- page Size 每页显示10个  -->
        </div>

    </div>

    <!-- 点击 -->
    <el-dialog v-model="handleClick" title="新建/编辑" width="500" center>

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

            <!-- <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">是否加密</span>
                <el-switch v-model="value1" />
            </div> -->

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


    <!-- 这是新建文章的 -->
    <!-- 点击 -->
    <el-dialog v-model="createArticlehandleClick" title="新建/编辑" width="500" center>

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

            <!-- <span style="color: red; ">Tip : 新建文章时请先保存再编辑</span> -->

            <div style="padding: 0.5rem;">
                <el-button type="success" size="small" @click="saveArticle">
                    保存文章
                </el-button>
            </div>

        </div>

    </el-dialog>




</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus'
import { createArticle } from '../../ts/axios/articleHttp';
import { toRefs, onMounted } from 'vue';
let router = useRouter()
import { ElLoading } from 'element-plus'

// 是否加密
const value1 = ref(true)

const handleClick = ref(false)
const createArticlehandleClick = ref(false)


let deleteId = []
function handleSelectionChange(selection) {
    deleteId = []
    selection.forEach((element) => {
        deleteId.push(element.id)
    });
    // console.log(deleteId);
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
                ElMessage.success("保存成功")
                upData()
            }
        ).catch(
            e => {
                ElMessage.error("出错啦")
            }
        )
    }
}
import { deleteArticles } from '../../ts/axios/articleHttp';

function deleteArticle() {
    // deleteId 是一个集合包含了要删除的id
    if (deleteId.length == 0) {
        console.log("选中为空未删除");
    } else {
        deleteArticles(deleteId).then(
            s => {
                if (s.data.code == 200) {
                    ElMessage.success("删除成功")
                    // 删除成功更新表格内容
                    upData()

                } else {
                    ElMessage.error("出现异常")
                }
            }
        ).catch(
            e => {
                ElMessage.error("出现异常")
            }
        )
    }
}


//修改文章的按钮 应该传过去文章的id并且跳转到修改文章页面,
function editArticle() {
    // saveArticle()//编辑文章的内容的时候先保存当前文章
    router.push(`/edit?id=${article.id}`)
}
// 点击修改按钮 修改文章信息

function editArticleInfo(row) {
    // 点击修改的时候应该拿到表格这一条的相关信息
    // console.log(row);//row就是
    article = row;
    // console.log(row.articleTitle);

    handleClick.value = true
}

// 点击创建文章 
function createArticleInfoBtn() {

    createArticlehandleClick.value = true

    // router.push('/edit')

}
const inputTitle = ref('')
let pagination = ref({
    total: 20, // 总条目数
    pageSize: 6, // 每页显示条目数
    currentPage: 1 // 当前页码
});

// 挂载的时候获取文章列表
import { getArtilesList } from '../../ts/axios/articleHttp'

function upData() {

    const loading = ElLoading.service({
        lock: true,
        text: '加载中...',
        background: 'rgba(0, 0, 0, 0.7)',
    })

    getArtilesList(currentPage.value).then(
        s => {
            pagination.value.total = s.data.data.total;
            tableData.value = s.data.data.list;

            loading.close()
        }
    ).catch(
        e => {
            ElMessage.error("网络错误")
            loading.close()
        }
    )
}

onMounted(
    () => {
        upData()
    }
)
//下方页码改变的时候
let currentPage = ref(1)
function changePage(newPage) {
    currentPage.value = newPage
    getArtilesList(newPage).then(
        s => {
            pagination.value.total = s.data.data.total;
            tableData.value = s.data.data.list;
        }
    ).catch()
}
const tableData = ref([
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

])


</script>

<style scoped>
.split-page {
    padding: 50px 0;
    display: flex;
    justify-content: center;
    margin: auto;
}

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

/* 顶部按钮样式 */
.top-tab {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 1rem;
    gap: 1rem;
    /* 增加按钮之间的间距 */
}

.top-tab .el-button {
    transition: background-color 0.3s ease, transform 0.3s ease;
    /* 添加过渡效果 */
}

.top-tab .el-button:hover {
    transform: scale(1.05);
    /* 鼠标悬浮时放大效果 */
}

.top-tab .el-button[type="success"] {
    background-color: #1E88E5;
    /* 深蓝色背景 */
    color: white;
    border: none;
}

.top-tab .el-button[type="success"]:hover {
    background-color: #0056b3;
    /* 鼠标悬浮时的深蓝色 */
}

.top-tab .el-button[type="danger"] {
    background-color: #E53935;
    /* 红色背景 */
    color: white;
    border: none;
}

.top-tab .el-button[type="danger"]:hover {
    background-color: #B71C1C;
    /* 鼠标悬浮时的深红色 */
}

/* 表格样式 */
.table {
    margin: 1rem auto;
    border-radius: 8px;
    /* 添加圆角 */
    overflow: hidden;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    /* 添加阴影效果 */
}

.table .el-table {
    border-radius: 8px;
    /* 表格圆角 */
}

.table :deep(.el-table__row:hover) {
    background-color: #BBDEFB;
    /* 鼠标悬浮时的柔和蓝色背景 */
    transition: background-color 0.3s ease;
    /* 添加过渡效果 */
}

.table :deep(.el-button) {
    transition: background-color 0.3s ease, transform 0.3s ease;
    /* 添加过渡效果 */
}

.table :deep(.el-button:hover) {
    transform: scale(1.05);
    /* 鼠标悬浮时放大效果 */
}

/* 分页样式 */
.split-page {
    padding: 50px 0;
    display: flex;
    justify-content: center;
    margin: auto;
}

.split-page :deep(.el-pagination) {
    background-color: #FFFFFF;
    /* 白色背景 */
    border-radius: 8px;
    /* 添加圆角 */
    padding: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    /* 添加阴影效果 */
}

/* 编辑弹窗样式 */
.edit-container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    gap: 1rem;
    /* 增加输入框之间的间距 */
}

.edit-container span {
    font-weight: bold;
    color: #1565C0;
    /* 深蓝色字体 */
}

.edit-container .el-input {
    border-radius: 8px;
    /* 添加圆角 */
    transition: border-color 0.3s ease;
    /* 添加过渡效果 */
}

.edit-container .el-input:focus {
    border-color: #64B5F6;
    /* 聚焦时的浅蓝色边框 */
}

.edit-container .el-button {
    background-color: #1E88E5;
    /* 深蓝色按钮背景 */
    color: white;
    border: none;
    border-radius: 8px;
    /* 添加圆角 */
    transition: background-color 0.3s ease, transform 0.3s ease;
    /* 添加过渡效果 */
}

.edit-container .el-button:hover {
    background-color: #0056b3;
    /* 鼠标悬浮时的深蓝色 */
    transform: scale(1.05);
    /* 鼠标悬浮时放大效果 */
}

/* 容器样式 */
.manage-article-container {
    margin-top: 70px;
    padding: 20px;
    background-color: #F5F5F5;
    /* 浅灰色背景 */
    border-radius: 8px;
    /* 添加圆角 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    /* 添加阴影效果 */
}
</style>