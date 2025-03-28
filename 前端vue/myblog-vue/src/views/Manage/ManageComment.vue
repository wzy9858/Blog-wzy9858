<template>
    <div class="visitor-manage-container">

        <!-- 顶栏标签 -->
        <div class="top-tab" v-if="cookies.get('isAdmin') === '1'">
            <el-button type="danger" size="small" @click="deleteVisitorBtn">
                删除所选
            </el-button>
        </div>

        <div class="table" v-if="cookies.get('isAdmin') === '1'" >
            <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="28" />
                <el-table-column fixed prop="id" label="id" width="120" />
                <el-table-column prop="articleId" label="所属文章id" width="120" />
                <el-table-column prop="content" label="评论内容" width="400" />
                <el-table-column prop="userAccount" label="评论账号" width="200" />
                <el-table-column prop="createTime" label="第一次访问时间" width="220" />

            </el-table>
        </div>

        <div style="display: flex; justify-content: center; align-items: center; margin-top: 300px;" v-else>
            <h1 style="font-family: myfont1; font-size: 50px;">普通用户,无权访问</h1>
        </div>
    </div>

</template>

<script setup>
import { ref } from 'vue';
import { onMounted } from 'vue';
import { getCommentsList } from '../../ts/axios/visitorHttp';
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

let deleteId = []
function handleSelectionChange(selection) {
    deleteId = []
    selection.forEach((element) => {
        deleteId.push(element.id)
    });
    console.log(deleteId);
}

let comments = ref({
    id: 0,
    articleId: 'ip地址',
    content: '地理位置',
    userAccount: '设备信息',
    userAvatar: '第一次访问时间',
    createTime: '上一次访问时间',
    isParent: 0,
    parentCommentId: 0,
    backupComment: '备份评论',
    replies: [],
},)


onMounted(() => {
    getCommentsList().then(
        s => {
            console.log(s);
            tableData.value = s.data
        }
    ).catch(
        e => {

        }
    )
})

import { ElMessage } from 'element-plus'
import { deleteComments } from '../../ts/axios/visitorHttp';

// 删除所选
function deleteVisitorBtn() {
    // deleteId 是一个集合包含了要删除的id
    if (deleteId.length == 0) {
        console.log("选中为空未删除");
    } else {
        deleteComments(deleteId).then(
            s => {
                if (s.data.code == 200) {
                    ElMessage.success("删除成功")
                    // // 删除成功更新表格内容
                    // upData()
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



const tableData = ref([
    {
        id: 0,
        articleId: '对应文章id',
        content: '评论内容',
        userAccount: '用户名',
        userAvatar: '用户头像',
        createTime: '创建时间',
        isParent: 0,
        parentCommentId: 0,
        backupComment: '备份评论',
        replies: [],
    }
])

</script>

<style scoped>
/* 顶部按钮样式 */
.top-tab {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 1rem;
    gap: 1rem; /* 增加按钮之间的间距 */
}

.top-tab .el-button {
    transition: background-color 0.3s ease, transform 0.3s ease; /* 添加过渡效果 */
}

.top-tab .el-button:hover {
    transform: scale(1.05); /* 鼠标悬浮时放大效果 */
}

.top-tab .el-button[type="danger"] {
    background-color: #E53935; /* 红色背景 */
    color: white;
    border: none;
}

.top-tab .el-button[type="danger"]:hover {
    background-color: #B71C1C; /* 鼠标悬浮时的深红色 */
}

/* 表格样式 */
.table {
    margin: 1rem auto;
    border-radius: 8px; /* 添加圆角 */
    overflow: hidden;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

.table .el-table {
    border-radius: 8px; /* 表格圆角 */
}

.table :deep(.el-table__row:hover) {
    background-color: #BBDEFB; /* 鼠标悬浮时的柔和蓝色背景 */
    transition: background-color 0.3s ease; /* 添加过渡效果 */
}

.table :deep(.el-button) {
    transition: background-color 0.3s ease, transform 0.3s ease; /* 添加过渡效果 */
}

.table :deep(.el-button:hover) {
    transform: scale(1.05); /* 鼠标悬浮时放大效果 */
}

/* 无权限访问提示样式 */
.visitor-manage-container h1 {
    font-family: 'myfont1';
    font-size: 50px;
    color: #E53935; /* 红色字体，强调无权限提示 */
    text-align: center;
}

/* 容器样式 */
.visitor-manage-container {
    margin-top: 70px;
    padding: 20px;
    background-color: #F5F5F5; /* 浅灰色背景 */
    border-radius: 8px; /* 添加圆角 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}
</style>