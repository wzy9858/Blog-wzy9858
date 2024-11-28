<template>
    <div>
        <!-- 博客首页的文字描述区域 -->
        <div class="description">
            <div class="" style="margin-bottom: 1rem;">
                <h1 style="font-family: myfont1;">菜鸟拯救世界のblog</h1>
            </div>

            <div class="typewriter">
                {{ typedText }}
            </div>
        </div>

        <!-- 博客功能描述区域 访问量 运行天数 文章数量 -->
        <div class="card">
            <div class="tip">
                <div class="tip-item" @click="num()">
                    <v-icon class="num" name="px-sort-numeric" scale="2" />
                    <!-- <h1>文章数量</h1> -->
                </div class="tip-item">

                <div class="tip-item" @click="runDay()">
                    <!-- PiCharizardShiny -->
                    <v-icon class="runDay" name="pi-charizard-shiny" scale="2" />
                    <!-- <h1>运行天数</h1> -->
                </div class="tip-item">

                <div class="tip-item" @click="vistiorNum()">
                    <!-- PiRhydon -->
                    <!-- <h1>访客数量</h1> -->
                    <v-icon class="vistiorNum" name="pi-rhydon" scale="2" />
                </div>
                <div class="tip-item" @click="addOne()">
                    <!-- GiRocketThruster -->
                    <v-icon class="addOne" name="gi-rocket-thruster" scale="2" />

                    <span style="margin-top: 3rem; font-size: large; font-weight: bold;">{{ addOneNum }}</span>
                </div>
            </div>
        </div>
        <div class="contain">

            <div class="items">
                <!-- 下面 文章的每一个内容区域 -->
                <div class="item">
                    <div class="item-image">
                        <img src="https://oss.adu88.top/blog/images/1719541484048.png" alt="">
                    </div>

                    <div class="title">
                        <h2>这是菜鸟拯救世界的博客</h2>
                    </div>

                    <div class="tab">
                        <!-- PxLabelAltMultiple -->
                        <div class="center-all" style="margin-right: 1rem;">
                            <v-icon name="px-label-alt-multiple" scale="1" />
                            标签
                        </div>
                        <div class="center-all" style="margin-right: 1rem;">
                            <!-- GiCampfire -->
                            <v-icon name="gi-campfire" scale="1" />
                            12
                        </div>
                        <div class="center-all">
                            <!-- IoTimeOutline -->
                            <v-icon name="io-time-outline" scale="1" />
                            2024-11-28
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="item-image">
                        <img src="https://file.moyublog.com/d/file/2021-02-11/595eeb99baf7dab3a91036eb26e48bf1.jpg"
                            alt="">
                    </div>

                    <div class="title">
                        <h2>这是菜鸟拯救世界的博客</h2>
                    </div>

                    <div class="tab">
                        <!-- PxLabelAltMultiple -->
                        <div class="center-all" style="margin-right: 1rem;">
                            <v-icon name="px-label-alt-multiple" scale="1" />
                            标签
                        </div>
                        <div class="center-all" style="margin-right: 1rem;">
                            <!-- GiCampfire -->
                            <v-icon name="gi-campfire" scale="1" />
                            12
                        </div>
                        <div class="center-all">
                            <!-- IoTimeOutline -->
                            <v-icon name="io-time-outline" scale="1" />
                            2024-11-28
                        </div>
                    </div>
                </div>

                <div class="split-page">
                    <el-pagination background layout="prev, pager, next" :total="20" :page-size="10" />
                    <!-- page Size 每页显示10个  -->
                </div>
            </div>

            <div class="right-container">

                <div class="one-card">
                    还没想好放神魔...
                </div>
                <div class="one-card">
                    还没想好放神魔...
                </div>
            </div>
        </div>
    </div>
    <Footer />
</template>

<script setup>
import Footer from '../components/Footer.vue';
import gsap from 'gsap';
// 文章的tip信息点击动画
function num() {
    const tl = gsap.timeline();
    tl.to(".num", { y: -15, duration: 0.5, ease: "power1.inOut" })
        .to(".num", { y: 15, duration: 0.5, ease: "power1.inOut" })
        .to(".num", { y: 0, duration: 0.5, ease: "power1.inOut" });
}

function runDay() {
    const tl = gsap.timeline();
    tl.to(".runDay", { x: -15, duration: 0.5, ease: "power1.inOut" })
        .to(".runDay", { x: 15, duration: 0.5, ease: "power1.inOut" })
        .to(".runDay", { x: 0, duration: 0.5, ease: "power1.inOut" });
}

function vistiorNum() {
    const tl = gsap.timeline();
    tl.to(".vistiorNum", { x: -15, duration: 0.5, ease: "power1.inOut" })
        .to(".vistiorNum", { x: 15, duration: 0.5, ease: "power1.inOut" })
        .to(".vistiorNum", { x: 0, duration: 0.5, ease: "power1.inOut" });
}

let addOneNum = 1;
function addOne() {
    const tl = gsap.timeline();
    tl.to(".addOne", { x: -15, y: 15, duration: 0.5, ease: "power1.inOut" })
        .to(".addOne", { x: 20, y: -20, duration: 1, ease: "bounce" })
        .to(".addOne", { x: 0, y: 0, duration: 0.5, ease: "power1.inOut" });
    addOneNum = addOneNum + 1;
}

// 打字机效果
import { ref, onMounted, onBeforeUnmount } from 'vue';
const text = "欲买桂花同载酒，终不似，少年游"; // 要显示的文本
const typedText = ref(''); // 已显示的文本
const isTyping = ref(true); // 是否正在打字
const speed = 100; // 打字速度，单位为毫秒
let intervalId; // 定时器ID

const type = () => {
    if (typedText.value.length < text.length) {
        typedText.value += text.charAt(typedText.value.length);
    } else {
        // 文本打完后，停止打字
        isTyping.value = false;
        setTimeout(() => {
            // 等待一段时间开始删除文本
            clearText();
        }, 1000); // 等待1秒后开始删除
        return;
    }
    intervalId = setTimeout(type, speed);
};

const clearText = () => {
    if (typedText.value.length > 0) {
        typedText.value = typedText.value.slice(0, -1);
    } else {
        // 文本删除完毕后，重新开始打字
        isTyping.value = true;
        typedText.value = '';
        type(); // 重新开始打字
        return;
    }
    intervalId = setTimeout(clearText, speed);
};

onMounted(() => {
    type();
});

onBeforeUnmount(() => {
    // 清除定时器
    clearTimeout(intervalId);
});
</script>
<style scoped>
/* 针对桌面电脑 */
@media only screen and (min-width: 767px) {

    /* @media only screen and (min-width: 1024px) { */
    .split-page {
        padding: 50px 0;
        display: flex;
        justify-content: center;
        margin: auto;
    }

    .right-container {
        width: 18%;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .contain {
        display: flex;
        justify-content: space-between;
        width: 80%;
        margin: auto;
        flex-wrap: wrap;
        height: 100%;
    }

    .items {
        display: flex;
        flex-wrap: wrap;
        display: flex;
        justify-content: space-between;
        width: 80%;
        height: 100%;
    }

    .item {
        background-color: aliceblue;
        width: 49%;
        height: 400px;
        border-radius: 1rem;
        border: 2px solid rgb(155, 150, 150);
        margin-bottom: 20px;
    }

    .description {
        margin-top: 70px;
        display: flex;
        flex-direction: column;
        height: 51rem;
        align-items: center;
        justify-content: center;
    }

    .tip-item {
        width: 32%;
        background-color: rgb(29, 124, 172);
        margin: 0 1rem 1rem 0rem;
        border-radius: 1rem;
        overflow: hidden;
        display: flex;
        justify-content: center;
        align-items: center;

    }
}

/* 针对所有手机 */
@media only screen and (max-width: 767px) {
    .split-page {
        padding: 50px 0;
        display: flex;
        justify-content: center;
        margin: auto;
    }

    .tip-item {
        width: 32%;
        background-color: rgb(29, 124, 172);
        margin: 0 1rem 1rem 0rem;
        border-radius: 1rem;
        overflow: hidden;

        display: flex;
        justify-content: center;
        align-items: center;
    }

    .description {
        margin-top: 70px;
        display: flex;
        flex-direction: column;
        height: 50rem;
        align-items: center;
        justify-content: center;
    }

    .right-container {
        display: none;
    }

    .item {
        cursor: pointer;
        background-color: aliceblue;
        width: 100%;
        height: 400px;
        border-radius: 1rem;
        border: 2px solid rgb(155, 150, 150);
        margin-bottom: 20px;
    }

    .contain {
        display: flex;
        width: 90%;
        margin: 0 auto;
        flex-wrap: wrap;
        height: 100%;
    }

    .items {
        display: flex;
        flex-wrap: wrap;
        display: flex;
        justify-content: space-between;
        width: 100%;
        height: 100%;
    }

}

/* 针对平板 */
/* @media only screen and (min-width: 768px) and (max-width: 1023px) {
} */


.item:hover {
    border: 2px solid rgb(231, 232, 234);
    box-shadow: 0px 0px 20px rgb(62, 109, 178);
}

.item:hover img {
    transform: scale(1.20);
    border: 2px solid rgb(231, 232, 234);
    transition: transform 0.3s ease;
}

.tip {
    width: 100%;
    display: flex;
    flex-direction: row;
    margin: 0 auto;
    height: 100px;
}



.card {
    display: flex;
    width: 80%;
    margin: auto;
    flex-wrap: wrap;
}


.item-image {
    /* display: flex;
    justify-content: center;
    align-items: center; */

    height: 70%;
    width: 100%;
    overflow: hidden;
    border-radius: 1rem;
}

.item-image img {
    height: 100%;
    width: 100%;
    object-fit: contain;
    transform: scale(1.1);
    /* background-color: aliceblue; */
}

.title {
    height: 15%;
    overflow: hidden;
    display: flex;
    align-items: center;
    padding-left: 20px;
    /* justify-content: center; */
}

.tab {
    height: 10%;
    overflow: hidden;
    display: flex;
    /* justify-content: center; */
    align-items: center;
    padding-left: 20px;
}

.one-card {
    top: 80px;
    border: 2px solid black;
    background-color: rgb(21, 239, 239);
}

.tip-item:hover {
    box-shadow: 10px 10px 10px black;
    transform: scale(1.20);
    border: 2px solid rgb(231, 232, 234);
    transition: transform 0.3s ease;
}

/* 以下是打字机的效果 */
.typewriter {
    font-family: 'Courier New', Courier, monospace;
    /* 打字机字体 */
    white-space: nowrap;
    /* 保持文本在一行内 */
    overflow: hidden;
    /* 隐藏超出部分 */
    border-right: 0.1em solid;
    /* 光标效果 */
    letter-spacing: 0.1em;
    /* 字符间距 */
    animation: blink-caret 0.75s step-end infinite;
}

.cursor {
    display: inline-block;
    margin-left: 5px;
    animation: blink-caret 0.75s step-end infinite;
}

/* 光标闪烁动画 */
@keyframes blink-caret {

    from,
    to {
        border-color: transparent
    }

    50% {
        border-color: black
    }
}
</style>