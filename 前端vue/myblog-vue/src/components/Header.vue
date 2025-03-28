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
                    <v-icon class="archive" name="co-airbnb" scale="1.5" />
                    <span style="font-size: 1.3rem;"> AI助手 </span>
                </RouterLink>


                <RouterLink to="/about" class="center-all item">
                    <v-icon class="archive" name="md-personsearch-outlined" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 关于 </span>
                </RouterLink>


            </ul>

        </div>

        <div class="phone-menu" @click="drawer = true;">
            <v-icon class="menu" name="ri-menu-fold-fill" scale="1.8" />
        </div>

        <div class="hiddenOnPhone" style="width: 15%;"></div>



        <div v-if="cookies.get('accountToken') == null" class="container-items center-all hiddenOnPhone">
            <ul class="items">

                <li class="search-btn hiddenOnPhone item" @click="searchDialog = true" style="margin-right: 2rem;">
                    <v-icon name="md-managesearch-twotone" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 搜索 </span>
                </li>

                <li class="center-all item" @click="login()" style="margin-right: 4rem;">
                    <v-icon class="archive" name="hi-login" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 登录 </span>
                </li>
            </ul>
        </div>

        <!-- 登录成功后的头像显示 --电脑端的 -->
        <div v-else class="hiddenOnPhone center-all">

             <div class="search-btn hiddenOnPhone item" @click="searchDialog = true" style="margin-right: 2rem;">
                        <v-icon name="md-managesearch-twotone" scale="1.8" />
                        <span style="font-size: 1.3rem;"> 搜索 </span>
                    </div>
            <el-dropdown>
                <span>
                    <div class="center-all" style="width: 250px;">
                        <el-avatar :src="head_img" />
                        <span style="margin-left: 0.2rem; ">{{ nickname }}</span>
                    </div>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="computer_articleManage">文章管理</el-dropdown-item>
                        <el-dropdown-item @click="computer_visitorManage">访客管理</el-dropdown-item>
                        <el-dropdown-item @click="computer_accountManage">账号管理</el-dropdown-item>

                        <el-dropdown-item @click="computer_commentManage">评论管理</el-dropdown-item>

                        <el-dropdown-item @click="computer_panel">仪表盘</el-dropdown-item>
                        <el-dropdown-item @click="quitlogin">退出登录</el-dropdown-item>

                        <!-- <el-dropdown-item>Action 2</el-dropdown-item>
                        <el-dropdown-item>Action 3</el-dropdown-item>
                        <el-dropdown-item disabled>Action 4</el-dropdown-item>
                        <el-dropdown-item divided>Action 5</el-dropdown-item> -->
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>

    </div>



    <!-- 手机端侧边栏 -->
    <el-drawer v-model="drawer" title="菜鸟拯救世界のblog" :direction="direction" :size="350" style="font-family: myfont1;">
        <div class="side-container">

            <div v-if="cookies.get('accountToken') != null" class="center-all" style="margin-bottom: 3rem;">
                <el-dropdown>
                    <span>
                        <div class="center-all">
                            <el-avatar :src="head_img" />
                            <span style="margin-left: 0.2rem;">{{ nickname }}</span>
                        </div>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item @click="quitlogin">退出登录</el-dropdown-item>
                            <!-- <el-dropdown-item>Action 2</el-dropdown-item>
                        <el-dropdown-item>Action 3</el-dropdown-item>
                        <el-dropdown-item disabled>Action 4</el-dropdown-item>
                        <el-dropdown-item divided>Action 5</el-dropdown-item> -->
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
            <span style="height: 1rem; width: 100%; padding-bottom: 1rem;">Hi,最近还好吗?</span>

            <div class="side-menu">
                <div class="side-menu-item" @click="btn_archive()">
                    <v-icon class="archive" name="px-archive" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 归档 </span>
                </div>

                <div class="side-menu-item" @click="btn_record()">
                    <v-icon class="archive" name="co-airbnb" scale="1.8" />
                    <span style="font-size: 1.3rem;"> AI助手 </span>
                </div>

                <div class="side-menu-item" @click="btn_about()">
                    <!-- CoAboutMe -->
                    <v-icon class="archive" name="md-personsearch-outlined" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 关于 </span>
                </div>

                <div v-if="cookies.get('accountToken') == null" class="side-menu-item" @click="login()">
                    <v-icon class="archive" name="hi-login" scale="1.8" />
                    <span style="font-size: 1.3rem;"> 登录 </span>
                </div>

                <div v-else class="manage">
                    <el-menu>
                        <el-sub-menu index="1">
                            <template #title>
                                <v-icon class="io-setting-outline" name="io-settings-outline" scale="1.5" />
                                <span style="font-size: 1.3rem;"> 管理 </span>
                                <!-- <el-icon>
                                        <location />
                                    </el-icon>
                                    <span>Navigator One</span> -->
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="1-1" @click="articleManage()">
                                    <v-icon class="archive" name="hi-login" scale="1.4" />
                                    <span style="font-size: 1.3rem;"> 文章 </span>
                                </el-menu-item>
                                <el-menu-item index="1-2" @click="visitorManage()">
                                    <v-icon class="archive" name="hi-login" scale="1.4" />
                                    <span style="font-size: 1.3rem;"> 访客 </span>
                                </el-menu-item>

                                <el-menu-item index="1-3" @click="accountManage()">
                                    <v-icon class="archive" name="hi-login" scale="1.4" />
                                    <span style="font-size: 1.3rem;"> 账号 </span>
                                </el-menu-item>

                                <el-menu-item index="1-4" @click="commentManage()">
                                    <v-icon class="archive" name="hi-login" scale="1.4" />
                                    <span style="font-size: 1.3rem;"> 评论 </span>
                                </el-menu-item>

                            </el-menu-item-group>
                        </el-sub-menu>
                    </el-menu>

                </div>

            </div>
        </div>
    </el-drawer>



    <!-- 用户登录对话框 -->
    <el-dialog class="dialog-width" v-model="dialogVisible">
        <div class="login-container">
            <div class="login-header">
                Blog
            </div>
            <div class="login-form">
                <el-tabs class="demo-tabs" v-model="activeName" style="width: 100%;display: flex;align-items: center;">
                    <el-tab-pane label="登录" name="first" style="width: 100%;">
                        <el-form style="max-width: 600px;width: 100%;" label-width="auto">
                            <el-form-item style="width: 100%;">
                                <el-input class="input-width" v-model="inputUserName" placeholder="请输入邮箱" />
                            </el-form-item>

                            <el-form-item>
                                <el-input v-model="inputPassWord" placeholder="请输入密码" type="password" />
                            </el-form-item>

                            <div class="center-all" style="width: 100%;">
                                <el-button type="primary" style="width: 100%;" @click="accountLogin">登录</el-button>
                            </div>

                        </el-form>
                    </el-tab-pane>

                    <el-tab-pane label="注册" name="second" style="width: 100%;">
                        <el-form style="max-width: 600px;width: 100%;" label-width="auto">
                            <el-form-item style="width: 100%;">
                                <el-input class="input-width" v-model="inputUserName" placeholder="请输入邮箱" />
                            </el-form-item>

                            <el-form-item>
                                <el-input v-model="inputPassWord" placeholder="请输入密码" type="password" />
                            </el-form-item>

                            <div class="center-all" style="width: 100%;">
                                <el-button type="success" style="width: 100%;" @click="accountSignUp">注册</el-button>
                            </div>

                        </el-form>
                    </el-tab-pane>
                </el-tabs>
            </div>


        </div>
    </el-dialog>


    <!-- 搜索框 -->
    <el-dialog v-model="searchDialog" :modal="false">
        <!-- <span>搜索</span> -->
        <template #footer>
            <div>
                <Search></Search>
            </div>
        </template>
    </el-dialog>

</template>


<script setup lang="ts">
import Search from './Search.vue'
import { ref, onMounted } from 'vue';
import gsap from 'gsap';
import type { DrawerProps } from 'element-plus'
import { useRouter } from 'vue-router';

let dialogVisible = ref(false)
const searchDialog = ref(false)
let inputUserName = ref('')
let inputPassWord = ref('')
import { ElMessage } from 'element-plus'
import { accountLogin as userLogin } from '../ts/axios/adminHttp';
import { accountRegister as userRegister } from '../ts/axios/adminHttp';

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
let head_img = ref("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png")
let nickname = ref('')
import { getSuperInfo } from '../ts/axios/adminHttp';

onMounted(() => {
    
    if (cookies.get("accountToken") != null) {
        // head_img = s.data.data.avatarUrl
        // getSuperInfo().then(
        //     s => {
        //         head_img.value = s.data.data.admin.avatarUrl
        //         nickname.value = s.data.data.admin.nickname

        //     }
        // ).catch()

        nickname.value = cookies.get("account")
        head_img.value = cookies.get("accountImgUrl")

    }
})
import { ElNotification } from 'element-plus'
// 账号注册函数
function accountSignUp() {

    userRegister(inputUserName.value, inputPassWord.value).then(
        s => {
            console.log(s);
            if (s.data.code != 404) {
                dialogVisible.value = false//注册成功关闭对话框


                ElNotification({
                    title: '🎈等待',
                    message: '✨审核结果将会以邮件形式告知',
                    type: 'info',
                })

            } else {
                ElNotification({
                    title: '失败',
                    message: '😭注册失败啦',
                    type: 'error',
                })
            }
        }
    ).catch(
        e => {
            ElMessage.error("网络出错了")
        }
    )

    ElNotification({
        title: '🎈等待',
        message: '✨已提交注册申请，请等待管理员审核',
        type: 'info',
    })



    // console.log(inputUserName.value, inputPassWord.value);
    // // ElNotification({
    // //     title: '成功',
    // //     message: '注册成功啦',
    // //     type: 'success',
    // // })

    // ElNotification({
    //     title: '失败',
    //     message: '😭注册功能暂未开放',
    //     type: 'error',
    // })

}

function accountLogin() {
    userLogin(inputUserName.value, inputPassWord.value).then(
        s => {
            // console.log(s);
            if (s.data.code != 404) {
                dialogVisible.value = false//登录成功关闭对话框

                ElMessage.success("✨登录成功啦✨")
                head_img.value = s.data.data.avatarUrl
                nickname.value = s.data.data.nickname

                // console.log("---------=======-------");
                // console.log(s.data.data.isAdmin);
                cookies.set("isAdmin", s.data.data.isAdmin, '1h')
                cookies.set("accountToken", s.data.data.token, '1h')
                cookies.set("account", inputUserName.value, '1h')
                cookies.set("accountImgUrl", s.data.data.avatarUrl, '1h')// 存储一下头像


                // 如果登录成功看要看一下是不是管理员用户 普通用户一些功能就不显示


                window.location.reload();
            } else {
                // ElMessage.error("账号或密码错误")

                ElNotification({
                    title: '错误',
                    message: '☠️账号或密码错误',
                    type: 'error',
                })
            }
        }
    ).catch(
        e => {
            ElMessage.error("网络出错了")
        }
    )

}

// 注销登录的方法
function quitlogin() {

    cookies.remove("accountToken");
    cookies.remove("isAdmin");
    cookies.remove("account");
    router.push('/home');
    // window.location.reload();
}


// 电脑端 登录成功后文章 访客 账号管理的点击事件
function computer_articleManage() {
    router.push('/articleManage')
}
function computer_visitorManage() {
    router.push('/visitorManage')
}
function computer_accountManage() {
    router.push('/accountManage')
}
function computer_commentManage() {
    router.push('/commentManage')
}
function computer_panel() {
    router.push('/panel')
}


let activeName = ref('first')
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

function login() {
    dialogVisible.value = true
    drawer.value = false;
}
//侧边栏的按钮点击事件 中的管理按钮的一些点击事件
function articleManage() {
    router.push('/articleManage')
    drawer.value = false;
}
function visitorManage() {
    router.push('/visitorManage')
    drawer.value = false;

}
function accountManage() {
    router.push('/accountManage')
    drawer.value = false;
}

function commentManage() {
    router.push('/commentManage')
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
        // console.log('下滑屏幕');
    } else {
        // console.log('上滑屏幕');

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
/* 针对桌面电脑和平板 */
@media only screen and (min-width: 768px) {
    .phone-menu {
        display: none;
    }

    .input-width {
        width: 380px;
    }

    .login-container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100%;
        width: 100%;
        flex-direction: column;
    }

    .dialog-width {
        width: 100%;
    }
}

/* 针对所有手机 */
@media only screen and (max-width: 767px) {
    .search-btn:hover {
        display: none;
    }

    .dialog-width {
        width: 100%;
    }

    .login-container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100%;
        flex-direction: column;
    }

    .hiddenOnPhone {
        display: none;
    }

    .input-width {
        width: 100%;
    }

    .phone-menu {
        width: 20%;
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
    }

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
        align-items: center;
        flex-direction: column;
        height: 100%;
        width: 100%;
    }

    .side-menu {
        display: flex;
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
        transition: background-color 0.3s ease;
    }

    .side-menu-item:hover {
        background-color: #64B5F6; /* 鼠标悬浮时的浅蓝色 */
        color: white; /* 悬浮时字体颜色变为白色 */
        border-radius: 8px; /* 添加圆角 */
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
    transition: top 0.3s, background-color 0.3s ease;
    font-family: 'myfont1';
}

.items {
    list-style-type: none;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.search-btn {
    margin-left: 40px;
    color: black;
    transition: background-color 0.3s ease, color 0.3s ease;
}

.search-btn:hover {
    background-color: #64B5F6; /* 鼠标悬浮时的浅蓝色 */
    color: white; /* 悬浮时字体颜色变为白色 */
    border-radius: 8px; /* 添加圆角 */
}

.item {
    width: 90px;
    margin-left: 40px;
    color: black;
    transition: background-color 0.3s ease, color 0.3s ease;
}

.item:hover {
    background-color: #BBDEFB; /* 鼠标悬浮时的柔和蓝色 */
    color: #1565C0; /* 悬浮时字体颜色变为深蓝色 */
    border-radius: 8px; /* 添加圆角 */
}

.left-icon {
    width: 20%;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
}

.left-icon .home:hover {
    background-color: #64B5F6; /* 鼠标悬浮时的浅蓝色 */
    border-radius: 8px; /* 添加圆角 */
}

.container-items {
    height: 100%;
}

.phone-menu .menu:hover {
    background-color: #64B5F6; /* 鼠标悬浮时的浅蓝色 */
    border-radius: 8px; /* 添加圆角 */
}

.login-header {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    font-family: myfont1;
    font-size: 50px;
    margin-bottom: 1rem;
}

.login-form {
    width: 100%;
}
</style>