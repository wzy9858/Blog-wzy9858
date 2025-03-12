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
      top: '50px' 
    },
    tooltip: {
      trigger: 'item' // 提示框触发类型，'item' 表示数据项触发
    },
    legend: {
        orient: 'vertical', // 图例垂直布局
        left: 'center', // 图例在图表右侧
        top: 'bottom' //
    },
    series: [
      {
        name: '标签名', // 系列名称
        type: 'pie', // 图表类型为饼图
        radius: '80%', // 饼图的半径
        data: data, // 使用请求获得的数据
        emphasis: {
          itemStyle: {
            shadowBlur: 10, // 高亮状态下的阴影模糊大小
            shadowOffsetX: 0, // 阴影水平方向上的偏移
            shadowColor: 'rgba(0, 0, 0, 0.5)' // 阴影颜色
          }
        },
        label: {
          show: false // 隐藏标签文字
        },
        labelLine: {
          show: false // 隐藏标签的引导线 
        }
      }
    ]
  };
  myChart.setOption(option); // 使用指定的配置项和数据显示图表
};
import {getHomePanelData} from '../../ts/axios/articleHttp.ts'
getHomePanelData().then(
  s => {
    console.log(s);
    const data = s.data
    initChart(data);

  }
).catch(
  e =>{
   console.error('获取数据失败:', error);
  }
)
// 在组件挂载后获取数据并初始化图表
onMounted(() => {

  getHomePanelData();
});
</script>

<style scoped>
/* 你可以在这里添加样式 */
</style>