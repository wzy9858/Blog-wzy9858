<template>
    <div class="archives-container">
        <div style="width: 100%; height: 310px;display: flex;justify-content: center;align-items: center;">
            <span style="font-family: myfont1; font-size: 3rem">归档</span>
        </div>

        <div class="timeline-container">
            <!-- 菜单切换 文章归档 开发日志 -->
            <div>

            </div>

            <!-- 一个菜单盒子 可以显示文章总数 -->
            <div class="title">
                <span style="font-family: myfont1; font-size: 2rem;">共
                    <span style="font-size: 4rem;">{{ total }}</span>
                    篇文章</span>
            </div>

            <el-timeline style="max-width: 600px">

                <el-timeline-item type="primary" timestamp="2018/4/12" placement="top"
                    v-for="(item, index) in articleList" :key="index" @click="toArticle(item.id, index)">
                    <el-card>
                        <h4>{{ item.articleTitle }}</h4>
                        <p> {{ item.createdAt }}</p>
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
/* .footer {} */

.timeline-container {
    display: flex;
    flex-direction: column;
    height: 100%;
    margin-top: 40px;
}

.archives-container {
    padding-top: 70px;
    width: 100%;
    height: 100%;
    /* height: 100%; */
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.title {
    width: 100%;
    padding: 1rem;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>