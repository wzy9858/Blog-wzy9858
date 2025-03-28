<template>
    <div class="home-top">
        <!-- 中间白条的颜色 板块1-->
        <div style="background-color: transparent; width: 100%;">

            <el-carousel indicator-position="outside">
                <!-- 板块2的颜色 -->
                <el-carousel-item style="background-color: #E3F2FD;" v-for="(text,index) in texts" :key="index">
                    <h3 class="carousel-text" style="font-family: myfont1; font-size: 4rem; color: #1565C0;">{{ text }}</h3>
                </el-carousel-item>
            </el-carousel>
        </div>

        <div class="link-container">

            <a class="link-item" href="https://fanyi.youdao.com/">
                <v-icon style="color: #FFFFFF;" class="archive" name="bi-translate" scale="2.6" />
                <h3 style="color: #FFFFFF;">有道翻译</h3>
            </a>

            <a class="link-item" href="https://www.123pan.com/">
                <v-icon style="color: #FFFFFF;" class="archive" name="co-icloud" scale="2.6" />
                <h3 style="color: #FFFFFF;">123云盘</h3>
            </a>

            <a class="link-item" href="https://github.com/">
                <v-icon style="color: #FFFFFF;" class="archive" name="bi-github" scale="2.6" />
                <h3 style="color: #FFFFFF;">github</h3>
            </a>

            <a class="link-item" href="https://gitee.com/">
                   <v-icon style="color: #FFFFFF;" class="archive" name="si-gitee" scale="2.6" />
                   <h3 style="color: #FFFFFF;">gitee</h3>
            </a>

            <a class="link-item" href="https://www.bilibili.com/">
                <v-icon style="color: #FFFFFF;" class="archive" name="ri-bilibili-fill" scale="2.6" />
                <h3 style="color: #FFFFFF;">bilibili</h3>
            </a>

        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';

const texts = ref([
  '欢迎来到菜鸟拯救世界の博客',
  '记录成长，分享知识',
  '代码是梦想的语言，博客是分享的桥梁',
  '致力于简洁高效的全栈开发',
  
]);

</script>

<style scoped>
.link-container {
    display: flex;
    direction: column;
}

.link-item {
    background-color: #1E88E5; /* 更柔和的蓝色 */
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: background-color 0.3s ease, transform 0.2s ease;
    border-radius: 8px; /* 添加圆角 */
    margin: 5px; /* 增加间距 */
}
.link-item:hover {
    background-color: #64B5F6; /* 鼠标悬浮时的浅蓝色 */
    transform: scale(1.05); /* 鼠标悬浮时放大效果 */
}

.link-item h3 {
    margin-top: 8px;
    font-size: 1.2rem;
    font-weight: bold;
}

.home-top {
    width: 100%;
    background-color: #F5F5F5; /* 整体背景色 */
}

@media only screen and (max-width: 767px) {
    .home-top {
        display: none;
    }
}

/* 顶部走马灯 elementplus样式 */
.el-carousel__item h3 {
    display: flex;
    justify-content: center;
    /* 水平居中 */
    align-items: center;
    /* 垂直居中 */
    color: #0D47A1; /* 更深的蓝色 */
    opacity: 0.85;
    height: 300px;
    /* 确保高度与 line-height 一致 */
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #BBDEFB; /* 更柔和的背景色 */
}

.el-carousel__item:nth-child(2n + 1) {
    background-color: #E3F2FD; /* 更浅的背景色 */
}
</style>