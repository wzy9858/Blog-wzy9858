<template>
    <div :style="navbaStyle" class="navbar">

        <!-- 左侧图标区域  回到首页区域-->
        <router-link to="/" class="left-icon">
            <v-icon class="home" name="fa-home" scale="2" />
        </router-link>

        <!-- 占位符区域 -->
        <div style="width: 20%;"></div>
        <!-- item区域 -->
        <div class="container-items center-all">
            <ul class="items">
                <RouterLink to="/archives" class="center-all item">
                    <v-icon class="archive" name="px-archive" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 归档 </span>
                </RouterLink>

                <RouterLink to="/record" class="center-all item">
                    <v-icon class="archive" name="bi-battery-full" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 记录 </span>
                </RouterLink>

                <RouterLink to="/about" class="center-all item">
                    <v-icon class="archive" name="co-about-me" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 关于 </span>
                </RouterLink>
            </ul>

            <div class="phone-menu" @click="drawer = true;">
                <v-icon name="ri-menu-fold-fill" scale="1.8" />
            </div>

        </div>
    </div>

    <el-drawer v-model="drawer" title="菜鸟拯救世界のblog" :direction="direction" :size="350" style="font-family: myfont1;">
        <div class="side-container">
            <span style="height: 1rem; width: 100%; padding-bottom: 1rem;">Hi,最近还好吗?</span>
            <div class="side-menu">
                <div class="side-menu-item" @click="btn_archive()">
                    <v-icon class="archive" name="px-archive" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 归档 </span>
                </div>

                <div class="side-menu-item" @click="btn_record()">
                    <v-icon class="archive" name="bi-battery-full" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 记录 </span>
                </div>

                <div class="side-menu-item" @click="btn_about()">
                    <!-- CoAboutMe -->
                    <v-icon class="archive" name="co-about-me" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 关于 </span>
                </div>
            </div>

        </div>

    </el-drawer>

</template>

<script setup lang="ts">
import { ref } from 'vue';
import gsap from 'gsap';
import type { DrawerProps } from 'element-plus'
import { useRouter } from 'vue-router';

let router = useRouter()
// 侧边栏的按钮点击事件
function btn_archive() {
    router.push('/archives')
    drawer.value = false;
}
function btn_about() {
    router.push('/about')
    drawer.value = false;
}
function btn_record() {
    router.push('/record')
    drawer.value = false;
}


const drawer = ref(false)
const direction = ref<DrawerProps['direction']>('ltr')
//隐藏导航栏的方法
function hiddenNav() {
    gsap.to(".navbar", { y: -200, duration: 1 }); // 将.box元素沿y轴移动200px，持续时间为1秒    
}
function emergeNav() {
    gsap.to(".navbar", { y: 0, duration: 1 });
}

let navbaStyle = ref({ backgroundColor: 'transparent' });
// 定义响应式变量存储上一次滚动位置
const lastScrollTop = ref(0);
const handleScroll = () => {
    const currentScrollTop = window.pageYOffset || document.documentElement.scrollTop;
    // 判断滚动方向
    if (currentScrollTop > lastScrollTop.value) {
        hiddenNav()
        console.log('下滑屏幕');
    } else {
        console.log('上滑屏幕');

        if (window.scrollY === 0) {
            navbaStyle.value.backgroundColor = 'transparent'
            // 鼠标不能往上滑了
            navbaStyle.value.backgroundColor = 'transparent'
        } else {
            // 鼠标还能往上滑
            navbaStyle.value.backgroundColor = 'white'
        }
        emergeNav()
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

    .side-container {
        display: flex;
        /* justify-content: center; */
        align-items: center;
        flex-direction: column;
        height: 100%;
        width: 100%;
    }

    .side-menu {
        display: flex;
        /* justify-content: center; */
        align-items: center;
        flex-direction: column;
        height: 100%;
        width: 100%;
    }

    .side-menu-item {
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 0.5rem;
        width: 100%;
        /* background-color: aqua; */
    }

    .side-menu-item:hover {
        background-color: rgb(173, 164, 164);
    }


}


.navbar {
    top: 0;
    width: 100%;
    height: 70px;
    z-index: 1000;
    background-color: transparent;
    position: fixed;
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
    background-color: rgb(177, 190, 190);
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