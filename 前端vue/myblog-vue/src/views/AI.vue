<template>
  <div class="ai-container">
    <h1 style="margin-bottom: 20px; margin-top: 20px;">AI小助手</h1>
    <div class="chat-container">
      <div v-for="(message, index) in messages" :key="index"
        :class="message.align === 'left' ? 'message-left' : 'message-right'">
        <div class="name">
          <span>{{ message.name }} {{ message.time }} </span>
        </div>
        <div class="chat_message">
          {{ message.text }}
        </div>
      </div>

      <div v-if="loading" class="loading">加载中...</div>

      <div class="input-box">
        <textarea v-model="userInput" placeholder="输入你的问题"></textarea>
        <button @click="sendMessage">发送</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const messages = ref([
  {
    text: "你好！我是一个 AI 助手，可以帮助你解决问题、提供建议、回答问题或协助你完成各种任务。我能够处理各种主题，包括学习、工作、娱乐、日常生活相关的问题，还能进行聊天，随时陪你沟通。 无论是查找信息、写文章、翻译、数学计算，或者提供技术支持，我都可以帮忙！如果有任何问题或者需要帮助的地方，请随时告诉我！😊",
    align: "left", name: "AI", time: new Date().toLocaleTimeString()
  },
  // { text: "你好！", align: "right", name: "丽丝", time: "19:21" },
  // { text: "如何才能帮助您？", align: "left", name: "王阳阳", time: "21:26" },
  // {
  //   text: "我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发我需要帮助进行Vue.js开发",
  //   align: "right",
  //   name: "丽丝",
  //   time: "22:37"
  // }
]);
const userInput = ref('');
const loading = ref(false);

const sendMessage = async () => {
  console.log('Sending message:', userInput.value); // 调试信息

  if (userInput.value.trim() !== '') {
    messages.value.push({
      text: userInput.value,
      align: 'right',
      name: '用户',
      time: new Date().toLocaleTimeString()
    });
  }
  userInput.value = '';
  loading.value = true;

  try {
    const formattedMessages = messages.value.map(msg => ({
      role: msg.align === 'right' ? 'user' : 'assistant',
      content: msg.text
    }));

    const result = await axios.post('https://models.inference.ai.azure.com/chat/completions', {
      messages: [
        {
          role: 'system',
          content: 'You are a helpful assistant.',  // 发送的信息
        },
        ...formattedMessages,  // 历史记录
      ],
      model: 'gpt-4o',
    }, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `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`, // 替换为你的 API Key
      },
    });

    console.log('Response received:', result.data); // 调试信息
    const aiResponse = result.data.choices[0].message.content.trim();

    messages.value.push({
      text: aiResponse,
      align: 'left',
      name: 'AI',
      time: new Date().toLocaleTimeString()
    });

  } catch (error) {
    console.error('Error sending message:', error);
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.ai-container {
  padding-top: 80px;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  background-color: #E3F2FD; /* 浅蓝色背景，与整体风格一致 */
}

.chat-container {
  overflow: hidden;
  display: flex;
  flex-direction: column;
  width: 800px; /* 增加宽度 */
  background: #ffffff;
  border: 1px solid #BBDEFB; /* 柔和蓝色边框 */
  border-radius: 8px; /* 添加圆角 */
  padding: 24px; /* 增加内边距 */
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

.name {
  font-family: PingFangSC-Regular;
  font-weight: 400;
  font-size: 14px; /* 增大字体 */
  color: #909399;
  margin-bottom: 6px;
}

.chat_message {
  padding: 12px 20px; /* 增加内边距 */
  background: #f8f8f9;
  border-radius: 8px; /* 添加圆角 */
  margin-bottom: 20px; /* 增加间距 */
  word-wrap: break-word;
  font-weight: 400;
  font-size: 16px; /* 增大字体 */
  color: #303133;
  transition: background-color 0.3s ease, transform 0.3s ease; /* 添加过渡效果 */
}

.message-left {
  max-width: 600px; /* 增加最大宽度 */
  align-self: flex-start;
}

.message-left .chat_message {
  background-color: #f8f8f9;
}

.message-left .chat_message:hover {
  background-color: #BBDEFB; /* 鼠标悬浮时的柔和蓝色背景 */
  transform: scale(1.02); /* 鼠标悬浮时放大效果 */
}

.message-left .name {
  align-self: flex-start;
}

.message-right {
  max-width: 600px; /* 增加最大宽度 */
  align-self: flex-end;
  display: flex;
  flex-direction: column;
}

.message-right .chat_message {
  background-color: #ebf3ff;
}

.message-right .chat_message:hover {
  background-color: #64B5F6; /* 鼠标悬浮时的浅蓝色背景 */
  transform: scale(1.02); /* 鼠标悬浮时放大效果 */
}

.message-right .name {
  align-self: flex-end;
}

.input-box {
  display: flex;
  flex-direction: column;
  width: 100%;
}

textarea {
  width: 100%;
  height: 120px; /* 增加高度 */
  margin-bottom: 15px; /* 增加间距 */
  resize: none;
  border: 1px solid #BBDEFB; /* 柔和蓝色边框 */
  border-radius: 8px; /* 添加圆角 */
  padding: 12px; /* 增加内边距 */
  font-size: 16px; /* 增大字体 */
  color: #303133;
  transition: border-color 0.3s ease;
}

textarea:focus {
  border-color: #64B5F6; /* 聚焦时的浅蓝色边框 */
  outline: none;
}

button {
  width: 100%;
  padding: 12px; /* 增加按钮高度 */
  background-color: #1E88E5; /* 深蓝色按钮背景 */
  color: white;
  border: none;
  border-radius: 8px; /* 添加圆角 */
  cursor: pointer;
  font-size: 18px; /* 增大字体 */
  transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
  background-color: #0056b3; /* 鼠标悬浮时的深蓝色背景 */
  transform: scale(1.02); /* 鼠标悬浮时放大效果 */
}

.loading {
  text-align: center;
  margin: 10px 0;
  font-size: 16px; /* 增大字体 */
  color: #1E88E5; /* 深蓝色加载文字 */
}

/* 添加媒体查询以适应手机端 */
@media (max-width: 600px) {
  .chat-container {
    width: 90%;
    padding: 16px; /* 减少内边距 */
  }

  .chat_message {
    font-size: 14px; /* 减小字体 */
  }

  .name {
    font-size: 12px; /* 减小字体 */
  }

  textarea {
    height: 100px; /* 减小高度 */
  }

  button {
    padding: 10px; /* 减小按钮高度 */
  }
}
</style>