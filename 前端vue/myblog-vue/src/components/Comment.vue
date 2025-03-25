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
        <div v-if="comment.replies?.length" class="replies">
          <div v-for="reply in comment.replies" :key="reply.id" class="reply">
            <div class="comment-header">
              <img :src="reply.userAvatar" class="userAvatar small" alt="用户头像">
              <div class="user-info">
                <span class="userAccount">{{ reply.userAccount }}</span>
                <span class="createTime">{{ formatTime(reply.createTime) }}</span>
              </div>
              <!-- <button class="reply-btn" @click="startReply(reply.id, reply.userAccount)">回复</button> -->
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
      <textarea v-model="newComment"
        :placeholder="replyingToUsername ? `回复 ${replyingToUsername}...` : '写下你的评论...'"></textarea>
      <button @click="addComment">提交评论</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useCookies } from "vue3-cookies";
import { ElMessage } from 'element-plus'
import { getComments, createComment } from '../ts/axios/articleHttp';

const { cookies } = useCookies();
const comments = ref([])

let articlId = ref(null)



const processComment = (comment) => {
  // 转换时间格式并处理嵌套回复
  const process = (c) => ({
    ...c,
    createTime: new Date(c.createTime),
    replies: c.replies ? [process(c.replies)] : []
  })
  return process(comment)
}


function getCommentFcn() {
  if (cookies.get("CommentArticleId")) {
    console.log("查找评论 所属id-----");
    
    console.log(cookies.get("CommentArticleId"));
    
    getComments(cookies.get("CommentArticleId")).then(res => {
      comments.value = res.data.map(comment => ({
        ...comment,
        createTime: new Date(comment.createTime),
        replies: comment.replies ? [{
          ...comment.replies,
          createTime: new Date(comment.replies.createTime),
        }] : []
      }))
    }).catch(console.error)
  }
}

onMounted(() => {  
  getCommentFcn();
})



const newComment = ref('')
const replyingToId = ref(null)
const replyingToUsername = ref('')

const formatTime = (date) => {
  if (!(date instanceof Date)) date = new Date(date)
  return date.toLocaleDateString('zh-CN') + ' ' + date.toLocaleTimeString('zh-CN', {
    hour: '2-digit',
    minute: '2-digit'
  })
}

const startReply = (commentId, userAccount) => {
  replyingToId.value = commentId
  replyingToUsername.value = userAccount
}

const cancelReply = () => {
  replyingToId.value = null
  replyingToUsername.value = ''
}

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

const addComment = async () => {
  if (!newComment.value.trim()) return

  if (!cookies.get("account")) {
    ElMessage.error("登录后才可发评论")
    return
  }

  try {
    const commentData = {
      articleId: cookies.get("CommentArticleId"),
      content: newComment.value,
      userAccount: cookies.get("account"),
      userAvatar: cookies.get("accountImgUrl"),
      isParent: replyingToId.value ? 1 : 0,
      parentCommentId: replyingToId.value || null
    }

    // 提交到后端
    await createComment(commentData)

    // 前端即时更新
    const newCommentObj = {
      ...commentData,
      // id: Date.now(), // 临时ID，实际应由后端生成
      createTime: new Date(),
      replies: []
    }

    if (replyingToId.value) { // 

      const target = findComment(comments.value, replyingToId.value)
      console.log('=========');

      console.log(replyingToId.value); //1903268573482160000

      console.log(target);


      if (target) {
        target.replies = target.replies ? [...target.replies, newCommentObj] : [newCommentObj]
      }
    } else {

      comments.value = [newCommentObj, ...comments.value]

    }

    newComment.value = ''
    cancelReply()
  } catch (error) {
    console.error('评论提交失败:', error)
    ElMessage.error('评论提交失败')
  }
}
</script>

<style scoped>
/* 保持原有样式不变 */
.comment-section {
  max-width: 1150px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #eee;
  border-radius: 8px;

  background-color: #d91111;
}

.comment {
  margin: 15px 0;
  padding: 15px;
  border-bottom: 1px solid #eee;
}

.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  position: relative;
}

.userAvatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.userAvatar.small {
  width: 30px;
  height: 30px;
}

.user-info {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
}

.userAccount {
  font-weight: bold;
  margin-bottom: 3px;
}

.createTime {
  font-size: 0.8em;
  color: #666;
}

.content {
  margin-left: 50px;
}

.replies {
  margin-left: 40px;
  padding-left: 20px;
  border-left: 2px solid #ddd;
}

.new-comment textarea {
  width: 100%;
  height: 80px;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ddd;
  border-radius: 4px;
  resize: vertical;
}

button {
  background-color: #007bff;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}

.reply-btn {
  background: none;
  color: #666;
  padding: 4px 8px;
  margin-left: 10px;
}

.reply-btn:hover {
  background-color: #f0f0f0;
}

.replying-to {
  padding: 8px;
  background-color: #f8f9fa;
  border-radius: 4px;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.cancel-btn {
  background-color: #dc3545;
  padding: 4px 8px;
}

.cancel-btn:hover {
  background-color: #bb2d3b;
}
</style>