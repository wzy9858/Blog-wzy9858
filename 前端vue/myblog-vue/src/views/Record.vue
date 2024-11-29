<template>
    <div class="record-container">

        <!-- 顶部的卡片 -->
        <div class="card-container">
            <div class="card-content" @click="textDace()">
                <span v-for="item, index in text" :class='"oneword" + index'>{{ item }}</span>
                <!-- 人生若只如初见，何事秋风悲画扇。 -->
            </div>
        </div>

        <!-- 这是内容展示区域 -->
        <div class="content-container">

            <div class="content-item">

                <!-- 这个是头像极其签名部分 -->
                <div class="profile">
                    <div style="">
                        <el-avatar :size="60"
                            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
                    </div>
                    <div class="name-time">
                        <span style="font-family: myfont1; font-size: 20px; margin-bottom: 0.4rem;">菜鸟拯救世界</span>
                        <span style="font-size: small;">2024-11-8</span>
                    </div>
                </div>

                <!-- 这个是内容区域 -->
                <div class="text-container">

                    <MdPreview :id="id" :modelValue="state.text" :previewTheme="state.theme" />


                </div>

                <!-- 这个是底部按钮区域 -->
                <div>

                </div>

            </div>

        </div>

    </div>
    <Footer />
</template>

<script setup>
import Footer from '../components/Footer.vue';
import gsap from 'gsap';
import { reactive, ref, onMounted } from 'vue';
import { MdPreview } from 'md-editor-v3';
// preview.css相比style.css少了编辑器那部分样式
import 'md-editor-v3/lib/preview.css';
const id = 'preview-only';
const state = reactive({
    text: '# Hello Editor \n## Hello World \n ### nihao \n #### 耳机 \n##### 大',
    theme: 'mk-cute',
});
let text = '人生若只如初见，何事秋风悲画扇。'
function textDace() {

    for (let i = 0; i < text.length; i++) {
        let wordClass = ".oneword" + i;
        const tl = gsap.timeline();
        tl.add(() => {
            tl.to(wordClass, { y: -15, duration: 0.5, ease: "power1.inOut" })
                .to(wordClass, { y: 15, duration: 0.5, ease: "power1.inOut" })
                .to(wordClass, { y: 0, duration: 0.5, ease: "power1.inOut" });
        }, i * 0.1)
    }
}

onMounted(() => {
    textDace()
})

</script>
<style scoped>
.text {
    padding: 10px 20px;
}

.text-container {
    margin-bottom: 3rem;
    padding: 15px 0px 0px 40px;
    background-color: aliceblue;

}

.name-time {
    display: flex;
    justify-content: center;
    /* align-items: center; */
    flex-direction: column;
}

.profile {
    display: flex;
    align-items: center;
    /* justify-content: center; */
}

.content-item {
    width: 90%;
    background-color: aliceblue;
}

.content-container {
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;

}

.record-container {
    padding-top: 70px;
    width: 100%;
    height: 100%;
    /* height: 100%; */
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.card-container {
    margin-top: 2rem;
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 1.8rem;
}

.card-content {
    background-color: rgb(116, 110, 166);
    width: 90%;
    height: 300px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 1rem;
    font-family: myfont1;
    font-size: 1.7rem;
}

/* 针对桌面电脑 */
@media only screen and (min-width: 767px) {
    .card-content {
        width: 70%;
    }

    .content-item {
        width: 70%;
    }


}
</style>