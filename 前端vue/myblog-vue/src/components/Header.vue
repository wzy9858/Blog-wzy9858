<template>
    <div class="navbar">

        <!-- 左侧图标区域  回到首页区域-->
        <div class="left-icon">
            <v-icon class="home" name="fa-home" scale="2" />
        </div>

        <!-- 占位符区域 -->
        <div style="width: 20%;"></div>
        <!-- item区域 -->
        <div class="container-items center-all">
            <ul class="items">
                <li class="center-all item">
                    <v-icon class="archive" name="gi-archive-research" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 归档 </span>
                </li>

                <li class="center-all item">
                    <v-icon class="archive" name="gi-archive-research" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 分类 </span>
                </li>

                <li class="center-all item">
                    <v-icon class="archive" name="gi-archive-research" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 关于 </span>
                </li>
            </ul>
            <div class="phone-menu">
                <v-icon name="ri-menu-fold-fill" scale="1.8" />
            </div>

        </div>

    </div>

</template>

<script setup lang="ts">
import { ref } from 'vue';
import gsap from 'gsap';

//隐藏导航栏的方法
function hiddenNav() {
    gsap.to(".navbar", { y: -50, duration: 1 }); // 将.box元素沿x轴移动200px，持续时间为1秒
    // gsap.from(".box", { x: -200, duration: 1 }); // 将.box元素从-200px位置移动到原位置，持续时间为1秒
    //gsap.fromTo(".box", { x: -200 }, { x: 200, duration: 1 }); // 将.box元素从-200px移动到200px，持续时间为1秒
    //gsap.set(".box", { x: 200 }); // 立即将.box元素沿x轴移动到200px的位置
}
function emergeNav() {
    gsap.to(".navbar", { y: 0, duration: 1 });
}

// 定义响应式变量存储上一次滚动位置
const lastScrollTop = ref(0);
const handleScroll = () => {
    const currentScrollTop = window.pageYOffset || document.documentElement.scrollTop;
    // 判断滚动方向
    if (currentScrollTop > lastScrollTop.value) {

        hiddenNav()
        // console.log('下滑屏幕');
    } else {
        emergeNav()
        // console.log('上滑屏幕');
    }
    // 更新上一次滚动位置
    lastScrollTop.value = currentScrollTop;
};
// 组件挂载时添加滚动事件监听器
window.addEventListener('scroll', handleScroll);
// 组件卸载时移除滚动事件监听器
import { onBeforeUnmount } from 'vue';
onBeforeUnmount(() => {
    window.removeEventListener('scroll', handleScroll);
});
</script>

<style scoped>
/* 针对桌面电脑 */
@media only screen and (min-width: 1024px) {

    /* CSS规则 */
    .phone-menu {
        display: none;
    }
}

/* 针对平板 */
@media only screen and (min-width: 768px) and (max-width: 1023px) {
    /* CSS规则 */
    .phone-menu {
        display: none;
    }
}

/* 针对所有手机 */
@media only screen and (max-width: 767px) {

    /* CSS规则 */
    .items {
        display: none;
    }

    .item {
        display: none;
    }

    .container-items {
        margin-left: auto;
        margin-right: 1rem;
    }
}


.navbar {
    width: 100%;
    height: 70px;
    z-index: 1000;
    background-color: transparent;
    /* background-color: antiquewhite; */
    display: flex;
    align-items: center;

    transition: top 0.3s;
    /* 平滑过渡效果 */

    font-family: 'myfont1';
}

.items {
    list-style-type: none;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.item {
    margin-left: 40px;
}

.item:hover {
    background-color: aqua;
    border-radius: 1rem;
}



.left-icon {
    width: 20%;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
}

.left-icon .home:hover {
    background-color: aqua;
    border-radius: 1rem;
}

.container-items {
    height: 100%;
}

.phone-menu:hover {
    background-color: aqua;
    border-radius: 0.2rem;
}
</style>