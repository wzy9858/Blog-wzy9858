<template>
    <div class="footer-container">

        <div class="items">
            <div class="item">
                <div class="word-box">
                    Copyright © 2024
                </div>
                <div class="number-box">
                    菜鸟拯救世界
                </div>
            </div>
            <a class="item" href="https://beian.miit.gov.cn/" style="text-decoration: none;">
                <div class="word-box">
                    豫ICP备
                </div>
                <div class="number-box" style="background-color: antiquewhite;">
                    2024093375号
                </div>
            </a>
            <div class="item">
                <div class="word-box">
                    豫公网安备
                </div>
                <div class="number-box" style="background-color: aquamarine;">
                    41160302000121
                </div>
            </div>

        </div>
    </div>
</template>

<script setup lang="ts">

</script>

<style scoped>
/* 针对桌面电脑 */
@media only screen and (min-width: 1024px) {
    .items {
        display: flex;
    }

    .footer-container {
        min-height: 70px;
        display: flex;
        align-items: flex-end;
        justify-content: center;
        -webkit-user-select: none;
        user-select: none;
    }

}

@media only screen and (max-width: 767px) {
    .footer-container {
        min-height: 140px;
        display: flex;
        align-items: flex-end;
        justify-content: center;
        -webkit-user-select: none;
        user-select: none;
    }

}



.item {
    border-radius: 8px;
    margin: 0 10px 20px 0;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 12px;
    height: 24px;
}

.items {
    /* display: flex; */
}

.word-box {
    padding: 0.4rem;
    background-color: #5d5d5d;
    color: #fff;
    border-top-left-radius: 0.41rem;
    border-bottom-left-radius: 0.41rem;
}

.number-box {
    padding: 0.4rem;
    background-color: aqua;
    border-top-right-radius: 0.41rem;
    border-bottom-right-radius: 0.41rem;

}
</style>