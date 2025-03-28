<template>
 <div ref="chart" style="height: 440px; width: 100%;display: flex; justify-content: center; align-items: flex-start;"></div>
</template>

<script setup>
import { ref, onMounted } from 'vue'; // 从 Vue 中导入 ref 和 onMounted 函数
import * as echarts from 'echarts'; // 导入 ECharts 库
import axios from 'axios'; // 导入 axios 库

const chart = ref(null); // 创建一个 ref，用于引用图表容器

// 初始化图表的函数
const initChart = (data) => {
  const chartDom = chart.value; // 获取图表容器的 DOM 元素
  const myChart = echarts.init(chartDom); // 初始化 ECharts 实例
  const option = {
    title: {
      text: '标签', // 图表标题
      subtext: '文章标签比例分布',
      left: 'center', // 标题居中
      top: '0px', // 调整标题位置
      textStyle: {
        color: '#1565C0', // 标题颜色（深蓝色）
        fontSize: 18, // 标题字体大小
        fontWeight: 'bold'
      },
      subtextStyle: {
        color: '#64B5F6', // 副标题颜色（浅蓝色）
        fontSize: 14
      }
    },
    tooltip: {
      trigger: 'item', // 提示框触发类型，'item' 表示数据项触发
      backgroundColor: 'rgba(50, 50, 50, 0.8)', // 提示框背景颜色
      textStyle: {
        color: '#fff' // 提示框文字颜色
      }
    },
    legend: {
      orient: 'vertical', // 图例垂直布局
      left: 'center', // 图例位置
      top: '350px', // 图例垂直居中
      textStyle: {
        color: '#424242' // 图例文字颜色（深灰色）
      }
    },
    series: [
      {
        name: '标签名', // 系列名称
        type: 'pie', // 图表类型为饼图
        radius: ['50%', '70%'], // 饼图的内外半径，形成环形图
        data: data, // 使用请求获得的数据
        emphasis: {
          itemStyle: {
            shadowBlur: 20, // 高亮状态下的阴影模糊大小
            shadowOffsetX: 0, // 阴影水平方向上的偏移
            shadowColor: 'rgba(0, 0, 0, 0.5)' // 阴影颜色
          }
        },
        label: {
          // ----------------- 去除标签文字 -----------------q
          show: false, // 显示标签文字
          formatter: '{b}: {d}%', // 标签格式：显示名称和百分比
          color: '#303133', // 标签文字颜色
          fontSize: 12
        },
        labelLine: {
          // ----------------- 去除标签引导线 -----------------
          show: false, // 显示标签的引导线
          // lineStyle: {
          //   color: '#ccc' // 引导线颜色
          // }
        },
        itemStyle: {
          borderRadius: 10, // 扇形边缘圆角
          borderColor: '#fff', // 扇形边框颜色
          borderWidth: 2 // 扇形边框宽度
        },
        color: ['#4CAF50', '#FF9800', '#2196F3', '#9C27B0', '#FFC107','#6D4C41','#FFAB91','#F5F5F5','#795548'] // 自定义颜色
      }
    ]
  };
  myChart.setOption(option); // 使用指定的配置项和数据显示图表
};

import { getHomePanelData } from '../../ts/axios/articleHttp.ts';
getHomePanelData()
  .then((s) => {
    console.log(s);
    const data = s.data;
    initChart(data);
  })
  .catch((error) => {
    console.error('获取数据失败:', error);
  });

// 在组件挂载后获取数据并初始化图表
onMounted(() => {
  getHomePanelData();
});
</script>

<style scoped>
/* 图表容器样式 */
div[ref="chart"] {
  height: 440px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  background-color: #f5f5f5; /* 浅灰色背景 */
  border-radius: 8px; /* 添加圆角 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  padding: 20px; /* 增加内边距 */
}
</style>