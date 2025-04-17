<template>
  <div class="super-ai-container" v-if="cookies.get('isAdmin') === '1'">
    <!-- 公告设置 -->
    <el-card class="announcement-card">
      <div class="announcement-header">
        <el-input v-model="customAiInfo.announcement" type="textarea" :rows="4" placeholder="请输入公告内容（最多100字）"
          maxlength="100" show-word-limit class="announcement-input" />
        <el-button type="primary" @click="publishAnnouncement" icon="el-icon-edit">
          发布公告
        </el-button>
      </div>
      <div class="announcement-preview">
        <el-alert v-if="customAiInfo.announcement" title="公告预览" type="info" :description="customAiInfo.announcement"
          show-icon class="announcement-alert" />
        <el-empty v-else description="暂无公告" />
      </div>
    </el-card>

    <!-- AI设定与对话框 -->
    <el-card class="ai-settings-dialog-card">
      <div class="ai-settings-header">
        <el-input v-model="customAiInfo.customAiStatement" type="textarea" :rows="4" placeholder="请输入AI设定内容"
          class="ai-setting-input" />
        <el-checkbox v-model="IfreferenceData">引用数据库内容</el-checkbox>
        <el-button type="success" @click="saveAISettings" icon="el-icon-check">
          保存设定
        </el-button>
      </div>
      <el-divider>AI 对话框</el-divider>

      <div class="ai-dialog-messages">
        <SuperAIComponent />
      </div>

      <div>
        <h1 style="display: flex; justify-content: center; margin-top: 60px;">配置信息</h1>
        <wzy />
      </div>

    </el-card>
  </div>

    <div style="display: flex; justify-content: center; align-items: center; margin-top: 300px;" v-else>
      <h1 style="font-family: myfont1; font-size: 50px;">普通用户,无权访问</h1>
    </div>

</template>

<script setup lang="ts">
import wzy from "./wzy.vue";

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
import { ref } from "vue";
import SuperAIComponent from "../components/SuperAIComponent.vue";
const announcementText = ref("");
// const aiSettingText = ref("");
const useDatabase = ref(false);
// const userMessage = ref("");
// const messages = ref<{ user: string; ai: string }[]>([]);
import { saveAiContent } from "../ts/axios/ai";
import { getAiContent } from "../ts/axios/ai";
let customAiInfo = ref({
  "id": 1,
  "customAiStatement": "ai设定",
  "referenceData": "true",
  "announcement": "公告内容",
  "conversationData": "历史对话记录",
})
let IfreferenceData = ref(true);
import { onMounted } from 'vue';
import { ElNotification } from 'element-plus'


onMounted(() => {
  // 这里拿到数据并并赋值给customAiInfo
  getAiContent().then(
    s => {
      // console.log(s);
      customAiInfo.value = s.data[0]
      if(customAiInfo.value.referenceData == "true"){
        IfreferenceData.value = true
      }else {
        IfreferenceData.value = false
      }

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

const publishAnnouncement = () => {
  // if (announcementText.value.trim()) {
  //   alert(`公告已发布：${announcementText.value}`);
  // } else {
  //   alert("公告内容不能为空！");
  // }
  saveAiContent(customAiInfo.value).then(
    s=>{
      alert(`公告已发布：${customAiInfo.value.announcement}`);
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

const saveAISettings = () => {
  alert(
    `AI设定已保存：${customAiInfo.value.customAiStatement}，引用数据库：${useDatabase.value ? "是" : "否"
    }`
  );

  saveAiContent(customAiInfo.value).then().catch(
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
.super-ai-container {
  margin-top: 70px;
  display: flex;
  flex-direction: column;
  gap: 30px;
  padding: 0 20px;
}

.announcement-card,
.ai-settings-dialog-card {
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
}

.announcement-header,
.ai-settings-header {
  display: flex;
  align-items: flex-start;
  gap: 15px;
}

.announcement-input,
.ai-setting-input,
.ai-dialog-input {
  flex: 1;
  border-radius: 8px;
}

.announcement-alert {
  margin-top: 10px;
}

.ai-dialog-content {
  display: flex;
  align-items: flex-start;
  gap: 15px;
  margin-top: 20px;
}

.ai-dialog-messages {
  /* width: 100%; */
  margin-top: 20px;
  max-height: 600px;
  overflow-y: auto;
  border-top: 1px solid #ebeef5;
  padding-top: 10px;
  background-color: #fff;
  border-radius: 8px;
}

.user-message,
.ai-message {
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-message {
  font-weight: bold;
  color: #409eff;
}

.ai-message {
  color: #67c23a;
}

.el-timeline-item__node {
  background-color: #409eff !important;
}

.el-button {
  border-radius: 8px;
}

.el-card {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.el-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}
</style>