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
.contain-mail {
    padding-top: 80px;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
    align-items: center;

}
</style>