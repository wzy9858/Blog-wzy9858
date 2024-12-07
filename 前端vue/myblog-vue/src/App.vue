<template>
    <div class="particle-container">
        <!-- 粒子效果 -->
        <Particles id="tsparticles" :options="options" class="particle" />
        <Header class="on-up"></Header>
        <RouterView />
    </div>
</template>

<script setup>
import Header from './components/Header.vue';
import Home from './views/Home.vue';

import { onMounted } from 'vue';
import { http } from './ts/axios';
onMounted(() => {
    const startTime = new Date();//开始计时
})
//关闭之后
window.addEventListener('beforeunload', function (event) {
    //结束计时 并转化成秒
    const endTime = new Date();
    const elapsedTimeInMilliseconds = endTime - startTime;
    const elapsedTimeInSeconds = elapsedTimeInMilliseconds / 1000;

    // 退出之后计算时间并发给数据库
    http.get('/test').then(
        s => {
            console.log("发送请求成功");
        }
    ).catch(
        e => {
            console.log("发送请求失败");
        }
    )
    

});






//配置particles
const options = {
    background: {
        color: {
            // value: '#0d47a1',
            value: 'aliceblue;',
        },
        // image: './image/2.jpg',
        // image: "url('~/image/2.jpg')",
        // position: "50% 50%",
        // repeat: "no-repeat",
        // size: "cover",
        // opacity: 1
    },
    fpsLimit: 120,
    interactivity: {
        events: {
            onClick: {
                enable: false,
                // mode: 'push',
                mode: 'bubble',
            },
            onHover: {
                enable: false,
                // mode: 'repulse',
                mode: 'push',
            },
            resize: true,
        },
        modes: {
            bubble: {
                distance: 400,
                duration: 2,
                opacity: 0.8,
                size: 40,
            },
            push: {
                quantity: 4,
            },
            repulse: {
                distance: 200,
                duration: 0.4,
            },
        },
    },
    particles: {
        color: {
            // value: '#ffffff',
            value: '#dddd',
        },
        // links: {
        //     color: '#dddd',
        //     // color: '#ffffff',
        //     distance: 150,
        //     enable: true,
        //     opacity: 0.5,
        //     width: 1,
        // },
        move: {
            direction: 'none',
            enable: true,
            outMode: 'bounce',
            random: false,
            speed: 1,
            straight: false,
        },
        number: {
            density: {
                enable: true,
                area: 800,
            },
            value: 80,
        },
        opacity: {
            // value: 0.5,
            value: 5,
        },
        shape: {
            // type: 'circle',
            type: 'star',
        },
        size: {
            random: true,
            value: 10,
        },
    },
    detectRetina: true,
}

</script>

<style scoped>
.particle-container {
    height: 100%;
    position: relative;
    z-index: 0;
    /* 容器的z-index设置为0 */
}

.particle {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
    /* 粒子背景在容器后面 */
}

.on-up {
    position: fixed;
    top: 0;
    z-index: 1;
}
</style>