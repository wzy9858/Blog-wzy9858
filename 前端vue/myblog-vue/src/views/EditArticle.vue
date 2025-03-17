<template>
    <div class="edit-container">
        <!-- 编辑文章的相关信息 标题 图片连接 作者等信息 -->

        <!-- <div class="edit-header">
            <div>
                <el-input v-model="inputTitle" style="width: 240px;" placeholder="请输入文章标题" />
            </div>
            <div style="margin-top: 1rem;">
                <el-input v-model="inputLabel" style="width: 240px" placeholder="请输入文章标签" />
            </div>
            <div class="btn-row" style="margin-top: 1rem; margin-bottom: 1rem;">
                <el-button type="danger" size="small" @click="handleClick">
                    保存文章
                </el-button>
            </div>
        </div> -->

        <!-- 编辑区域 -->
        <div style="width: 100%;height: 46rem;">
            <MdEditor @onUploadImg="onUploadImg" style="height: 100%;" v-model="text" @onSave="handleSave" :preview="false" />
        </div>
    </div>

    <div class="ai-btn">
        <el-button type="primary" @click="createAiContent">ai大纲</el-button>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { ElMessage } from 'element-plus';
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getArticleById, saveArticleContent } from '../ts/axios/articleHttp';
import axios from 'axios';

import { ElLoading } from 'element-plus'
// const openFullScreen2 = () => {
//   const loading = ElLoading.service({
//     lock: true,
//     text: '加载中',
//     background: 'rgba(0, 0, 0, 0.7)',
//   })
//   setTimeout(() => {
//     loading.close()
//   }, 2000)
// }


let route = useRoute();
const text = ref('# Hello Editor');
let article = ref({
    "id": 1,
    "articleContent": "Hello world",
});
// 创建ai大纲方法
async function createAiContent() {
    const loading = ElLoading.service({
    lock: true,
    text: '加载中...',
    background: 'rgba(0, 0, 0, 0.7)',
  })
    console.log("点击了ai大纲");
    console.log(text.value); // 文章内容

    try {
        const response = await axios.post('https://models.inference.ai.azure.com/chat/completions', {
            messages: [
                {
                    role: 'system',
                    content: '你是一个帮助生成文章摘要的助手,请简要生成一份摘要,要求不超过300字。', // 发送的信息
                },
                {
                    role: 'user',
                    content: text.value, // 文章内容
                }
            ],
            model: 'gpt-4o',
        }, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `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`, // 替换为你的 API Key
            },
        });
        const aiContent = response.data.choices[0].message.content;
        text.value = "> AI智能大纲<br>  "+ aiContent + "<br>" + text.value;
        loading.close()
    } catch (error) {
        loading.close()
        console.error("AI 请求失败", error);
        ElMessage.error("AI 请求失败");
    }
}

// 文章保存事件
function handleSave() {
    article.value.articleContent = text.value;
    saveArticleContent(article.value).then(
        s => {
            if (s.data.code == 200) {
                ElMessage.success("保存成功");
            } else {
                ElMessage.error("出错啦");
            }
        }
    ).catch(
        e => {
            ElMessage.error("出异常啦");
        }
    );
}

onMounted(() => {
    article.value.id = ref(route.query.id);
    getArticleById(article.value.id).then(
        s => {
            text.value = s.data.data.article.articleContent;
            article.value.articleContent = text.value;
        }
    ).catch(
        e => {
            ElMessage.error("网络错误");
        }
    );
});
// 上传图片的方法
const onUploadImg = async (files, callback) => {
  const res = await Promise.all(
    files.map((file) => {
      return new Promise((rev, rej) => {
        const form = new FormData();
        form.append('file', file);

        axios
          .post('/api/complete/img/upload', form, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          })
          .then((res) => rev(res))
          .catch((error) => rej(error));
      });
    })
  );
  callback(res.map((item) => item.data.url));
};


</script>

<style scoped>
.edit-container {
    height: 100%;
    margin-top: 70px;
}

.ai-btn {
    position: absolute;
    top: 40px;
    right: 10px;
}

/* .edit-header {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
} */
</style>