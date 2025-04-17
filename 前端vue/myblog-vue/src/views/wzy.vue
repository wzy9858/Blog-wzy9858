<template>
    <div class="ai-settings-container"  v-if="cookies.get('isAdmin') === '1'">
        <!-- 所有文章数据 -->
        <el-card class="articles-card">
            <div class="card-header">
                <span>所有文章数据</span>


                <el-button type="primary" size="small" @click="getAllArticles" >
                    获取文章内容
                </el-button>

                  <el-button type="primary" size="small" @click="copyArticlesData" icon="el-icon-document-copy">
                    一键复制
                </el-button>
            </div>
            <el-input v-model="articleContent" type="textarea" :rows="6" readonly
                class="articles-input" />
        </el-card>

        <!-- 更改设定 -->
        <el-card class="settings-card">
            <div class="card-header">
                <span>更改设定</span>
            </div>
            <el-form :model="customAiInfo" label-width="120px" class="settings-form">
                <el-form-item label="AI设定">
                    <el-input v-model="customAiInfo.customAiStatement" placeholder="请输入AI设定内容" />
                </el-form-item>
                <el-form-item label="引用数据库">
                    <el-input v-model="customAiInfo.referenceData" placeholder="" />
                </el-form-item>
                <!-- <el-form-item label="引用数据库">
                    <el-checkbox v-model="customAiInfo.referenceData">引用数据库内容</el-checkbox>
                </el-form-item> -->

                <el-form-item label="公告内容">
                    <el-input v-model="customAiInfo.announcement" placeholder="请输入公告内容" />
                </el-form-item>
                <el-form-item label="备用数据">
                    <el-input v-model="customAiInfo.spareData" placeholder="请输入引用的数据" />
                </el-form-item>
                <el-form-item>
                    <el-button type="success" @click="saveSettings" icon="el-icon-check">
                        保存设定
                    </el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script setup>
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
import { ref } from "vue";
import { ElMessage } from "element-plus";

import { getAiContent } from "../ts/axios/ai";
import { onMounted } from 'vue';
import { saveAiContent } from "../ts/axios/ai";
onMounted(() => {
    // 这里拿到数据并并赋值给customAiInfo
    getAiContent().then(
        s => {
            // console.log(s);
            customAiInfo.value = s.data[0]

        }
    ).catch(
        e => {
            ElNotification({
                title: '失败',
                message: '请求出错啦，请联系管理员修复',
                type: 'error',
            })
        }
    )
})

let customAiInfo = ref({
    id: 1,
    customAiStatement: "ai设定",
    referenceData: "0",
    announcement: "公告内容",
    conversationData: "历史对话记录",
    spareData: "引用的数据",
});

// 一键复制功能
const copyArticlesData = () => {
    navigator.clipboard
        .writeText(customAiInfo.value.conversationData)
        .then(() => {
            ElMessage({
                message: "文章数据已复制到剪贴板！",
                type: "success",
            });
        })
        .catch(() => {
            ElMessage({
                message: "复制失败，请重试！",
                type: "error",
            });
        });
};

let articleContent = ref("文章内容")
import { getAIAllArticles } from "../ts/axios/ai";
function getAllArticles(){
    getAIAllArticles().then(
        s=>{
            articleContent.value = JSON.stringify(s.data);
        }
    ).catch()
}
// 保存设定
const saveSettings = () => {
    saveAiContent(customAiInfo.value).then(
        s => {
            ElMessage({
                message: "设定已保存！",
                type: "success",
            });

        }
    ).catch(
        e => {
            ElNotification({
                title: '失败',
                message: '保存失败啦，请联系管理员处理',
                type: 'error',
            })
        }
    )

};
</script>

<style scoped>
.ai-settings-container {

    display: flex;
    margin-top: 70px;
    flex-direction: column;
    gap: 20px;
    padding: 20px;
    background: #f5f7fa;
    border-radius: 12px;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
}

.articles-card,
.settings-card {
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
    background-color: #ffffff;
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
    font-size: 16px;
    font-weight: bold;
}

.articles-input {
    width: 100%;
    border-radius: 8px;
    font-size: 14px;
    background-color: #f9f9f9;
    padding: 10px;
    border: 1px solid #ebeef5;
}

.settings-form {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.el-form-item {
    margin-bottom: 15px;
}

.el-button {
    border-radius: 8px;
}
</style>