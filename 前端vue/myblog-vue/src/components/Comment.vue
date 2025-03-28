<template>
  <div class="comment-section">
    <!-- 评论列表 -->
    <div class="comments">
      <div v-for="comment in comments" :key="comment.id" class="comment">
        <div class="comment-header">
          <img :src="comment.userAvatar" class="userAvatar" alt="用户头像">
          <div class="user-info">
            <span class="userAccount">{{ comment.userAccount }}</span>
            <span class="createTime">{{ formatTime(comment.createTime) }}</span>
          </div>
          <button class="reply-btn" @click="startReply(comment.id, comment.userAccount)">回复</button>
        </div>
        <div class="content">{{ comment.content }}</div>

        <!-- 子评论 -->
        <div v-if="comment.replies && comment.replies.length > 0" class="replies">
          <div v-for="reply in comment.replies" :key="reply.id" class="reply">
            <div class="comment-header">
              <img :src="reply.userAvatar" class="userAvatar small" alt="用户头像">
              <div class="user-info">
                <span class="userAccount">{{ reply.userAccount }}</span>
                <span class="createTime">{{ formatTime(reply.createTime) }}</span>
              </div>
              <!-- <button class="reply-btn" @click="startReply(comment.id, reply.userAccount)">回复</button> -->
            </div>
            <div class="content">{{ reply.content }}</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 发表评论 -->
    <div class="new-comment">
      <div v-if="replyingToUsername" class="replying-to">
        正在回复 @{{ replyingToUsername }}
        <button class="cancel-btn" @click="cancelReply">取消</button>
      </div>
      <textarea 
        v-model="newComment"
        :placeholder="replyingToUsername ? `回复 ${replyingToUsername}...` : '写下你的评论...'"
      ></textarea>
      <button @click="addComment">提交评论</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useCookies } from "vue3-cookies";
import { ElMessage } from 'element-plus'
import { useRoute } from 'vue-router'
import { getComments, createComment } from '../ts/axios/articleHttp';

const { cookies } = useCookies();
const route = useRoute();
const comments = ref([])
const articleId = ref(route.query.id)

const newComment = ref('')
const replyingToId = ref(null)
const replyingToUsername = ref('')


// 获取评论数据
const fetchComments = async () => {

  let id = ref(route.query.id)
  try {
    const res = await getComments(id.value)
    comments.value = res.data.map(comment => ({
      ...comment,
      createTime: new Date(comment.createTime),
      replies: comment.replies || []
    }))
  } catch (error) {
    console.error('获取评论失败:', error)
    ElMessage.error('获取评论失败')
  }
}

// 时间格式化
const formatTime = (date) => {
  if (!(date instanceof Date)) date = new Date(date)
  return date.toLocaleDateString('zh-CN') + ' ' + date.toLocaleTimeString('zh-CN', {
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 回复处理
const startReply = (commentId, userAccount) => {
  
  replyingToId.value = commentId
  replyingToUsername.value = userAccount

  console.log("点击了回复评论");
  console.log("commentId = "+replyingToId.value);
  console.log("userAccount = "+replyingToUsername.value);
}

const cancelReply = () => {

  replyingToId.value = null
  replyingToUsername.value = ''

  console.log("点击了取消回复");
  console.log("commentId = "+replyingToId.value);
  console.log("userAccount = "+replyingToUsername.value);
}

// 递归查找评论
const findComment = (commentList, targetId) => {
  for (const comment of commentList) {
    if (comment.id === targetId) return comment
    if (comment.replies?.length) {
      const found = findComment(comment.replies, targetId)
      if (found) return found
    }
  }
  return null
}

// 添加评论
const addComment = async () => {
  if (!newComment.value.trim()) {
    ElMessage.warning('评论内容不能为空')
    return
  }

  if (!cookies.get("account")) {
    ElMessage.error("请登录后发表评论")
    return
  }

  try {
    const commentData = {
      articleId: articleId.value,
      content: newComment.value.trim(),
      userAccount: cookies.get("account"),
      userAvatar: cookies.get("accountImgUrl"),
      isParent: replyingToId.value ? 1 : 0, // 1表示父评论，0表示子评论
      parentCommentId: replyingToId.value
    }

    // 提交到后端
    const res = await createComment(commentData)

    console.log("点击提交评论");
    console.log("是否有父评论"+replyingToId.value);
    
    
    console.log("提交评论的数据:");
    
    console.log(commentData);
    
    
    //前端重新获取后端数据
    fetchComments()
    // 前端更新逻辑
    // const newCommentObj = {
    //   ...res.data, // 使用后端返回的真实数据
    //   createTime: new Date(res.data.createTime),
    //   replies: []
    // }

    // if (replyingToId.value) {
    //   const target = findComment(comments.value, replyingToId.value)
    //   if (target) {
    //     if (!target.replies) target.replies = []
    //     target.replies.unshift(newCommentObj)
    //   }
    // } else {
    //   comments.value.unshift(newCommentObj)
    // }

    newComment.value = ''
    cancelReply()
    ElMessage.success('评论发表成功')
  } catch (error) {
    console.error('评论提交失败:', error)
    ElMessage.error('评论提交失败')
  }
}

onMounted(() => {
  // if (30) {
    fetchComments()
  // } else {
  //   ElMessage.error('缺少文章ID参数')
  // }
})
</script>

<style scoped>
/* 评论区整体样式 */
.comment-section {
  max-width: 1150px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #e0e0e0; /* 浅灰色边框 */
  border-radius: 8px;
  background-color: #ffffff; /* 白色背景 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

/* 单条评论样式 */
.comment {
  margin: 15px 0;
  padding: 15px;
  border-bottom: 1px solid #f0f0f0; /* 浅灰色分隔线 */
  transition: background-color 0.3s ease; /* 添加过渡效果 */
}

.comment:hover {
  background-color: #f9f9f9; /* 鼠标悬浮时的浅灰色背景 */
}

/* 评论头部样式 */
.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  position: relative;
}

/* 用户头像样式 */
.userAvatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
  object-fit: cover;
  border: 2px solid #e3f2fd; /* 浅蓝色边框 */
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* 添加过渡效果 */
}

.userAvatar:hover {
  transform: scale(1.1); /* 鼠标悬浮时放大效果 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* 鼠标悬浮时添加阴影 */
}

/* 用户信息样式 */
.user-info {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
}

.userAccount {
  font-weight: 600;
  margin-bottom: 3px;
  color: #1565c0; /* 深蓝色用户名 */
}

.createTime {
  font-size: 0.8em;
  color: #999999; /* 浅灰色时间文字 */
}

/* 评论内容样式 */
.content {
  margin-left: 50px;
  color: #303133; /* 深灰色文字 */
  line-height: 1.6;
}

/* 子评论样式 */
.replies {
  margin-left: 40px;
  padding-left: 20px;
  border-left: 2px solid #e0e0e0; /* 浅灰色边框 */
}

/* 新评论输入框样式 */
.new-comment {
  margin-top: 30px;
}

.new-comment textarea {
  width: 100%;
  height: 100px;
  padding: 12px;
  margin: 10px 0;
  border: 1px solid #bbdefb; /* 浅蓝色边框 */
  border-radius: 6px;
  resize: vertical;
  font-size: 14px;
  color: #303133; /* 深灰色文字 */
  transition: border-color 0.3s ease; /* 添加过渡效果 */
}

.new-comment textarea:focus {
  border-color: #64b5f6; /* 聚焦时的浅蓝色边框 */
  outline: none;
}

/* 按钮样式 */
button {
  background-color: #1e88e5; /* 深蓝色背景 */
  color: white;
  padding: 8px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease; /* 添加过渡效果 */
}

button:hover {
  background-color: #64b5f6; /* 鼠标悬浮时的浅蓝色背景 */
  transform: scale(1.05); /* 鼠标悬浮时放大效果 */
}

/* 回复按钮样式 */
.reply-btn {
  background: none;
  color: #666666; /* 深灰色文字 */
  padding: 4px 10px;
  border: 1px solid #dddddd; /* 浅灰色边框 */
  border-radius: 4px;
  transition: background-color 0.3s ease, color 0.3s ease; /* 添加过渡效果 */
}

.reply-btn:hover {
  background-color: #f8f9fa; /* 鼠标悬浮时的浅灰色背景 */
  color: #1565c0; /* 鼠标悬浮时的深蓝色文字 */
}

/* 回复状态样式 */
.replying-to {
  padding: 8px 12px;
  background-color: #f8f9fa; /* 浅灰色背景 */
  border-radius: 4px;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 0.9em;
}

/* 取消按钮样式 */
.cancel-btn {
  background-color: #dc3545; /* 红色背景 */
  color: white;
  padding: 4px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease; /* 添加过渡效果 */
}

.cancel-btn:hover {
  background-color: #bb2d3b; /* 鼠标悬浮时的深红色背景 */
  transform: scale(1.05); /* 鼠标悬浮时放大效果 */
}
</style>