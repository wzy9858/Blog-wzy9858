<template>
    <div>

      <!-- 右上角的组件 -->
      <div class="ai-stream-container">
        <!-- 输入区域 -->
        <div class="input-group">
          <textarea
            v-model="promptText"
            placeholder="请输入你的问题"
            @keyup.enter="startStream"
            rows="1"
          ></textarea>
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
            <MdPreview  :modelValue="aiResponse" />
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, nextTick } from 'vue';
  import { MdPreview } from 'md-editor-v3';
  
  import { useTextStore } from '../ts/store/index';
  
  const textStore = useTextStore();
  
  // console.log("-------------");
  // console.log(textStore.longText); // 访问 store 中的 text 属性
  
  // 响应式数据
  const promptText = ref('');
  const aiResponse = ref(''); // 用于存储 AI 的回复内容
  const isConnecting = ref(false);
  let eventSource = null;

  
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

      eventSource = new EventSource(`http://localhost:8080/ai/stream?prompt=${encodedPrompt}`);
      // eventSource = new EventSource(`http://123.56.28.80:8080/ai/stream?prompt=${encodedPrompt}`);
  
      // 处理消息
      eventSource.onmessage = (event) => handleSSEData(event.data);
  
      // 错误处理
      eventSource.onerror = (error) => {
        console.error('SSE Error:', error);
        closeConnection();
        aiResponse.value += '\n';
      };
  
      promptText.value = ''; // 清空输入框内容
    } catch (error) {
      console.error('启动失败:', error);
      isConnecting.value = false;
      promptText.value = ''; // 清空输入框内容
    }
  };
  
  let flag = true;
  let flag2 = true;
  
  // 解析 SSE 数据
  const handleSSEData = (rawData) => {
    try {
      const isThinking = rawData.includes('思考过程');
      const content = rawData.split(': ')[1];
  
      if (flag) {
        aiResponse.value += '##### 思考过程：\n';
        flag = false;
      }
      if (!isThinking && flag2) {
        aiResponse.value += '\n### Ai答复\n';
        flag2 = false;
      }
  
      aiResponse.value += content;
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
  
    /* top: 160px; 距离顶部 20px */
    /* right: 10px; 距离右侧 20px */
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 1000px; /* 调整宽度以适应右上角布局 */
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    width: 96%;
    /* z-index: 1000; 确保在其他元素之上 */
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
    height: 400px; /* 调整高度以适应右上角布局 */
    border: 1px solid #ddd;
    border-radius: 4px;
    padding: 10px;
    overflow-y: auto;
    background-color: #fff;
    white-space: pre-wrap;
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
  
  .circle-checkbox-container {
    display: flex;
    align-items: center;
    gap: 5px;
    position: relative;
  }
  
  .circle-checkbox {
    position: relative;
    display: inline-block;
    width: 12px; /* 调整圆圈的宽度 */
    height: 12px; /* 调整圆圈的高度 */
  }
  
  .circle-checkbox input {
    opacity: 0;
    width: 0;
    height: 0;
  }
  
  .circle-checkbox span {
    position: absolute;
    top: 0;
    left: 0;
    width: 12px; /* 调整圆圈的宽度 */
    height: 12px; /* 调整圆圈的高度 */
    background-color: #fff;
    border: 2px solid #007bff;
    border-radius: 50%;
    cursor: pointer;
    transition: background-color 0.3s, border-color 0.3s;
  }
  
  .circle-checkbox input:checked + span {
    background-color: #007bff;
    border-color: #007bff;
  }
  
  .circle-checkbox span::after {
    content: '';
    position: absolute;
    display: none;
    top: 50%;
    left: 50%;
    width: 6px; /* 调整选中状态的圆点大小 */
    height: 6px; /* 调整选中状态的圆点大小 */
    background-color: #fff;
    border-radius: 50%;
    transform: translate(-50%, -50%);
  }
  
  .circle-checkbox input:checked + span::after {
    display: block;
  }
  
  .checkbox-label {
    font-size: 14px;
    color: #333;
    cursor: pointer;
  }
  
  .circle-checkbox-container:hover .checkbox-label::after {
    content: '勾选后将引用文章内容';
    position: absolute;
    top: 25px;
    left: 0;
    background-color: #f9f9f9;
    color: #333;
    padding: 5px 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 12px;
    white-space: nowrap;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  </style>