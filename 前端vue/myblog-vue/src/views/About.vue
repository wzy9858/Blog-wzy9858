<template>
    <div class="about-container">

        <!-- 上半部分 -->
        <div class="about-top">
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
.top-info {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
.about-top {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 310px;
    width: 100%;
}

.about-container {
    padding-top: 70px;
    width: 100%;
    height: 100%;
    /* height: 100%; */
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.about-content {
    margin-bottom: 3rem;
    background-color: aliceblue;
    width: 90%;
    height: 100%;
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