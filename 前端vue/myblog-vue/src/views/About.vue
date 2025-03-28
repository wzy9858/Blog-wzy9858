<template>
    <div class="about-container animate__animated animate__zoomIn animate__delay-0.2s animate__faster animate__repeat-1">

        <!-- 上半部分 -->
        <div class="about-top ">
            <div class="top-info">
                <el-avatar :size="200" :src="head_img" />
                  <span style="font-family: myfont1; font-size: 3rem">{{nickname}}</span>
            </div>
          
        </div>

        <!-- 下半部分 -->
        <div class="about-content">
            <MdPreview :id="id" :modelValue="state.text" :previewTheme="state.theme" />
        </div>

        <PageFooter />

    </div>

</template>

<script setup lang="ts">
import PageFooter from '../components/PageFooter.vue';
import { reactive, onMounted,ref } from 'vue';
import { MdPreview } from 'md-editor-v3';
// preview.css相比style.css少了编辑器那部分样式
import 'md-editor-v3/lib/preview.css';

const id = 'preview-only';
const state = reactive({
    text: '# Hello Word',
    theme: 'cyanosis',
});

let head_img = ref("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png")
let nickname = ref('')
// axios请求 赋值文章内容 等信息
import { getSuperInfo } from '../ts/axios/adminHttp';
onMounted(() => {
    getSuperInfo().then(
        s => {
            head_img.value = s.data.data.admin.avatarUrl
            nickname.value = s.data.data.admin.nickname

            state.text = s.data.data.admin.bio
            console.log(s);
        }
    ).catch()
})


</script>


<style scoped>
/* 顶部信息样式 */
.top-info {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    gap: 1rem; /* 增加头像和昵称之间的间距 */
}

.top-info span {
    font-family: 'myfont1';
    font-size: 3rem;
    color: #1565C0; /* 深蓝色字体，与整体风格一致 */
    transition: color 0.3s ease; /* 添加颜色过渡效果 */
}

.top-info span:hover {
    color: #64B5F6; /* 鼠标悬浮时字体颜色变为浅蓝色 */
}

/* 头像样式 */
.el-avatar {
    border: 2px solid #BBDEFB; /* 添加柔和蓝色边框 */
    transition: transform 0.3s ease, border-color 0.3s ease; /* 添加过渡效果 */
}

.el-avatar:hover {
    transform: scale(1.1); /* 鼠标悬浮时放大效果 */
    border-color: #64B5F6; /* 鼠标悬浮时边框颜色变为浅蓝色 */
}

/* 顶部容器样式 */
.about-top {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 310px;
    width: 100%;
    background-color: #E3F2FD; /* 浅蓝色背景，与整体风格一致 */
    border-bottom: 2px solid #BBDEFB; /* 添加底部边框 */
}

/* 整体容器样式 */
.about-container {
    padding-top: 70px;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    background-color: #F5F5F5; /* 浅灰色背景，提升整体层次感 */
}

/* 内容区域样式 */
.about-content {
    margin-bottom: 3rem;
    background-color: #FFFFFF; /* 白色背景 */
    width: 90%;
    height: 100%;
    border-radius: 8px; /* 添加圆角 */
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
    padding: 20px; /* 增加内边距 */
    transition: transform 0.3s ease, box-shadow 0.3s ease; /* 添加过渡效果 */
}

.about-content:hover {
    transform: scale(1.02); /* 鼠标悬浮时放大效果 */
    box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.2); /* 鼠标悬浮时增强阴影 */
}

/* 页脚样式 */
.footer {
    width: 100%;
    background-color: #E3F2FD; /* 浅蓝色背景，与整体风格一致 */
    padding: 20px 0;
    text-align: center;
    font-family: 'myfont1';
    color: #1565C0; /* 深蓝色字体 */
}

/* 针对桌面电脑 */
@media only screen and (min-width: 767px) {
    .about-content {
        width: 75%;
    }
}

/* 针对所有手机 */
@media only screen and (max-width: 767px) {
    .about-content {
        width: 100%;
    }
}
</style>