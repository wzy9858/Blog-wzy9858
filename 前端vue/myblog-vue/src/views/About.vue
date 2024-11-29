<template>
    <div class="about-container">

        <!-- 上半部分 -->
        <div class="about-top">
            <span style="font-family: myfont1; font-size: 3rem">菜鸟拯救世界</span>
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
import { reactive, ref } from 'vue';
import { MdPreview } from 'md-editor-v3';
// preview.css相比style.css少了编辑器那部分样式
import 'md-editor-v3/lib/preview.css';
const id = 'preview-only';
const state = reactive({
    text: '# Hello Editor \n## Hello World \n ### nihao \n #### 耳机 \n##### 大',
    theme: 'cyanosis',
});
</script>

<style scoped>
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
</style>