<template>
    <div class="article-container">
        <!-- 文章头部样式 -->
        <div class="article-header">
            <div style="padding-bottom: 1rem;">
                <span style="font-size: 30px; font-family: myfont1;">
                    一款Springboot+vue3开发的博客系统
                </span>
            </div>
            <div>
                <!-- 15px -->
                <span style="margin-right: 0.5rem; font-size: 15px;">
                    <v-icon name="px-label-alt-multiple" scale="1" />
                    标签
                </span>


                <span style="margin-right: 0.5rem; font-size: 15px;">
                    <v-icon name="gi-campfire" scale="1" />
                    114
                </span>

                <span style="margin-right: 0.5rem; font-size: 15px;">
                    <v-icon name="io-time-outline" scale="1" />
                    2024-11-28
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
        <div class="article-footer">
            <span style="font-family: myfont1; font-size: 30px;">菜鸟拯救世界のblog</span>
        </div>
    </div>

</template>

<script setup>
import { ref } from 'vue';
import { MdPreview, MdCatalog } from 'md-editor-v3';
// preview.css相比style.css少了编辑器那部分样式
import 'md-editor-v3/lib/preview.css';
const id = 'preview-only';
const text = ref('# Hello Editor \n## Hello World \n ### nihao \n #### 耳机 \n##### 大');
const scrollElement = document.documentElement;
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
    .computer-catalog{
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



.article-footer {
    width: 100%;
    height: 180px;
    background-color: aliceblue;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>