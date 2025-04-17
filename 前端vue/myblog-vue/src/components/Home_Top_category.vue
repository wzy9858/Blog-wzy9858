<template>
    <div class="category-container" ref="containerRef">
      <!-- 导航按钮 -->
      <button 
        class="nav-button left"
        @click="scroll('left')"
        :disabled="scrollPosition <= 0"
      >
        ←
      </button>
      <button 
        class="nav-button right"
        @click="scroll('right')"
        :disabled="scrollPosition >= maxScroll"
      >
        →
      </button>
  
      <!-- 分类项容器 -->
      <div class="items-wrapper" ref="scrollContainer">
        <div 
          v-for="(item, index) in categories" 
          :key="index"
          class="category-item"
          :class="{ active: activeIndex === index }"
          @click="selectCategory(index)"
        >
          {{ item }}
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, nextTick } from 'vue'
  import { getAllCategory } from '../ts/axios/visitorHttp'
  const emit = defineEmits(['triggerMethodA', 'triggerMethodB'])

  // 固定分类数据（示例16个）
  const categories = ref([
    '全部', '全部', '全部', '全部', '全部', '全部','全部', '全部', '全部', '全部', '全部', '全部', '全部', '全部', '全部', '全部', '全部', '全部', '全部',
    '全部', '全部', '全部', '全部', '全部', '全部', '全部', '全部',
])


  
  // 响应式数据
  const activeIndex = ref(0)
  const scrollContainer = ref(null)
  const containerRef = ref(null)
  const scrollPosition = ref(0)
  const maxScroll = ref(0)
  const scrollDistance = ref(0)
  
  // 初始化计算
  onMounted(
    async () => {
    await nextTick()
    calculateDimensions()
    window.addEventListener('resize', calculateDimensions)
    getAllCategory().then(
        s =>{
            categories.value = s.data
            // console.log(categories.value);
            // console.log(s.data);
        }
    ).catch()
  })
  
  // 计算滚动参数
  const calculateDimensions = () => {
    const container = scrollContainer.value;
    if (!container) return;
  
    // 获取单个分类项的宽度（包含margin）
    const firstItem = container.querySelector('.category-item');
    const itemStyle = firstItem ? getComputedStyle(firstItem) : null;
    const itemWidth = firstItem
      ? firstItem.offsetWidth + parseInt(itemStyle.marginRight)
      : 0;
  
    // 根据屏幕宽度动态调整每次滚动的分类项数量
    const itemsPerScroll = window.innerWidth <= 767 ? 4 : 8; // 小屏幕滚动4个，大屏幕滚动8个
    scrollDistance.value = itemWidth * itemsPerScroll;
  
    // 计算最大滚动位置
    maxScroll.value = container.scrollWidth - container.clientWidth;
  };
  
  // 监听窗口大小变化
  window.addEventListener('resize', calculateDimensions);
  
  // 滚动控制
  const scroll = (direction) => {
    const container = scrollContainer.value
    if (!container) return
  
    let newPosition = scrollPosition.value
    
    if (direction === 'right') {
      newPosition = Math.min(scrollPosition.value + scrollDistance.value, maxScroll.value)
    } else {
      newPosition = Math.max(scrollPosition.value - scrollDistance.value, 0)
    }
  
    container.scrollTo({
      left: newPosition,
      behavior: 'smooth'
    })
    
    scrollPosition.value = newPosition
  }
  

  // 分类选择
  const selectCategory = (index) => {
    activeIndex.value = index
    // console.log(index);
    // console.log(categories.value[index]);//具体的数值
    emit('triggerMethodA', categories.value[index])  // 触发方法A[1](@ref)
  }


  </script>
  
  <style scoped>
  .category-container {
    position: relative;
    width: 100%; /* 宽度改为100%，适配不同屏幕 */
    max-width: 900px; /* 最大宽度限制 */
    margin: 0 auto;
    padding: 0 20px; /* 两侧增加内边距 */
  }
  
  .items-wrapper {
    display: flex;
    overflow-x: hidden;
    scroll-behavior: smooth;
    gap: 10px; /* 间距调整为更小的值，适配小屏幕 */
    padding: 12px 0;
  }
  
  .category-item {
    flex: 0 0 auto;
    min-width: 80px; /* 最小宽度调整为80px，适配小屏幕 */
    padding: 6px 12px; /* 内边距调整为更小的值 */
    border-radius: 20px;
    background: #E3F2FD;
    color: #1565C0;
    cursor: pointer;
    transition: all 0.3s;
    text-align: center;
    margin-right: 8px; /* 间距调整为更小的值 */
    font-size: 14px; /* 字体大小调整为14px */
    font-weight: 500;
  }
  
  .category-item:hover {
    background: #64B5F6;
    color: white;
    transform: scale(1.05);
  }
  
  .category-item.active {
    background: #1E88E5;
    color: white;
    font-weight: bold;
  }
  
  .nav-button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    width: 32px; /* 按钮大小调整为更小的值 */
    height: 32px;
    border-radius: 50%;
    background: #FFFFFF;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    border: none;
    cursor: pointer;
    z-index: 1;
    transition: all 0.3s;
  }
  
  .nav-button:hover {
    background: #F5F5F5;
    transform: translateY(-50%) scale(1.1);
  }
  
  .nav-button.left {
    left: 5px; /* 左侧按钮位置调整 */
  }
  
  .nav-button.right {
    right: 5px; /* 右侧按钮位置调整 */
  }
  
  .nav-button:disabled {
    opacity: 0.5;
    cursor: not-allowed;
    transform: translateY(-50%);
  }
  
  /* 隐藏滚动条 */
  .items-wrapper::-webkit-scrollbar {
    display: none;
  }

  /* 小屏幕适配 */
  @media only screen and (max-width: 767px) {
    .category-item {
      min-width: 60px; /* 更小的最小宽度 */
      padding: 4px 8px; /* 更小的内边距 */
      font-size: 12px; /* 更小的字体大小 */
    }
  
    .nav-button {
      width: 28px; /* 更小的按钮大小 */
      height: 28px;
    }
  
    .nav-button.left {
      left: 2px; /* 左侧按钮位置调整 */
    }
  
    .nav-button.right {
      right: 2px; /* 右侧按钮位置调整 */
    }
  }
  </style>