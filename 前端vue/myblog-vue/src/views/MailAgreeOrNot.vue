<template>
  <div class="contain-mail">


    已同意该注册申请,账号密码已通过邮件发送至申请人邮箱
    
  </div>
</template>

<script setup lang="ts">
import {agreeRegister} from '../ts/axios/adminHttp';
import { onMounted, ref } from 'vue';

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
console.log("-------");
console.log(cookies.get("isAdmin"));


const fullPath = window.location.href;

let queryParams = ref(fullPath.split('=')[1]); // 拿到了查询参数

console.log(fullPath); // 输出: http://localhost:5173/#/register

onMounted(() => {
  agreeRegister(queryParams.value);
});

</script>

<style scoped>
/* 容器样式 */
.contain-mail {
  margin-top: 80px;
  padding-top: 80px;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #F5F5F5; /* 浅灰色背景 */
  font-family: 'myfont1';
  font-size: 1.5rem;
  color: #1565C0; /* 深蓝色字体，与整体风格一致 */
  text-align: center;
  border-radius: 8px; /* 添加圆角 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  padding: 20px; /* 增加内边距 */
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* 添加过渡效果 */
}

.contain-mail:hover {
  transform: scale(1.02); /* 鼠标悬浮时放大效果 */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2); /* 鼠标悬浮时增强阴影 */
}
</style>