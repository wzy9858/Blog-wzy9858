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
          <el-button type="info" @click="centerDialogVisible = true">
            <v-icon name="fc-feedback" scale="1.3" />
             bug反馈
          </el-button>
        </div>
        <!-- bug反馈对话框 -->
        <el-dialog v-model="centerDialogVisible" title="问题反馈" width="500" align-center >
          <textarea v-model="userFeedback" placeholder="请输入你要反馈的问题" style="width: 100%; height: 100px;">
          </textarea>
          <textarea v-model="userFeedbackContect"  placeholder="你的联系方式" style="width: 100%;">

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
const userFeedback = ref('');
const userFeedbackContect = ref('')
let router = useRouter();
let head_img = ref("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
let nickname = ref('');
let centerDialogVisible = ref(false)
function sendFeedbackMail(){
  console.log("input="+userFeedback.value);// 用户输入的内容
  console.log("联系方式="+userFeedbackContect.value);// 用户的联系方式

  // centerDialogVisible.value = false;//点击发送就关闭
  sendFeedback(userFeedback.value+"-"+userFeedbackContect.value).then(
    s =>{
      ElMessage.success("🎈已成功发送邮件")
      userFeedback.value = ''
      userFeedbackContect.value=''
      centerDialogVisible.value = false;//关闭弹窗
    }
  ).catch(
    e => {
      ElMessage.success("网络出错啦!")
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
  pageSize: 10,
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
  background-color: rgb(29, 124, 172);
  /* height: 20px; */
  display: flex;
  width: 80%;
  margin: auto;
  margin-bottom: 30px;

  /* border-radius: 6rem; */

  flex-wrap: wrap;
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
    background-color: aliceblue;
    width: 49%;
    height: 400px;
    border-radius: 1rem;
    border: 2px solid rgb(155, 150, 150);
    margin-bottom: 20px;
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
    background-color: rgb(29, 124, 172);
    margin: 0 1rem 1rem 0rem;
    border-radius: 1rem;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
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
    background-color: rgb(29, 124, 172);
    margin: 0 1rem 1rem 0rem;
    border-radius: 1rem;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
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
    background-color: aliceblue;
    width: 100%;
    height: 400px;
    border-radius: 1rem;
    border: 2px solid rgb(155, 150, 150);
    margin-bottom: 20px;
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
}

.item:hover {
  border: 2px solid rgb(231, 232, 234);
  box-shadow: 0px 0px 20px rgb(62, 109, 178);
}

.item:hover img {
  transform: scale(1.30);
  border: 2px solid rgb(231, 232, 234);
  transition: transform 0.3s ease;
}

.item:hover .item-text {
  transform: scale(1.01);
  border: 2px solid rgb(231, 232, 234);
  transition: transform 0.3s ease;
}

.tip {
  width: 100%;
  display: flex;
  flex-direction: row;
  margin: 0 auto;
  height: 100px;
}

.card {
  display: flex;
  width: 80%;
  margin: auto;
  flex-wrap: wrap;
}

.item-text {
  background-color: antiquewhite;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70%;
  width: 100%;
  overflow: hidden;
  border-radius: 1rem;
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
  object-fit: contain;
  transform: scale(1.2);
}

.title {
  height: 15%;
  overflow: hidden;
  display: flex;
  align-items: center;
  padding-left: 20px;
}

.tab {
  height: 10%;
  overflow: hidden;
  display: flex;
  align-items: center;
  padding-left: 20px;
}

.one-card {
  top: 80px;
  border: 2px solid black;
  background-color: rgb(21, 239, 239);
}

.tip-item:hover {
  box-shadow: 10px 10px 10px black;
  transform: scale(1.20);
  border: 2px solid rgb(231, 232, 234);
  transition: transform 0.3s ease;
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
</style>