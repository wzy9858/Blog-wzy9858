<template>
    <!--  -->
    <el-affix style=" position: fixed;
    right: 20px; /* 右侧距离 */
    top: 590px;  /* 与offset保持一致 */
    z-index: 999;
    display: flex;
    flex-direction: column;
    " @click="thumb_btn">
        <v-icon name="md-thumbupalt-twotone" scale="6" class="like-btn"
            style="color: #409EFF; /* 初始颜色保持原主题色 */"></v-icon>
        <span style="font-family: myfont1;"> {{ thumb }}</span>
    </el-affix>

    <div class="article-container">

        <!-- 文章头部样式 -->

        <div class="article-header ">
            <div style="padding-bottom: 1rem;">
                <span style="font-size: 80px; font-family: myfont1;">

                    {{ article.articleTitle }}
                </span>
            </div>
            <div>
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
            <img src="../assets/gif/打招呼.gif">
            <img src="../assets/gif/加载.gif">
            <img src="../assets/gif/惊讶.gif">
        </div>

        <!-- 文章内容样式 -->

        <div class="computer" style="width: 100%;">
            <div class="article-content">
                <!-- 下面是目录 -->

                <MdCatalog class="phone-catalog" :editorId="id" :scrollElement="scrollElement" />


                <!-- 这是内容区域 -->
                <MdPreview :id="id" :modelValue="text" />
            </div>

            <MdCatalog class="computer-catalog" :editorId="id" :scrollElement="scrollElement" />
            
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

        caidanAxios(article.value.articleTitle+'-'+currentPath).then().catch();
    }
    if (thumb.value >= 66) {
        caidan.value = true
    }

}
const id = 'preview-only';
const text = ref('# Hello Word!');
const scrollElement = document.documentElement;


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
    .computer-catalog {
        display: none;
    }

    .weave {
        background-color: rgb(20, 115, 197);
        width: 100%;
        height: 100px;
        display: flex;
        justify-content: space-between;

    }

    .weave img {
        width: 20%;
    }

    .article-content {
        background-color: aliceblue;
        width: 100%;
        height: 100%;
    }

    .phone-catalog {
        background-color: antiquewhite;
        border-radius: 1rem;
        padding: 1rem;
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
    }

    .computer-catalog {
        /* width: 10%; */

        /* padding: 0.5rem; */
        background-color: antiquewhite;
        border-radius: 1rem;
        padding: 1rem;
        /* background-color: rgb(5, 201, 201); */
    }

    .weave {
        background-color: rgb(20, 115, 197);
        width: 100%;
        height: 200px;
        display: flex;
        justify-content: space-between;
    }

    .weave img {
        width: 15%;
    }

    .article-content {
        background-color: aliceblue;
        width: 70%;
        height: 100%;
    }


}


/* 针对平板 */
/* @media only screen and (min-width: 768px) and (max-width: 1023px) { */

.article-header {
    padding-top: 70px;
    height: 310px;
    /* margin-left: 10%; */
    width: 80%;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.article-container {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    background-color: aliceblue;
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

.like-btn {
    cursor: pointer;
    position: relative;
    transition: all 0.3s;
}

/* 点击效果 */
.like-btn:active {
    animation: heartBeat 0.6s ease;
    color: #ff4081 !important;
}

/* 波纹效果 */
.like-btn::after {
    content: "";
    position: absolute;
    left: 50%;
    top: 50%;
    width: 120px;
    height: 120px;
    background: rgba(255, 64, 129, 0.3);
    border-radius: 50%;
    transform: translate(-50%, -50%) scale(0);
}

.like-btn:active::after {
    animation: ripple 0.6s ease;
}

/* 悬停效果 */
.like-btn:hover {
    filter: drop-shadow(0 0 8px rgba(255, 64, 129, 0.4));
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
</style>