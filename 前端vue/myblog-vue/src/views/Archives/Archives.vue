<template>
    <div class="archives-container ">
        <div style="width: 100%; height: 310px;display: flex;justify-content: center;align-items: center;" class="animate__animated animate__zoomIn animate__delay-0.2s animate__faster animate__repeat-1">
            <span style="font-family: myfont1; font-size: 3rem">归档</span>
        </div>

        <div class="timeline-container animate__animated animate__rollIn animate__delay-0.2s animate__faster animate__repeat-1">
            <!-- 菜单切换 文章归档 开发日志 -->
            <!-- <div>

            </div> -->

            <!-- 一个菜单盒子 可以显示文章总数 -->
            <div class="title">
                <span style="font-family: myfont1; font-size: 2rem;">共
                    <span style="font-size: 4rem;">{{ total }}</span>
                    篇文章</span>
            </div>

            <el-timeline style="max-width: 600px;">

                <el-timeline-item type="primary" :timestamp="item.createdAt" placement="top"
                    v-for="(item, index) in articleList" :key="index" @click="toArticle(item.id, index)">
                    <el-card>
                        <h2>{{ item.articleTitle }}</h2>
                        <!-- <p> {{ item.createdAt }}</p> -->
                    </el-card>
                </el-timeline-item>

                <!-- <el-timeline-item type="primary" timestamp="2018/4/12" placement="top">
                    <el-card>
                        <h4>第二篇文章</h4>
                        <p>Tom committed 2018/4/12 20:46</p>
                    </el-card>
                </el-timeline-item> -->
                
            </el-timeline>
        </div>
    </div>
    <div class="footer">
        <PageFooter style="background-color: transparent;" />
    </div>
</template>

<script setup>
import PageFooter from '../../components/PageFooter.vue';
import { onMounted, ref } from 'vue';
import { getAllInfo } from '../../ts/axios/articleHttp';
import { useRouter } from 'vue-router';
// import { articleList } from '../../ts/store';
let router = useRouter();
function toArticle(id, index) {
    // console.log(id);
    router.push(`/article?id=${id}`);
}
onMounted(() => {
    getAllInfo().then(
        s => {
            total.value = s.data.data.total;
            articleList.value = s.data.data.list;
            console.log(s.data.data.list);

        }
    ).catch()
})
let total = ref(1)//文章总数

let articleList = ref([{
    "id": 3,
    "articleTitle": "第1篇文章",
    "articleContent": null,
    "createdAt": "2024-12-17 13:57:50",
    "updatedAt": "2024-12-17 13:57:50",
    "isEncrypted": 1,
    "tags": "tag1,tag3",
    "popularity": 10,
    "homeDisplayImageUrl": "",
    "isPinned": 10,
    "owner": "user3"
}])


</script>

<style scoped>
/* 整体容器样式 */
.archives-container {
    padding-top: 70px;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    background-color: #E3F2FD; /* 浅蓝色背景，与整体风格一致 */
}

/* 时间轴容器样式 */
.timeline-container {
    width: 100%;
    display: flex;
    flex-direction: column;
    height: 100%;
    margin-top: 40px;
    background-color: #FFFFFF; /* 白色背景，突出时间轴内容 */
    border-radius: 8px; /* 添加圆角 */
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
    padding: 20px;
}

/* 标题样式 */
.title {
    width: 100%;
    padding: 1rem;
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: 'myfont1';
    font-size: 1.5rem;
    color: #1565C0; /* 深蓝色字体，与整体风格一致 */
}

/* 时间轴样式 */
.el-timeline {
    max-width: 800px; /* 增加时间轴的最大宽度 */
    margin: auto;
}

.el-timeline-item {
    cursor: pointer;
    transition: transform 0.3s ease, background-color 0.3s ease;
}

.el-timeline-item:hover {
    transform: scale(1.02); /* 鼠标悬浮时放大效果 */
    /* background-color: #BBDEFB; 鼠标悬浮时的柔和蓝色背景 */
    border-radius: 8px; /* 添加圆角 */
}

/* 卡片样式 */
.el-card {
    background-color: #FFFFFF; /* 白色背景 */
    border: 1px solid #BBDEFB; /* 柔和蓝色边框 */
    border-radius: 8px; /* 添加圆角 */
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    width: 100%; /* 卡片宽度占满时间轴项 */
    max-width: 700px; /* 增加卡片的最大宽度 */
    padding: 20px; /* 增加卡片的内边距 */
    margin: 10px auto; /* 增加卡片之间的间距 */
}

.el-card:hover {
    transform: scale(1.05); /* 鼠标悬浮时放大效果 */
    box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.2); /* 鼠标悬浮时增强阴影 */
}

/* 卡片标题样式 */
.el-card h2 {
    font-size: 1.5rem; /* 增大标题字体 */
    color: #1565C0; /* 深蓝色字体 */
    font-weight: bold;
    margin: 0;
    text-align: center;
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
</style>