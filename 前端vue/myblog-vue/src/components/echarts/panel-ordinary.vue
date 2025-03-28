<template>
  <div id="main-left" style="width: 100%; height: 400px;"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import { getPanelPopularityRankings } from '../../ts/axios/visitorHttp.ts';

const xAxisData = ref([]);
const seriesData = ref([]);

onMounted(() => {
  var chartDom = document.getElementById('main-left');
  var myChart = echarts.init(chartDom);
  var option = {
    backgroundColor: '#f5f5f5', // 浅灰色背景，柔和且不刺眼
    title: {
      text: '文章热度排行',
      left: 'center',
      textStyle: {
        color: '#1565C0', // 深蓝色标题，符合整体风格
        fontSize: 18,
        fontWeight: 'bold'
      }
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow' // 鼠标悬浮时显示阴影指示器
      },
      backgroundColor: 'rgba(50, 50, 50, 0.8)', // 半透明黑色背景
      textStyle: {
        color: '#ffffff' // 白色文字
      }
    },
    legend: {
      data: ['热度'],
      top: '10%',
      textStyle: {
        color: '#424242' // 深灰色图例文字
      }
    },
    xAxis: {
      type: 'category',
      data: xAxisData.value,
      axisLine: {
        lineStyle: {
          color: '#64B5F6' // 浅蓝色坐标轴线
        }
      },
      axisLabel: {
        color: '#303133' // 深灰色坐标轴标签
      }
    },
    yAxis: {
      type: 'value',
      axisLine: {
        lineStyle: {
          color: '#64B5F6' // 浅蓝色坐标轴线
        }
      },
      axisLabel: {
        color: '#303133' // 深灰色坐标轴标签
      },
      splitLine: {
        lineStyle: {
          color: '#e0e0e0' // 浅灰色网格线
        }
      }
    },
    series: [
      {
        name: '热度',
        type: 'bar',
        data: seriesData.value,
        itemStyle: {
          color: '#4CAF50', // 默认绿色柱状图
          emphasis: {
            color: '#FF9800' // 鼠标悬浮时的橙色柱状图
          }
        },
        barWidth: '50%', // 调整柱状图宽度
        label: {
          show: true,
          position: 'top',
          color: '#303133', // 深灰色标签文字
          fontSize: 12
        }
      }
    ]
  };
  myChart.setOption(option);

  getPanelPopularityRankings().then(res => {
    for (let i = 0; i < res.data.length; i++) {
      xAxisData.value.push(res.data[i].title);
      seriesData.value.push(res.data[i].popularition);
    }

    myChart.setOption({
      xAxis: {
        data: xAxisData.value
      },
      series: [
        {
          name: '热度',
          type: 'bar',
          data: seriesData.value
        }
      ]
    });
  });
});
</script>

<style scoped>
#main-left {
  width: 100%;
  height: 400px;
  background-color: #f5f5f5; /* 浅灰色背景，与图表背景一致 */
  border-radius: 8px; /* 添加圆角 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  padding: 20px; /* 增加内边距 */
}
</style>