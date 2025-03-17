<template>
    <div class="article-container">
        <!-- 文章头部样式 -->
       
        <div class="article-header ">
            <div style="padding-bottom: 1rem;">
                <span style="font-size: 30px; font-family: myfont1;">

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
        <!-- 文章尾部样式 -->
        <PageFooter></PageFooter>

        <Footer />

    </div>


</template>

<script setup>

import Footer from '../components/Footer.vue';
import PageFooter from '../components/PageFooter.vue';
import { ref } from 'vue';
import { MdPreview, MdCatalog } from 'md-editor-v3';

import { useRoute } from 'vue-router';
import { onMounted } from 'vue';

import { getArticleById } from '../ts/axios/articleHttp';

// preview.css相比style.css少了编辑器那部分样式
import 'md-editor-v3/lib/preview.css';


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

onMounted(() => {
    let id = ref(route.query.id)
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

</style>