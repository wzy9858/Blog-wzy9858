<template>
    <div class="visitor-manage-container">

        <!-- 顶栏标签 -->
        <div class="top-tab" v-if="cookies.get('isAdmin') === '1'">
            <el-button type="danger" size="small" @click="deleteVisitorBtn">
                删除所选
            </el-button>

        </div>

        <div class="table" v-if="cookies.get('isAdmin') === '1'">
            <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="28" />
                <el-table-column fixed prop="visitorId" label="id" width="49" />
                <el-table-column prop="visitorNotes" label="访客备注" width="120" />
                <el-table-column prop="ipAddress" label="ip" width="90" />
                <el-table-column prop="geoLocation" label="地理位置" width="120" />
                <el-table-column prop="firstVisitTime" label="第一次访问时间" width="120" />
                <el-table-column prop="lastVisitTime" label="上一次访问时间" width="120" />
                <el-table-column prop="visitFrequency" label="访问次数" width="100" />
                <el-table-column prop="isBlacklisted" label="是否拉黑" width="100" />
                <el-table-column prop="accessPath" label="访问路径" width="500" />
                <el-table-column prop="userAgent" label="设备标识完整字符串" width="350" />
                <el-table-column fixed="right" label="操作" min-width="65">
                    <template #default="{ row }">
                        <el-button type="primary" size="small" @click="VisitorNoteBtn(row)">
                            备注
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div style="display: flex; justify-content: center; align-items: center; margin-top: 300px;" v-else>
            <h1 style="font-family: myfont1; font-size: 50px;">普通用户,无权访问</h1>
        </div>

    
    </div>

    <!-- 点击事件 -->
    <el-dialog v-model="handleClick" title="访客备注" width="500" center>
        <div class="center-all">
            <div style="padding: 0.5rem;">
                <span style="padding: 0.5rem;">备注</span>
                <el-input v-model="inputTitle" style="width: 160px;" placeholder="输入对该访客的备注" />
            </div>

            <div style="padding: 0.5rem;">
                <el-button type="success" size="small" @click="visitorNoteSaveBtn">
                    保存
                </el-button>
            </div>

        </div>

    </el-dialog>
</template>

<script setup>
import { ref } from 'vue';
import { onMounted } from 'vue';
const handleClick = ref(false)
const inputTitle = ref('')
import { getVisitorList } from '../../ts/axios/visitorHttp';
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

let deleteId = []
function handleSelectionChange(selection) {
    deleteId = []
    selection.forEach((element) => {
        deleteId.push(element.visitorId)
    });
    console.log(deleteId);
}


// 点击备注时的按钮
function VisitorNoteBtn(row) {
    visitor.value = row
    handleClick.value = true
}
let visitor = ref({
    visitorId: 0,
    ipAddress: 'ip地址',
    geoLocation: '地理位置',
    deviceInfo: '设备信息',
    firstVisitTime: '第一次访问时间',
    lastVisitTime: '上一次访问时间',
    visitDuration: 0,
    visitFrequency: 0,
    visitorNotes: '访客备注',
    isBlacklisted: 0,
    accessPath: '访问路径',
    userAgent: '用户代理完整字符串',
},)
import { saveVisitorNote } from '../../ts/axios/visitorHttp';
//访客备注保存按钮
// 需要拿到备注id  和 备注信息
function visitorNoteSaveBtn() {
    // console.log(inputTitle.value);//输入的备注信息
    visitor.value.visitorNotes = inputTitle.value

    // console.log(visitor.value);
    console.log(visitor);
    
    saveVisitorNote(visitor.value).then(
        s => {
            if (s.data.code) {
                ElMessage.success("保存成功")
            } else {
                ElMessage.error("出错啦")
            }

        }
    ).catch(
        e => {
            ElMessage.error("出错啦")
        }
    )

}

// import { useCookies } from "vue3-cookies";
// const { cookies } = useCookies();
// cookies.get('accountToken') == null


onMounted(() => {

    // // 如果没有cookie就不显示
    // if (cookies.get('accountToken') == null) {
    //     console.log("没有cookie");
    //     return
    // }

    getVisitorList().then(
        s => {
            // console.log(s);
            tableData.value = s.data.data.list
        }
    ).catch(
        e => {

        }
    )
})

import { ElMessage } from 'element-plus'
import { deleteVisitor } from '../../ts/axios/visitorHttp';
import { tr } from 'element-plus/es/locales.mjs';
// 删除所选
function deleteVisitorBtn() {
    // deleteId 是一个集合包含了要删除的id
    if (deleteId.length == 0) {
        console.log("选中为空未删除");
    } else {
        deleteVisitor(deleteId).then(
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
        visitorId: 0,
        ipAddress: 'ip地址',
        geoLocation: '地理位置',
        deviceInfo: '设备信息',
        firstVisitTime: '第一次访问时间',
        lastVisitTime: '上一次访问时间',
        visitDuration: 0,
        visitFrequency: 0,
        visitorNotes: '访客备注',
        isBlacklisted: 0,
        accessPath: '访问路径',
        userAgent: '用户代理完整字符串',
    },
])

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