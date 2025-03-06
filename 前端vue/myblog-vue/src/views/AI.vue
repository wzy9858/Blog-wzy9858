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
}

.chat-container {
  overflow: hidden;
  display: flex;
  flex-direction: column;
  width: 600px;
  background: #ffffff;
  border: 1px solid #e4e7ed;
  border-radius: 3px;
  padding: 16px;
}

.name {
  font-family: PingFangSC-Regular;
  font-weight: 400;
  font-size: 12px;
  color: #909399;
  margin-bottom: 6px;
}

.chat_message {
  padding: 6px 12px;
  background: #f8f8f9;
  border-radius: 4px;
  margin-bottom: 15px;
  word-wrap: break-word;
  font-weight: 400;
  font-size: 14px;
  color: #303133;
}

.message-left {
  max-width: 418px;
  align-self: flex-start;
}

.message-left .chat_message {
  background-color: #f8f8f9;
}

.message-left .name {
  align-self: flex-start;
}

.message-right {
  max-width: 418px;
  align-self: flex-end;
  display: flex;
  flex-direction: column;
}

.message-right .chat_message {
  background-color: #ebf3ff;
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
  height: 100px;
  margin-bottom: 10px;
  resize: none;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.loading {
  text-align: center;
  margin: 10px 0;
  font-size: 14px;
  color: #007bff;
}

/* 添加媒体查询以适应手机端 */
@media (max-width: 600px) {
  .chat-container {
    width: 90%;
    padding: 8px;
  }

  .chat_message {
    font-size: 12px;
  }

  .name {
    font-size: 10px;
  }

  textarea {
    height: 80px;
  }

  button {
    padding: 8px;
  }
}
</style>