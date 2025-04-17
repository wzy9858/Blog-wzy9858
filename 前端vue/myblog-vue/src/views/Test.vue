<template>

  <div>
    <el-button type="primary" @click="f1 = !f1">ai大纲</el-button>
  </div>

  <div class="ai-stream-container" v-if="f1">

    <!-- 输入区域 -->
    <div class="input-group">
      <textarea v-model="promptText" placeholder="请输入你的问题" @keyup.enter="startStream" rows="2"></textarea>
      <button :disabled="isConnecting" @click="startStream">
        {{ isConnecting ? '传输中...' : '发送' }}
      </button>
    </div>

    <!-- 显示区域 -->
    <div class="output-container">
      <div v-if="!aiResponse" class="empty-tip">
        等待问题输入...
      </div>
      <div v-else class="message-item">
        <MdPreview :id="id" :modelValue="aiResponse" />
        <!-- {{ aiResponse }} -->
      </div>
    </div>
  </div>

</template>

<script setup>
import { ref, nextTick } from 'vue';
import { MdPreview } from 'md-editor-v3';
// 响应式数据
const promptText = ref('');
const aiResponse = ref(''); // 用于存储 AI 的回复内容
const isConnecting = ref(false);
let eventSource = null;
let f1 = ref(false)
// 启动流式请求
const startStream = async () => {
  if (!promptText.value.trim()) {
    alert('请输入有效内容');
    return;
  }

  resetState();
  isConnecting.value = true;

  try {
    const encodedPrompt = encodeURIComponent(promptText.value);
    eventSource = new EventSource(`http://localhost:8080/ai-stream?prompt=${encodedPrompt}`);

    // 处理消息
    eventSource.onmessage = (event) => handleSSEData(event.data);

    // 错误处理
    eventSource.onerror = (error) => {
      console.error('SSE Error:', error);
      closeConnection();
      aiResponse.value += '\n';
    };
  } catch (error) {
    console.error('启动失败:', error);
    isConnecting.value = false;
  }
};

let flag = true;
let flag2 = true;

// 解析 SSE 数据
const handleSSEData = (rawData) => {

  // a:123456
  // b: 78910
  try {

    const isThinking = rawData.includes('思考过程');
    const content = rawData.split(': ')[1];

    if (flag) {//在思考过程的开头加
      aiResponse.value += '### 思考过程：\n';
      flag = false;
    }
    if (!isThinking && flag2) {//在结果的前面加
      aiResponse.value += '\n### Ai答复\n';
      flag2 = false;
    }

    aiResponse.value += content
    // aiResponse.value += content;
    scrollToBottom();
  } catch (e) {
    console.warn('数据解析异常:', e);
  }
};


// 关闭连接
const closeConnection = () => {
  if (eventSource) {
    eventSource.close();
    eventSource = null;
  }
  isConnecting.value = false;
};

// 重置状态
const resetState = () => {
  aiResponse.value = ''; // 清空 AI 回复内容
  closeConnection();
};

// 自动滚动到底部
const scrollToBottom = () => {
  nextTick(() => {
    const container = document.querySelector('.output-container');
    if (container) {
      container.scrollTop = container.scrollHeight;
    }
  });
};
</script>

<style scoped>
.ai-stream-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  max-width: 600px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.input-group {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}

textarea {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  resize: none;
  font-size: 14px;
  font-family: inherit;
}

textarea:focus {
  outline: none;
  border-color: #007bff;
  box-shadow: 0 0 4px rgba(0, 123, 255, 0.5);
}

button {
  padding: 10px 20px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
}

button:disabled {
  background: #6c757d;
  cursor: not-allowed;
}

button:hover:not(:disabled) {
  background: #0056b3;
}

.output-container {
  height: 300px;
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 10px;
  overflow-y: auto;
  background-color: #fff;
  white-space: pre-wrap;
  /* 保留换行符 */
  font-size: 14px;
  line-height: 1.5;
}

.message-item {
  margin-bottom: 10px;
  padding: 8px;
  background: #f1f1f1;
  border-radius: 4px;
  word-wrap: break-word;
}

.empty-tip {
  color: #999;
  text-align: center;
  padding: 20px;
  font-size: 14px;
}
</style>