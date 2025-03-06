<template>
    <div class="container">
        
        
           
    
        <div class="left" >



            <!-- <h5 class="mb-2">Custom colors</h5> -->
            <el-menu active-text-color="#ffd04b" background-color="#545c64" class="el-menu-vertical-demo left-content"
                 text-color="#fff">

                <span style="padding-top: 1rem;" class="center-all">
                    <el-button type="primary" icon="DArrowLeft" circle />
                </span>

                <span class="center-all" style="padding-top: 1rem;">
                    <h1>博客管理</h1>
                </span>

                <router-link to="/manage/visitor">
                    <el-menu-item class="center-all" index="2">
                        <el-icon><icon-menu /></el-icon>
                        <span>查看访客</span>
                    </el-menu-item>
                </router-link>

                <router-link to="/manage/message">
                    <el-menu-item class="center-all" index="3">
                        <el-icon>
                            <setting />
                        </el-icon>
                        <span>查看留言</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/manage/essay">
                    <el-menu-item class="center-all" index="4">
                        <el-icon>
                            <setting />
                        </el-icon>
                        <span>管理博文</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/manage/account">
                    <el-menu-item class="center-all" index="5">
                        <el-icon>
                            <setting />
                        </el-icon>
                        <span>管理账号</span>
                    </el-menu-item>
                </router-link>
            </el-menu>

        </div>
        <div class="right">
            <router-view />
        </div>
    </div>

</template>

<script setup lang="ts">
import {
    Menu as IconMenu,
    Setting,
} from '@element-plus/icons-vue'
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';

 

//是否显示空的图片
let emptyStatus = ref(true)
const router = useRouter();
watch(
    () => router.currentRoute.value.path,
    () => {
        // console.log(`Current path changed from ${oldPath} to ${newPath}`);
        if (window.location.hash != '#/manage') {
            emptyStatus.value = false
        }
    },
    { immediate: true }
);

</script>

<style scoped>
/* 媒体查询(手机端) */
@media (max-width: 768px) {}

/* 媒体查询（电脑端） */
@media (min-width: 769px) {}

.container {
    display: flex;
}

.left {
    width: 10rem;
    /* width: 0; */
}

.left-content {
    height: 52rem;
    /* width: 0; */
    /* display: none; */
}

.right {
    width: 100%;
    height: 40rem;
}
</style>