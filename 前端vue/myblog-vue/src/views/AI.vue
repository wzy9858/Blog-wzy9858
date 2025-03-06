<template>
  <div class="ai-container">
    
    <div class="chat-window">
      <div class="chat-box">
        <div v-for="(msg, index) in chatHistory" :key="index" :class="msg.role === 'user' ? 'message-right' : 'message-left'">
          <div class="name">
            <span>{{ msg.role === 'user' ? '用户' : 'AI' }}</span>
          </div>
          <div class="chat_message">
            {{ msg.content }}
          </div>
        </div>
      </div>

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

const userInput = ref('');
const response = ref(null);
const chatHistory = ref([]);

const sendMessage = async () => {
  console.log('Sending message:', userInput.value); // 调试信息
  chatHistory.value.push({ role: 'user', content: userInput.value });
  try {
    const result = await axios.post('https://models.inference.ai.azure.com/chat/completions', {
      messages: [
        {
          role: 'system',
          content: 'You are a helpful assistant.',
        },
        ...chatHistory.value,
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
    chatHistory.value.push({ role: 'assistant', content: aiResponse });
    response.value = aiResponse;
  } catch (error) {
    console.error('Error sending message:', error);
  }
  userInput.value = '';
};
</script>

<style scoped>
.ai-container {
  padding-top: 20px;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.chat-window {
  width: 60%;
  height: 80%;
  border: 1px solid #ccc;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.chat-box {
  flex: 1;
  overflow-y: auto;
  margin-bottom: 20px;
}

.chat-message {
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 10px;
  max-width: 70%;
  word-wrap: break-word;
  display: inline-block;
}

.message-left {
  max-width: 418px;
  align-self: flex-start;
}

.message-left .chat_message {
  background-color: #f1f1f1;
}

.message-left .name {
  align-self: flex-start;
  font-family: PingFangSC-Regular;
  font-weight: 400;
  font-size: 12px;
  color: #909399;
  margin-bottom: 6px;
}

.message-right {
  max-width: 418px;
  align-self: flex-end;
  display: flex;
  flex-direction: column;
}

.message-right .chat_message {
  background-color: #007bff;
  color: white;
}

.message-right .name {
  align-self: flex-end;
  font-family: PingFangSC-Regular;
  font-weight: 400;
  font-size: 12px;
  color: #909399;
  margin-bottom: 6px;
}

.input-box {
  display: flex;
  flex-direction: column;
}

textarea {
  width: 100%;
  height: 100px;
  margin-bottom: 10px;
}

button {
  align-self: flex-end;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>