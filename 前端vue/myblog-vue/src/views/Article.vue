<template>
    <!--  -->
    <el-affix style=" position: fixed;
    right: 20px; /* 右侧距离 */
    top: 590px;  /* 与offset保持一致 */
    z-index: 999;
    display: flex;
    flex-direction: column;
    " @click="thumb_btn">
        <v-icon name="md-thumbupalt-twotone" scale="3" class="like-btn"
            style="color: #409EFF; /* 初始颜色保持原主题色 */"></v-icon>
        <span style="font-family: myfont1;"> {{ thumb }}</span>
    </el-affix>


    <el-affix class="hidden-phone" style="position: fixed; right: 20px; top: 500px;  z-index: 20;">
        <el-button type="primary" @click="openContent">目录开关</el-button>
    </el-affix>

    <div class="article-container">

        <!-- 文章头部样式 -->

        <div class="article-header ">
            <div style="padding-bottom: 1rem;">
                <span style="font-size: 80px; font-family: myfont1;">
                    {{ article.articleTitle }}
                </span>
            </div>

            <div class="phone-tip">
                <span style="margin-right: 0.5rem; font-size: 15px;">
                    <v-icon name="fa-user-edit" scale="1" />
                    {{ article.owner }}
                </span>

                <!-- 15px -->
                <span style="margin-right: 0.5rem; font-size: 15px;">
                    <v-icon name="px-label-alt-multiple" scale="1" />
                    {{ article.tags }}
                </span>

                <span style="margin-right: 0.5rem; font-size: 15px;">
                    <v-icon name="gi-campfire" scale="1" />
                    {{ article.popularity }}
                </span>

                <span style="margin-right: 0.5rem; font-size: 15px;">
                    <v-icon name="io-time-outline" scale="1" />
                    更新于 {{ article.updatedAt }}
                </span>

                <span style="margin-right: 0.5rem; font-size: 15px;">
                    <v-icon name="io-time-outline" scale="1" />
                    创建于 {{ article.createdAt }}
                </span>



            </div>




        </div>


        <!-- 波浪线效果 -->

        <div class="weave">

        </div>

        <!-- 文章内容样式 -->

        <div class="computer" style="width: 100%;">
            <div class="article-content">
                <!-- 下面是目录 -->

                <MdCatalog class="phone-catalog" :editorId="id" :scrollElement="scrollElement" />


                <!-- 这是内容区域 -->
                <MdPreview :id="id" :modelValue="text" />
                
            </div>

            <MdCatalog v-if="contentSwitch" class="computer-catalog" :editorId="id" :scrollElement="scrollElement" />

        </div>

        <!-- 评论系统 -->
        <div style="width: 100%;">
            <Comment />
        </div>

        <!-- 文章尾部样式 -->
        <PageFooter></PageFooter>

        <Footer />

    </div>

    <!--  彩蛋 -->
    <el-dialog v-model="caidan" title="" width="500" center>
        <span style="font-family: myfont1; font-size: 30px;">
            ✨💥【彩蛋解锁】<br>您的第66次点赞足以体现对这篇文章的喜爱,已通过邮件形式告知博主,如果这篇文章有出错的地方,或者您有新的见解,请通过邮件/注册方式联系博主。<br>
            期待与您的一同交流进步！
        </span>
        <template #footer>
            <div class="dialog-footer">
                <el-button type="primary" @click="caidan = false">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>


</template>

<script setup>
import Comment from '../components/Comment.vue';
import Footer from '../components/Footer.vue';
import PageFooter from '../components/PageFooter.vue';
import { ref } from 'vue';
import { MdPreview, MdCatalog } from 'md-editor-v3';
import { useRoute } from 'vue-router';
import { onMounted } from 'vue';

import { getArticleById } from '../ts/axios/articleHttp';
// preview.css相比style.css少了编辑器那部分样式
import 'md-editor-v3/lib/preview.css';
import { caidan as caidanAxios } from '../ts/axios/visitorHttp';
const caidan = ref(false)
let thumb = ref(0)
function thumb_btn() {
    thumb.value = thumb.value + 1
    if (thumb.value == 66) {
        const currentPath = window.location.href;
        // console.log(currentPath);
        // console.log(article.value.articleTitle);

        caidanAxios(article.value.articleTitle + '-' + currentPath).then().catch();
    }
    if (thumb.value >= 66) {
        caidan.value = true
    }

}
const id = 'preview-only';
const text = ref('# Hello Word!');
const scrollElement = document.documentElement;


// 点击就打开或者关闭目录
let contentSwitch = ref(false)
function openContent() {
    contentSwitch.value = !contentSwitch.value
}


let route = useRoute();
let article = ref({
    "id": 1,
    "articleTitle": "菜鸟拯救世界的Blog",
    "articleContent": "Hello world",
    "createdAt": "2024-12-17 13:57:50",
    "updatedAt": "2024-12-17 13:57:50",
    "isEncrypted": 1,
    "tags": "tag1,tag3",
    "popularity": 10,
    "homeDisplayImageUrl": "",
    "isPinned": 10,
    "owner": "user3"
})

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();


onMounted(() => {
    let id = ref(route.query.id)
    cookies.set("CommentArticleId", id.value, '0.1h')
    // console.log(id.value);
    getArticleById(id.value).then(
        s => {
            article.value = s.data.data.article
            // console.log(article.value);
            text.value = s.data.data.article.articleContent
        }
    ).catch(
        e => {
            ElMessage.error("网络错误")
        }
    )
})

</script>

<style scoped>
/* 针对所有手机 */
@media only screen and (max-width: 767px) {


    .phone-tip {
        display: flex;
        justify-content: center;
        flex-direction: column;

    }


    /* 确保所有内容不超出视口宽度 */
    body,
    html {
        overflow-x: hidden;
        /* 禁止横向滚动 */
    }

    .hidden-phone {
        display: none;
    }

    .article-container {
        width: 100%;
        /* 确保容器宽度为100% */
        overflow-x: hidden;
        /* 禁止横向滚动 */
    }

    .computer-catalog {
        display: none;
        /* 隐藏电脑端目录 */
    }

    .weave {
        background-color: #1565C0;
        /* 深蓝色背景 */
        width: 100%;
        height: 100px;
        display: flex;
        justify-content: space-between;
    }

    .weave img {
        width: 20%;
    }

    .article-content {
        background-color: #FFFFFF;
        /* 白色背景 */
        width: 100%;
        /* 确保内容宽度为100% */
        height: 100%;
        border-radius: 8px;
        /* 添加圆角 */
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        /* 添加阴影效果 */
        padding: 20px;
        /* 增加内边距 */
        box-sizing: border-box;
        /* 包括内边距在宽度内 */
    }

    .phone-catalog {
        width: 100%;
        /* 确保目录宽度为100% */
        background-color: #E3F2FD;
        /* 浅蓝色背景 */
        border-radius: 1rem;
        padding: 1rem;
        box-sizing: border-box;
        /* 包括内边距在宽度内 */
    }
}

/* 针对桌面电脑 */
@media only screen and (min-width: 767px) {
    .phone-catalog {
        display: none;
    }

    .computer {

        display: flex;
        justify-content: center;

        overflow: visible;
        /* 确保父容器不限制子元素的滚动 */
    }


    .computer-catalog {
        background-color: #E3F2FD;

        /* 浅蓝色背景 */
        border-radius: 1rem;
        padding: 1rem;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        /* 添加阴影效果 */


        position: fixed;
        top: 60px;
        right: 20px;
        /* 触发固定的位置 */
        z-index: 10;


    }

    .weave {
        background-color: #1565C0;
        /* 深蓝色背景 */
        width: 100%;
        height: 200px;
        display: flex;
        justify-content: center;
    }

    .weave img {
        width: 15%;
        transition: transform 0.3s ease;
        /* 添加过渡效果 */
    }

    .weave img:hover {
        transform: scale(1.1);
        /* 鼠标悬浮时放大效果 */
    }

    .article-content {
        background-color: #FFFFFF;
        /* 白色背景 */
        width: 70%;
        height: 100%;
        border-radius: 8px;
        /* 添加圆角 */
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        /* 添加阴影效果 */
        padding: 20px;
        /* 增加内边距 */
    }
}

/* 文章头部样式 */
.article-header {
    padding-top: 70px;
    height: 310px;
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    background-color: #E3F2FD;
    /* 浅蓝色背景 */
    border-radius: 8px;
    /* 添加圆角 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    /* 添加阴影效果 */
}

/* 文章容器样式 */
.article-container {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    background-color: #F5F5F5;
    /* 浅灰色背景 */
}

/* 点赞按钮样式 */
.like-btn {
    cursor: pointer;
    position: relative;
    transition: all 0.3s ease;
    color: #409EFF;
    /* 初始颜色为主题蓝色 */
}

.like-btn:hover {
    filter: drop-shadow(0 0 8px rgba(64, 158, 255, 0.6));
    /* 鼠标悬浮时添加蓝色光晕 */
}

.like-btn:active {
    animation: heartBeat 0.6s ease;
    color: #FF4081 !important;
    /* 点击后变为粉色 */
}

/* 波纹动画 */
.like-btn::after {
    content: "";
    position: absolute;
    left: 50%;
    top: 50%;
    width: 120px;
    height: 120px;
    background: rgba(255, 64, 129, 0.3);
    /* 粉色半透明波纹 */
    border-radius: 50%;
    transform: translate(-50%, -50%) scale(0);
}

.like-btn:active::after {
    animation: ripple 0.6s ease;
}

/* 评论区样式 */
.comment-container {
    width: 100%;
    background-color: #FFFFFF;
    /* 白色背景 */
    border-radius: 8px;
    /* 添加圆角 */
    padding: 20px;
    /* 增加内边距 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    /* 添加阴影效果 */
}

/* 彩蛋对话框样式 */
.el-dialog {
    border-radius: 8px;
    /* 添加圆角 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    /* 添加阴影效果 */
}

.el-dialog__header {
    background-color: #E3F2FD;
    /* 浅蓝色背景 */
    color: #1565C0;
    /* 深蓝色标题文字 */
    font-weight: bold;
}

.dialog-footer .el-button {
    border-radius: 8px;
    /* 添加圆角 */
    transition: background-color 0.3s ease, transform 0.3s ease;
    /* 添加过渡效果 */
}

.dialog-footer .el-button[type="primary"] {
    background-color: #1E88E5;
    /* 深蓝色背景 */
    color: #ffffff;
    /* 白色文字 */
}

.dialog-footer .el-button[type="primary"]:hover {
    background-color: #64B5F6;
    /* 鼠标悬浮时的浅蓝色背景 */
    transform: scale(1.05);
    /* 鼠标悬浮时放大效果 */
}

/* ------------------------------------以下均是点赞动画---------------- */
/* 点赞动画 */
@keyframes heartBeat {
    0% {
        transform: scale(1);
    }

    15% {
        transform: scale(1.3);
    }

    30% {
        transform: scale(0.9);
    }

    45% {
        transform: scale(1.2);
    }

    60% {
        transform: scale(1);
    }
}

/* 波纹动画 */
@keyframes ripple {
    to {
        transform: scale(2);
        opacity: 0;
    }
}

/* 新增数字动画 */
@keyframes numberFloat {
    0% {
        opacity: 1;
        transform: translateY(0);
    }

    100% {
        opacity: 0;
        transform: translateY(-100px);
    }
}

/* 新增烟花粒子 */
@keyframes firework {
    0% {
        opacity: 1;
        transform: translate(0, 0);
    }

    100% {
        opacity: 0;
        transform: translate(var(--x), var(--y));
    }
}

.number-effect {
    position: absolute;
    font-size: 24px;
    font-weight: bold;
    color: #ff4081;
    animation: numberFloat 1s ease-out forwards;
    pointer-events: none;
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.firework-particle {
    position: absolute;
    width: 8px;
    height: 8px;
    border-radius: 50%;
    animation: firework 0.8s ease-out forwards;
    pointer-events: none;
}

/* 波浪线效果 */
.weave {
    width: 100%;
    height: 200px;
    /* 波浪线高度 */
    position: relative;
    overflow: hidden;
    /* 隐藏超出容器的部分 */
    background-color: #1565C0;
    /* 深蓝色背景 */
}

.weave::before,
.weave::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 200%;
    height: 100%;
    background: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 300" preserveAspectRatio="none"><path fill=\'%23E3F2FD\' d=\'M 1014 264 v 122 h -808 l -172 -86 s 310.42 -22.84 402 -79 c 106 -65 154 -61 268 -12 c 107 46 195.11 5.94 275 137 z\'></path><path fill=\'%23BBDEFB\' d=\'M -302 55 s 235.27 208.25 352 159 c 128 -54 233 -98 303 -73 c 92.68 33.1 181.28 115.19 235 108 c 104.9 -14 176.52 -173.06 267 -118 c 85.61 52.09 145 123 145 123 v 74 l -1306 10 z\'></path><path fill=\'%23E3F2FD\' d=\'M -286 255 s 214 -103 338 -129 s 203 29 384 101 c 145.57 57.91 178.7 50.79 272 0 c 79 -43 301 -224 385 -63 c 53 101.63 -62 129 -62 129 l -107 84 l -1212 12 z\'></path><path fill=\'%23BBDEFB\' d=\'M -24 69 s 299.68 301.66 413 245 c 8 -4 233 2 284 42 c 17.47 13.7 172 -132 217 -174 c 54.8 -51.15 128 -90 188 -39 c 76.12 64.7 118 99 118 99 l -12 132 l -1212 12 z\'></path><path fill=\'%23E3F2FD\' d=\'M -12 201 s 70 83 194 57 s 160.29 -36.77 274 6 c 109 41 184.82 24.36 265 -15 c 55 -27 116.5 -57.69 214 4 c 49 31 95 26 95 26 l -6 151 l -1036 10 z\'></path></svg>');
    background-size: cover;
    /* 确保波浪图案覆盖整个容器 */
    animation: wave-animation 8s linear infinite;
    /* 添加动画 */
}

.weave::after {
    top: 20px;
    /* 第二层波浪稍微偏移 */
    opacity: 0.6;
    /* 增加透明度，形成层次感 */
    animation-delay: -4s;
    /* 延迟动画，制造错位效果 */
}

/* 动画关键帧 */
@keyframes wave-animation {
    0% {
        transform: translateX(0);
    }

    100% {
        transform: translateX(-50%);
    }
}

/* 图片悬浮效果 */
.weave img {
    width: 15%;
    transition: transform 0.3s ease;
    /* 添加过渡效果 */
}

.weave img:hover {
    transform: scale(1.1);
    /* 鼠标悬浮时放大效果 */
}
</style>