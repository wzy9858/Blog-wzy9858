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

    <el-dialog v-if="cookies.get('announceFlag') != 'yes'" v-model="announce" title="" width="500" center>
        <span style="font-family: myfont1; font-size: 30px;">
            <span style="display: flex; justify-content: center;">📢 公告</span>
            
            <br>
            {{ customAiInfo.announcement}}
            
        </span>
        <template #footer>
            <div class="dialog-footer">
                <el-button type="primary" @click="announce = false">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script setup>
import Cursor from './components/Cursor.vue';
import Header from './components/Header.vue';
import Home from './views/Home.vue';

import { onMounted, ref } from 'vue';
import { http } from './ts/axios';

import { ElNotification } from 'element-plus'
import { getIpAndAdress } from './ts/axios/visitorHttp.ts';
const announce = ref(true)
// -------------

const fullPath = window.location.href;
// console.log(fullPath); // 输出: http://localhost:5173/#/register
const lastPart = fullPath.split('/').pop();
// console.log(lastPart); // 输出: register
const viewCur = ref(true) //false为不显示
//getIpAndAdress
// ---------


import { getAiContent } from './ts/axios/ai.ts';

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

// cookies.set("isAdmin", s.data.data.isAdmin, '1h')
// cookies.get("accountToken")

let customAiInfo = ref({
  "id": 1,
  "announcement": "公告",
})


onMounted(() => {



    getAiContent().then(
        s=>{
            customAiInfo.value = s.data[0]
            cookies.set("announceFlag", "yes", '0.2h')        
        }
    ).catch()


    if (lastPart == 'register') {
        viewCur.value = false
        return
    }


    // const startTime = new Date();//开始计时

    //http://localhost:8080/complete/getIpAndAdress
    // 拿到用户的ip地址 ip-city

    let ipInfo;
    if(cookies.get("announceFlag") != "yes"){
        getIpAndAdress().then(
            s => {
                ipInfo = s.data;
                console.log("发送请求成功");
                console.log(s);

                ElNotification({
                    title: '✨✨✨欢迎你',
                    message: '来自于 [' + ipInfo.split('-')[1] + '] 的朋友<br>' + "您的ip地址为: " + ipInfo.split('-')[0],
                    type: 'success',
                    dangerouslyUseHTMLString: true
                })

            }
        ).catch(
            e => {
                console.log("发送请求失败");
            }
        )
    }



    
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
            value: '#E3F2FD', // 背景色调整为浅蓝色，与整体风格一致
        },
    },
    fpsLimit: 120,
    interactivity: {
        events: {
            onClick: {
                enable: true, // 启用点击交互
                mode: 'bubble',
            },
            onHover: {
                enable: true, // 启用悬浮交互
                mode: 'repulse', // 鼠标悬浮时粒子排斥
            },
            resize: true,
        },
        modes: {
            bubble: {
                distance: 300,
                duration: 2,
                opacity: 0.8,
                size: 20, // 调整气泡大小
            },
            push: {
                quantity: 4,
            },
            repulse: {
                distance: 150, // 调整排斥距离
                duration: 0.4,
            },
        },
    },
    particles: {
        color: {
            value: '#64B5F6', // 粒子颜色调整为浅蓝色
        },
        links: {
            color: '#BBDEFB', // 粒子连线颜色调整为柔和蓝色
            distance: 150,
            enable: true,
            opacity: 0.5,
            width: 1,
        },
        move: {
            direction: 'none',
            enable: true,
            outMode: 'bounce',
            random: false,
            speed: 1.5, // 调整粒子移动速度
            straight: false,
        },
        number: {
            density: {
                enable: true,
                area: 800,
            },
            value: 100, // 增加粒子数量
        },
        opacity: {
            value: 0.7, // 调整粒子透明度
        },
        shape: {
            type: 'circle', // 将形状改为圆形
        },
        size: {
            random: true,
            value: 8, // 调整粒子大小
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
    background: linear-gradient(to right, #E3F2FD, #BBDEFB); /* 添加渐变背景，与整体风格一致 */
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
    background-color: #1E88E5; /* 深蓝色，与 Header 保持一致 */
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

.el-backtop {
    background-color: #1E88E5; /* 深蓝色，与整体风格一致 */
    border-radius: 50%;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
    transition: all 0.3s ease;
}

.el-backtop:hover {
    background-color: #64B5F6; /* 鼠标悬浮时的浅蓝色 */
    transform: scale(1.1); /* 鼠标悬浮时放大效果 */
    box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.3); /* 增强阴影效果 */
}

.v-icon {
    color: white; /* 图标颜色为白色，与按钮背景形成对比 */
    transition: color 0.3s ease;
}

.v-icon:hover {
    color: #E3F2FD; /* 鼠标悬浮时图标颜色变为浅蓝色 */
}
</style>