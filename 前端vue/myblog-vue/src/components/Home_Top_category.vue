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
    const container = scrollContainer.value
    if (!container) return
    
    // 获取单个分类项的宽度（包含margin）
    const firstItem = container.querySelector('.category-item')
    const itemStyle = firstItem ? getComputedStyle(firstItem) : null
    const itemWidth = firstItem ? firstItem.offsetWidth + parseInt(itemStyle.marginRight) : 0
    
    // 计算每次滚动距离（8个分类项宽度）
    scrollDistance.value = itemWidth * 8
    
    // 计算最大滚动位置
    maxScroll.value = container.scrollWidth - container.clientWidth
  }
  
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
    width: 900px;
    margin: 0 auto;
    padding: 0 60px;
  }
  
  .items-wrapper {
    display: flex;
    overflow-x: hidden;
    scroll-behavior: smooth;
    gap: 20px;
    padding: 12px 0;
  }
  
  .category-item {
    flex: 0 0 auto;
    min-width: 120px;
    padding: 8px 16px;
    border-radius: 20px;
    background: #E3F2FD; /* 与之前的浅蓝色保持一致 */
    color: #1565C0; /* 深蓝色字体 */
    cursor: pointer;
    transition: all 0.3s;
    text-align: center;
    margin-right: 10px;
    font-weight: 500;
  }
  
  .category-item:hover {
    background: #64B5F6; /* 鼠标悬浮时的浅蓝色 */
    color: white; /* 悬浮时字体颜色变为白色 */
    transform: scale(1.05); /* 悬浮时放大效果 */
  }
  
  .category-item.active {
    background: #1E88E5; /* 激活状态的深蓝色 */
    color: white;
    font-weight: bold;
  }
  
  .nav-button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    width: 36px;
    height: 36px;
    border-radius: 50%;
    background: #FFFFFF; /* 按钮背景为白色 */
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    border: none;
    cursor: pointer;
    z-index: 1;
    transition: all 0.3s;
  }
  
  .nav-button:hover {
    background: #F5F5F5; /* 鼠标悬浮时的浅灰色 */
    transform: translateY(-50%) scale(1.1); /* 悬浮时放大效果 */
  }
  
  .nav-button.left {
    left: 10px;
  }
  
  .nav-button.right {
    right: 10px;
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
  </style>