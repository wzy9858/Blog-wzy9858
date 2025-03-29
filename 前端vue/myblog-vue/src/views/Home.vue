<template>
  <div>
    <!-- 博客首页的文字描述区域 -->
    <div class="description animate__animated animate__rotateIn animate__delay-0.3s animate__faster animate__repeat-1">
      <div style="margin-bottom: 1rem;">
        <div class="top-info">
          <el-avatar :size="200" :src="head_img" />
          <span style="font-family: myfont1; font-size: 2rem;margin-top: 0.5rem;">{{ nickname }}</span>
        </div>
      </div>

      <div class="typewriter">
        {{ typedText }}
      </div>
    </div>

    <!-- 改成超链接形式 -->
    <div class="app-link-container">


    </div>


    <!-- 博客功能描述区域 访问量 运行天数 文章数量  已经改成超连接形式-->
    <!-- <div class="card">
      <div class="tip">
        <div class="tip-item" @click="num()">
          <v-icon class="num" name="px-sort-numeric" scale="2" />
        </div>

        <div class="tip-item" @click="runDay()">
          <v-icon class="runDay" name="pi-charizard-shiny" scale="2" />
        </div>

        <div class="tip-item" @click="vistiorNum()">
          <v-icon class="vistiorNum" name="pi-rhydon" scale="2" />
        </div>

        <div class="tip-item" @click="addOne()">
          <v-icon class="addOne" name="gi-rocket-thruster" scale="2" />
          <span style="margin-top: 3rem; font-size: large; font-weight: bold;">{{ addOneNum }}</span>
        </div>
      </div>
    </div> -->


    <!-- 放一些超链接-快捷键 -->
    <div class="top-container">
      <Home_Top />
    </div>




    <div class="contain">

      <div class="items">
        <div class="hidden-phone" style="width: 100%;  margin-bottom: 20px;">
          <Home_Top_category @triggerMethodA="parentMethodA" />
        </div>

        <div v-animate-onscroll.repeat="{ down: 'animated animate__bounceIn' }" class="item"
          v-for="(item, index) in articleList" :key="index" @click="toArticle(item.id, index)">
          <div class="item-image">
            <img v-if="item.homeDisplayImageUrl != ''" :src="item.homeDisplayImageUrl" alt="">
            <img v-else src="../assets/img/img_load_fail.png" alt="">
          </div>

          <div class="title">
            <h2>{{ item.articleTitle }}</h2>
          </div>

          <div class="tab">
            <!-- 这里放文章作者 -->
            <div class="center-all" style="margin-right: 1rem;">
              <v-icon name="fa-user-edit" scale="1" />
              {{ item.owner }}
            </div>
          <!-- 文章标签 -->
            <div class="center-all" style="margin-right: 1rem;">
              <v-icon name="px-label-alt-multiple" scale="1" />
              {{ item.tags }}
            </div>
            <div class="center-all" style="margin-right: 1rem;">
              <v-icon name="gi-campfire" scale="1" />
              {{ item.popularity }}
            </div>
            <div class="center-all">
              <v-icon name="io-time-outline" scale="1" />
              {{ item.createdAt }}
            </div>
          </div>
        </div>

        <div class="split-page">
          <el-pagination background layout="prev, pager, next" :total="pagination.total"
            :page-size="pagination.pageSize" @current-change="changePage" />
        </div>
      </div>


      <div class="right-container">
        <homeChart />

        <div style="margin-top: 30px;">
          <el-button style="background-color: #1565C0;" type="info" @click="centerDialogVisible = true">
            <v-icon name="fc-feedback" scale="1.3" />
            bug反馈
          </el-button>
        </div>
        <!-- bug反馈对话框 -->
        <el-dialog v-model="centerDialogVisible" title="问题反馈" width="500" align-center>
          <textarea v-model="userFeedback" placeholder="请输入你要反馈的问题" style="width: 450px; height: 100px;">
          </textarea>
          <textarea v-model="userFeedbackContect" placeholder="你的联系方式" style="width: 450px;">

          </textarea>


          <template #footer>
            <div class="dialog-footer">
              <el-button @click="centerDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="sendFeedbackMail">
                发送
              </el-button>
            </div>
          </template>
        </el-dialog>

      </div>
    </div>
  </div>
  <Footer />
</template>

<script setup>

import Home_Top_category from '../components/Home_Top_category.vue';
import Home_Top from '../components/Home_Top.vue';
import homeChart from '../components/echarts/home-chart.vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { getArtilesList } from '../ts/axios/articleHttp';
import { getSuperInfo } from '../ts/axios/adminHttp';
import { sendFeedback } from '../ts/axios/visitorHttp';
import Footer from '../components/Footer.vue';
import gsap from 'gsap';
import { getIpAndAdress } from '../ts/axios/visitorHttp';
const userFeedback = ref('');
const userFeedbackContect = ref('')
let router = useRouter();
let head_img = ref("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
let nickname = ref('');
let centerDialogVisible = ref(false)


import { getAllArticlesByTag } from '../ts/axios/visitorHttp';
const parentMethodA = (payload) => {
  // console.log('执行方法A', payload.params)
  if (payload != '全部') { // 不等于全部 说明选择了其他
    // console.log("true----");

    getAllArticlesByTag(payload).then(
      s => {
        pagination.value.total = s.data.data.total;
        articleList.value = s.data.data.list;
      }
    ).catch(
      e => {
        ElMessage.error("网络出错啦!");
      }
    )
  } else {
    getArtilesList(1).then(s => {
      pagination.value.total = s.data.data.total;
      articleList.value = s.data.data.list;
    }).catch(e => {
      ElMessage.error("网络错误");
    });
  }
  // console.log('执行方法A', payload)

}


import { ElLoading } from 'element-plus'
function sendFeedbackMail() {
  console.log("input=" + userFeedback.value);// 用户输入的内容
  console.log("联系方式=" + userFeedbackContect.value);// 用户的联系方式

  if(userFeedback.value == '' || userFeedbackContect.value == ''){
    ElMessage.info("内容/联系方式不不能为空")

    return
  }

  const loading = ElLoading.service({
    lock: true,
    text: '🎈正在发送邮件...',
    background: 'rgba(0, 0, 0, 0.7)',
  })

  // centerDialogVisible.value = false;//点击发送就关闭
  sendFeedback(userFeedback.value + "-" + userFeedbackContect.value).then(
    s => {
      ElMessage.success("🎈已成功发送邮件")
      userFeedback.value = ''
      userFeedbackContect.value = ''
      centerDialogVisible.value = false;//关闭弹窗
      loading.close()
    }
  ).catch(
    e => {
      ElMessage.error("网络出错啦!")
      loading.close()
    }
  );

}
function toArticle(id, index) {
  router.push(`/article?id=${id}`);
}

onMounted(() => {
  getArtilesList(1).then(s => {
    pagination.value.total = s.data.data.total;
    articleList.value = s.data.data.list;
  }).catch(e => {
    ElMessage.error("网络错误");
  });
  //获得超级管理员的相关信息
  getSuperInfo().then(s => {
    head_img.value = s.data.data.admin.avatarUrl;
    nickname.value = s.data.data.admin.nickname;
  }).catch();

  let ipInfo;
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
});

let articleList = ref([{
  "id": 3,
  "articleTitle": "第1篇文章",
  "articleContent": null,
  "createdAt": "2024-12-17 13:57:50",
  "updatedAt": "2024-12-17 13:57:50",
  "isEncrypted": 1,
  "tags": "tag1,tag3",
  "popularity": 10,
  "homeDisplayImageUrl": "",
  "isPinned": 10,
  "owner": "user3"
}]);

let pagination = ref({
  total: 20,
  pageSize: 6,
  currentPage: 1
});



function changePage(newPage) {
  getArtilesList(newPage).then(s => {
    pagination.value.total = s.data.data.total;
    articleList.value = s.data.data.list;
  }).catch();
}

function num() {
  const tl = gsap.timeline();
  tl.to(".num", { y: -15, duration: 0.5, ease: "power1.inOut" })
    .to(".num", { y: 15, duration: 0.5, ease: "power1.inOut" })
    .to(".num", { y: 0, duration: 0.5, ease: "power1.inOut" });
}

function runDay() {
  const tl = gsap.timeline();
  tl.to(".runDay", { x: -15, duration: 0.5, ease: "power1.inOut" })
    .to(".runDay", { x: 15, duration: 0.5, ease: "power1.inOut" })
    .to(".runDay", { x: 0, duration: 0.5, ease: "power1.inOut" });
}

function vistiorNum() {
  const tl = gsap.timeline();
  tl.to(".vistiorNum", { x: -15, duration: 0.5, ease: "power1.inOut" })
    .to(".vistiorNum", { x: 15, duration: 0.5, ease: "power1.inOut" })
    .to(".vistiorNum", { x: 0, duration: 0.5, ease: "power1.inOut" });
}

let addOneNum = 1;

function addOne() {
  const tl = gsap.timeline();
  tl.to(".addOne", { x: -15, y: 15, duration: 0.5, ease: "power1.inOut" })
    .to(".addOne", { x: 20, y: -20, duration: 1, ease: "bounce" })
    .to(".addOne", { x: 0, y: 0, duration: 0.5, ease: "power1.inOut" });
  addOneNum = addOneNum + 1;
}

const text = "欲买桂花同载酒，终不似，少年游！";
const typedText = ref('');
const isTyping = ref(true);
const speed = 100;
let intervalId;

const type = () => {
  if (typedText.value.length < text.length) {
    typedText.value += text.charAt(typedText.value.length);
  } else {
    isTyping.value = false;
    setTimeout(() => {
      clearText();
    }, 1000);
    return;
  }
  intervalId = setTimeout(type, speed);
};

const clearText = () => {
  if (typedText.value.length > 0) {
    typedText.value = typedText.value.slice(0, -1);
  } else {
    isTyping.value = true;
    typedText.value = '';
    type();
    return;
  }
  intervalId = setTimeout(clearText, speed);
};

type();

onBeforeUnmount(() => {
  clearTimeout(intervalId);
});
</script>

<style scoped>
/* 顶部的div */
.top-container {
  background-color: #1E88E5;
  /* 更柔和的蓝色，与 Home_Top 保持一致 */
  display: flex;
  width: 80%;
  margin: auto;
  margin-bottom: 30px;
  flex-wrap: wrap;
  border-radius: 8px;
  /* 添加圆角 */
}

/* 针对桌面电脑 */
@media only screen and (min-width: 767px) {
  .split-page {
    padding: 50px 0;
    display: flex;
    justify-content: center;
    margin: auto;
  }

  .right-container {
    width: 18%;
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .contain {
    display: flex;
    justify-content: space-between;
    width: 80%;
    margin: auto;
    flex-wrap: wrap;
    height: 100%;
  }

  .items {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    width: 80%;
    height: 100%;
  }

  .item {
    background-color: #E3F2FD;
    /* 浅蓝色，与 Home_Top_category 保持一致 */
    width: 49%;
    height: 400px;
    border-radius: 1rem;
    border: 2px solid #BBDEFB;
    /* 边框颜色更柔和 */
    margin-bottom: 20px;
    transition: all 0.3s ease;
  }

  .item:hover {
    border: 2px solid #64B5F6;
    /* 鼠标悬浮时的浅蓝色边框 */
    box-shadow: 0px 0px 20px rgba(62, 109, 178, 0.5);
    /* 添加阴影效果 */
    transform: scale(1.02);
    /* 鼠标悬浮时轻微放大 */
  }

  .item:hover img {
    transform: scale(1.1);
    /* 图片放大效果 */
    transition: transform 0.3s ease;
  }

  .description {
    margin-top: 70px;
    display: flex;
    flex-direction: column;
    height: 51rem;
    align-items: center;
    justify-content: center;
  }

  .tip-item {
    width: 32%;
    background-color: #1E88E5;
    /* 深蓝色，与整体风格一致 */
    margin: 0 1rem 1rem 0rem;
    border-radius: 1rem;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: all 0.3s ease;
  }

  .tip-item:hover {
    background-color: #64B5F6;
    /* 鼠标悬浮时的浅蓝色 */
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
    /* 添加阴影效果 */
    transform: scale(1.1);
    /* 鼠标悬浮时放大 */
  }
}

/* 针对所有手机 */
@media only screen and (max-width: 767px) {
  .split-page {
    padding: 50px 0;
    display: flex;
    justify-content: center;
    margin: auto;
  }

  .tip-item {
    width: 32%;
    background-color: #1E88E5;
    /* 深蓝色，与整体风格一致 */
    margin: 0 1rem 1rem 0rem;
    border-radius: 1rem;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: all 0.3s ease;
  }

  .tip-item:hover {
    background-color: #64B5F6;
    /* 鼠标悬浮时的浅蓝色 */
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
    /* 添加阴影效果 */
    transform: scale(1.1);
    /* 鼠标悬浮时放大 */
  }

  .description {
    margin-top: 70px;
    display: flex;
    flex-direction: column;
    height: 50rem;
    align-items: center;
    justify-content: center;
  }

  .right-container {
    display: none;
  }

  .item {
    cursor: pointer;
    background-color: #E3F2FD;
    /* 浅蓝色，与 Home_Top_category 保持一致 */
    width: 100%;
    height: 400px;
    border-radius: 1rem;
    border: 2px solid #BBDEFB;
    /* 边框颜色更柔和 */
    margin-bottom: 20px;
    transition: all 0.3s ease;
  }

  .item:hover {
    border: 2px solid #64B5F6;
    /* 鼠标悬浮时的浅蓝色边框 */
    box-shadow: 0px 0px 20px rgba(62, 109, 178, 0.5);
    /* 添加阴影效果 */
    transform: scale(1.02);
    /* 鼠标悬浮时轻微放大 */
  }

  .contain {
    display: flex;
    width: 90%;
    margin: 0 auto;
    flex-wrap: wrap;
    height: 100%;
  }

  .items {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    width: 100%;
    height: 100%;
  }

  .hidden-phone {
    display: none;
  }
}

.item-image {
  height: 70%;
  width: 100%;
  overflow: hidden;
  border-radius: 1rem;
}

.item-image img {
  height: 100%;
  width: 100%;
  object-fit: cover;
  transform: scale(1);
  transition: transform 0.3s ease;
}

.title {
  height: 15%;
  overflow: hidden;
  display: flex;
  align-items: center;
  padding-left: 20px;
  font-weight: bold;
  color: #1565C0;
  /* 深蓝色，与整体风格一致 */
}

.tab {
  height: 10%;
  overflow: hidden;
  display: flex;
  align-items: center;
  padding-left: 20px;
  color: #1565C0;
  /* 深蓝色，与整体风格一致 */
}

.tab .center-all {
  display: flex;
  align-items: center;
  margin-right: 1rem;
}

.tab .center-all v-icon {
  margin-right: 0.5rem;
  color: #1565C0;
  /* 深蓝色，与整体风格一致 */
}

.tab .center-all:hover {
  color: #64B5F6;
  /* 鼠标悬浮时的浅蓝色 */
}

/* 以下是打字机的效果 */
.typewriter {
  margin-bottom: 2rem;
  font-family: 'Courier New', Courier, monospace;
  white-space: nowrap;
  overflow: hidden;
  border-right: 0.1em solid;
  letter-spacing: 0.1em;
  animation: blink-caret 0.75s step-end infinite;
}

.cursor {
  display: inline-block;
  margin-left: 5px;
  animation: blink-caret 0.75s step-end infinite;
}

/* 光标闪烁动画 */
@keyframes blink-caret {

  from,
  to {
    border-color: transparent
  }

  50% {
    border-color: black
  }
}

.top-info {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}




textarea {
  border: 1px solid #BBDEFB; /* 浅蓝色边框 */
  border-radius: 8px; /* 添加圆角 */
  padding: 10px;
  font-size: 14px;
  color: #303133; /* 深灰色文字 */
  transition: border-color 0.3s ease; /* 添加过渡效果 */
}

textarea:focus {
  border-color: #64B5F6; /* 聚焦时的浅蓝色边框 */
  outline: none;
}

/* 对话框底部按钮样式 */
.dialog-footer .el-button {
  border-radius: 8px; /* 添加圆角 */
  padding: 8px 20px; /* 调整内边距，增加按钮大小 */
  font-size: 14px; /* 调整字体大小 */
  font-weight: bold; /* 加粗文字 */
  transition: all 0.3s ease; /* 添加平滑过渡效果 */
}

/* 主按钮样式 */
.dialog-footer .el-button[type="primary"] {
  background-color: #1565C0; /* 深蓝色背景，与主题一致 */
  color: #ffffff; /* 白色文字 */
  border: none; /* 去除边框 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
}

.dialog-footer .el-button[type="primary"]:hover {
  background-color: #64B5F6; /* 鼠标悬浮时的浅蓝色背景 */
  transform: translateY(-2px); /* 鼠标悬浮时轻微上移 */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* 鼠标悬浮时增强阴影 */
}

/* 次按钮样式 */
.dialog-footer .el-button:not([type="primary"]) {
  background-color: #f5f5f5; /* 浅灰色背景 */
  color: #303133; /* 深灰色文字 */
  border: 1px solid #dcdcdc; /* 添加浅灰色边框 */
}

.dialog-footer .el-button:not([type="primary"]):hover {
  background-color: #e0e0e0; /* 鼠标悬浮时的更深灰色背景 */
  transform: translateY(-2px); /* 鼠标悬浮时轻微上移 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 鼠标悬浮时添加阴影 */
}
</style>