<template>
    <div class="article-footer">
        <span style="font-family: myfont1; font-size: 30px;">菜鸟拯救世界のblog</span>
    </div>

</template>

<script setup>
</script>
<style scoped>
.article-footer {
    width: 100%;
    height: 180px;
    background-color: #E3F2FD; /* 浅蓝色背景，与整体风格一致 */
    display: flex;
    justify-content: center;
    align-items: center;
    border-top: 2px solid #BBDEFB; /* 添加顶部边框 */
    transition: background-color 0.3s ease, transform 0.3s ease; /* 添加过渡效果 */
}

.article-footer:hover {
    background-color: #BBDEFB; /* 鼠标悬浮时的柔和蓝色背景 */
    transform: scale(1.01); /* 鼠标悬浮时轻微放大效果 */
}

.article-footer span {
    font-family: 'myfont1';
    font-size: 30px;
    color: #1565C0; /* 深蓝色字体，与整体风格一致 */
    transition: color 0.3s ease; /* 添加字体颜色过渡效果 */
}

.article-footer span:hover {
    color: #64B5F6; /* 鼠标悬浮时字体颜色变为浅蓝色 */
}
</style>