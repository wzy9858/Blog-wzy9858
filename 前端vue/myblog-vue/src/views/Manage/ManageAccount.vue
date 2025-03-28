<template>
    <div class="account-manage-container">
        <div class="center-all">
            <el-button type="danger" size="small" @click="deleteAccount">
                删除所选
            </el-button>
        </div>

        <div class="table">
            <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="28" />

                <el-table-column fixed prop="account" label="账号" width="100" />
                <el-table-column prop="email" label="邮箱" width="150" />
                <el-table-column prop="nickname" label="昵称" width="90" />
                <el-table-column prop="avatarUrl" label="头像地址" width="320" />
                <el-table-column prop="isSuperAdmin" label="是否超级管理员" width="130" />
                <el-table-column prop="creationTime" label="创建时间" width="120" />
                <el-table-column prop="lastLoginTime" label="上一次登录时间" width="120" />
                <el-table-column prop="bio" label="关于我" width="100" />
                <el-table-column prop="ipAddress" label="ip地址" width="100" />
                <el-table-column prop="password" label="密码" width="100" />
                <el-table-column fixed="right" label="操作" min-width="65">
                    <template #default="{ row }">
                        <el-button type="primary" size="small" @click="accountEdit(row)">
                            编辑
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>


    </div>
    <!-- 对话框 -->
    <el-dialog v-model="handleClick" title="编辑" width="500" center>
        <div class="edit-container">
            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">账号</span>
                <el-input v-model="account.account" style="width: 160px;" placeholder="请输入账号" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">邮箱</span>
                <el-input v-model="account.email" style="width: 160px;" placeholder="请输入邮箱" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">昵称</span>
                <el-input v-model="account.nickname" style="width: 160px;" placeholder="请输入昵称" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">密码</span>
                <el-input v-model="account.password" style="width: 160px;" placeholder="请输入密码" />
            </div>

            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">头像地址</span>
                <el-input v-model="account.avatarUrl" style="width: 160px;" placeholder="请输入头像地址" />
            </div>

            <div style="padding: 0.5rem;">
                <el-button type="success" size="small" @click="saveAccount">
                    保存
                </el-button>

                <el-button type="success" size="small" @click="editAboutMeBtn">
                    About Me
                </el-button>

            </div>

        </div>

    </el-dialog>

</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAccountList } from '../../ts/axios/adminHttp';
import { getAloneAccount } from '../../ts/axios/adminHttp';
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();


const handleClick = ref(false)

import { useRouter } from 'vue-router';

let router = useRouter()
function editAboutMeBtn() {
    saveAccount()
    router.push(`/editAboutMe?id=${account.value.account}`)//还是传过去账号
}
onMounted(() => {
    if (cookies.get('isAdmin') === '1') {
        getAccountList().then(
            s => {
                tableData.value = s.data.data.list
                tableData.value.forEach(e => {
                    e.bio = "太长啦 展示不下"
                })
            }
        ).catch()
    } else {
        getAloneAccount(cookies.get('account')).then(
            s => {
                tableData.value = s.data.data.list
                tableData.value.forEach(e => {
                    e.bio = "太长啦 展示不下"
                })
            }
        ).catch()
    }




})
let account = ref({
    account: "账号",
    nickname: '昵称',
    password: '密码',
    bio: null,
    avatarUrl: '头像地址',
    ipAddress: 'ip地址',
    creationTime: "",
    lastLoginTime: "",
    email: '邮箱',
    isSuperAdmin: '是否超级管理员',
})
import { updateAccountInfo } from '../../ts/axios/adminHttp';
import { updateOrdinaryAccountInfo } from '../../ts/axios/adminHttp';
// 保存账号内容
// import { accountPinia } from '../../ts/store';
// let pinia = accountPinia()
function saveAccount() {
    // pinia.bio = account.value.bio

    account.value.bio = null
    if (cookies.get('isAdmin') === '1') {
        updateAccountInfo(account.value).then(
            s => {
                if (s.data.code == 200) {
                    ElMessage.success("保存成功")
                } else {
                    ElMessage.error("保存失败")
                }
            }
        ).catch(
            e => {
                ElMessage.error("保存失败")
            }
        )
    }else{
        updateOrdinaryAccountInfo(account.value).then(
            s => {
                if (s.data.code == 200) {
                    ElMessage.success("保存成功")
                } else {
                    ElMessage.error("保存失败")
                }
            }
        ).catch(
            e => {
                ElMessage.error("保存失败")
            }
        )
    }




}

import { deleteAccountByIds } from '../../ts/axios/adminHttp';
import { ElMessage } from 'element-plus';
//删除所选账号
function deleteAccount() {
    if (deleteId.length == 0) {
        console.log("选中为空未删除");
    } else {
        deleteAccountByIds(deleteId).then(
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

//点击编辑按钮
function accountEdit(row: any) {
    account.value = row
    handleClick.value = true
}

const tableData = ref([
    {
        account: "账号",
        nickname: '昵称',
        password: '密码',
        bio: '关于我',
        avatarUrl: '头像地址',
        ipAddress: 'ip地址',
        creationTime: "",
        lastLoginTime: "",
        email: '邮箱',
        isSuperAdmin: '是否超级管理员',
    },
])

let deleteId: any[] = []
function handleSelectionChange(selection: any[]) {
    deleteId = []
    selection.forEach((element: {
        account(account: any): unknown; visitorId: any;
    }) => {
        deleteId.push(element.account)
    });
    // console.log(deleteId);
}



</script>

<style scoped>
/* 容器样式 */
.account-manage-container {
    margin-top: 70px;
    padding: 20px;
    background-color: #F5F5F5; /* 浅灰色背景 */
    border-radius: 8px; /* 添加圆角 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
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

/* 顶部按钮样式 */
.center-all {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 1rem;
    gap: 1rem; /* 增加按钮之间的间距 */
}

.center-all .el-button {
    transition: background-color 0.3s ease, transform 0.3s ease; /* 添加过渡效果 */
}

.center-all .el-button:hover {
    transform: scale(1.05); /* 鼠标悬浮时放大效果 */
}

.center-all .el-button[type="danger"] {
    background-color: #E53935; /* 红色背景 */
    color: white;
    border: none;
}

.center-all .el-button[type="danger"]:hover {
    background-color: #B71C1C; /* 鼠标悬浮时的深红色 */
}

/* 编辑弹窗样式 */
.el-dialog {
    border-radius: 8px; /* 添加圆角 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

.edit-container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    gap: 1rem; /* 增加输入框之间的间距 */
}

.edit-container span {
    font-weight: bold;
    color: #1565C0; /* 深蓝色字体 */
}

.edit-container .el-input {
    border-radius: 8px; /* 添加圆角 */
    transition: border-color 0.3s ease; /* 添加过渡效果 */
}

.edit-container .el-input:focus {
    border-color: #64B5F6; /* 聚焦时的浅蓝色边框 */
}

.edit-container .el-button {
    background-color: #1E88E5; /* 深蓝色按钮背景 */
    color: white;
    border: none;
    border-radius: 8px; /* 添加圆角 */
    transition: background-color 0.3s ease, transform 0.3s ease; /* 添加过渡效果 */
}

.edit-container .el-button:hover {
    background-color: #0056b3; /* 鼠标悬浮时的深蓝色 */
    transform: scale(1.05); /* 鼠标悬浮时放大效果 */
}
</style>