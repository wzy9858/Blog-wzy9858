<template>
    <div class="archives-container">
        <div style="width: 100%; height: 310px;display: flex;justify-content: center;align-items: center;">
            <span style="font-family: myfont1; font-size: 3rem">归档</span>
        </div>

        <div class="timeline-container">
            <!-- 菜单切换 文章归档 开发日志 -->
            <div>

            </div>

            <!-- 一个菜单盒子 可以显示文章总数 -->
            <div class="title">
                <span style="font-family: myfont1; font-size: 2rem;">共
                    <span style="font-size: 4rem;">66</span>
                    篇文章</span>
            </div>

            <el-timeline style="max-width: 600px">
                <el-timeline-item type="primary" timestamp="2018/4/12" placement="top">
                    <el-card>
                        <h4>第一篇文章</h4>
                        <p>Tom committed 2018/4/12 20:46</p>
                    </el-card>
                </el-timeline-item>

                <el-timeline-item type="primary" timestamp="2018/4/12" placement="top">
                    <el-card>
                        <h4>第二篇文章</h4>
                        <p>Tom committed 2018/4/12 20:46</p>
                    </el-card>
                </el-timeline-item>
                
            </el-timeline>


        </div>


    </div>
    <div class="footer">
        <PageFooter style="background-color: transparent;"/>
    </div>

</template>

<script setup>
import PageFooter from '../../components/PageFooter.vue';
</script>

<style scoped>
/* .footer {} */

.timeline-container {
    display: flex;
    flex-direction: column;
    height: 100%;
    margin-top: 40px;
}

.archives-container {
    padding-top: 70px;
    width: 100%;
    height: 100%;
    /* height: 100%; */
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.title {
    width: 100%;
    padding: 1rem;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>