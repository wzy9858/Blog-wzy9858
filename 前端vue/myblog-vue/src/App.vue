<template>
     <Cursor />
    <div v-if="viewCur" class="particle-container">
        <!-- 粒子效果 -->
        <Particles id="tsparticles" :options="options" class="particle" />
        <Header class="on-up"></Header>
        <RouterView />
    </div>
    <div v-else>
        <RouterView />
    </div>
    <el-backtop :bottom="100">
        <v-icon name="co-vertical-align-top" scale="1.8" />
    </el-backtop>
</template>

<script setup>
import Cursor from './components/Cursor.vue';
import Header from './components/Header.vue';
import Home from './views/Home.vue';

import { onMounted, ref } from 'vue';
import { http } from './ts/axios';

import { ElNotification } from 'element-plus'
import { getIpAndAdress } from './ts/axios/visitorHttp.ts';

// -------------

const fullPath = window.location.href;
console.log(fullPath); // 输出: http://localhost:5173/#/register
const lastPart = fullPath.split('/').pop();
console.log(lastPart); // 输出: register
const viewCur = ref(true) //false为不显示
//getIpAndAdress
// ---------
onMounted(() => {
    if (lastPart == 'register') {
        viewCur.value = false
        return
    }
    // const startTime = new Date();//开始计时

    //http://localhost:8080/complete/getIpAndAdress
    // 拿到用户的ip地址 ip-city

    let ipInfo;
    getIpAndAdress().then(
        s => {
            ipInfo = s.data;
            console.log("发送请求成功");
            console.log(s);

            ElNotification({
                title: '✨✨✨欢迎你',
                message: '来自于 ['+ipInfo.split('-')[1]+'] 的朋友<br>' + "您的ip地址为: " + ipInfo.split('-')[0] ,
                type: 'success',
                dangerouslyUseHTMLString: true
            })

        }
    ).catch(
        e => {
            console.log("发送请求失败");
        }
    )
})
// //关闭之后
// window.addEventListener('beforeunload', function (event) {
//     //结束计时 并转化成秒
//     const endTime = new Date();
//     const elapsedTimeInMilliseconds = endTime - startTime;
//     const elapsedTimeInSeconds = elapsedTimeInMilliseconds / 1000;

//     // 退出之后计算时间并发给数据库
//     http.get('/test').then(
//         s => {
//             console.log("发送请求成功");
//         }
//     ).catch(
//         e => {
//             console.log("发送请求失败");
//         }
//     )


// });

//配置particles
const options = {
    background: {
        color: {
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

    /* background: linear-gradient(to right,#ff7e5f,#feb47b); 渐变背景 */
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