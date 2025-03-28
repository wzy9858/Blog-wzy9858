<template>
  <div id="main-right" style="width: 100%; height: 400px;"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import { getHomePanelData } from '../../ts/axios/articleHttp';

const data = ref([
  { value: 335, name: 'Direct' },
  { value: 310, name: 'Email' },
  { value: 274, name: 'Union Ads' },
  { value: 235, name: 'Video Ads' },
  { value: 400, name: 'Search Engine' }
]);

onMounted(() => {
  var chartDom = document.getElementById('main-right');
  var myChart = echarts.init(chartDom);
  var option = {
    backgroundColor: '#f5f5f5', // 浅灰色背景，柔和且不刺眼
    title: {
      text: '文章标签分布',
      left: 'center',
      top: 20,
      textStyle: {
        color: '#1565C0', // 深蓝色标题，符合整体风格
        fontSize: 18,
        fontWeight: 'bold'
      }
    },
    tooltip: {
      trigger: 'item',
      backgroundColor: 'rgba(50, 50, 50, 0.8)', // 半透明黑色背景
      textStyle: {
        color: '#ffffff' // 白色文字
      }
    },
    visualMap: {
      show: false,
      min: 80,
      max: 600,
      inRange: {
        colorLightness: [0.2, 0.8] // 调整颜色亮度范围
      }
    },
    series: [
      {
        name: '标签',
        type: 'pie',
        radius: '55%',
        center: ['50%', '50%'],
        data: data.value.sort(function (a, b) {
          return a.value - b.value;
        }),
        roseType: 'radius',
        label: {
          color: '#303133', // 深灰色标签文字
          fontSize: 12
        },
        labelLine: {
          lineStyle: {
            color: '#ccc' // 浅灰色引导线
          },
          smooth: 0.2,
          length: 10,
          length2: 20
        },
        itemStyle: {
          color: function (params) {
            const colors = ['#4CAF50', '#FF9800', '#2196F3', '#9C27B0', '#FFC107'];
            return colors[params.dataIndex % colors.length]; // 使用自定义颜色
          },
          shadowBlur: 20, // 阴影模糊大小
          shadowColor: 'rgba(0, 0, 0, 0.5)', // 阴影颜色
          borderColor: '#ffffff', // 白色边框
          borderWidth: 2
        },
        emphasis: {
          itemStyle: {
            shadowBlur: 30, // 鼠标悬浮时增加阴影模糊
            shadowColor: 'rgba(0, 0, 0, 0.8)', // 鼠标悬浮时的阴影颜色
            borderColor: '#64B5F6', // 鼠标悬浮时的边框颜色（浅蓝色）
            borderWidth: 3
          }
        },
        animationType: 'scale',
        animationEasing: 'elasticOut',
        animationDelay: function (idx) {
          return Math.random() * 200;
        }
      }
    ]
  };
  myChart.setOption(option);

  getHomePanelData().then(res => {
    data.value = res.data;
    option.series[0].data = data.value.sort(function (a, b) {
      return a.value - b.value;
    }).map(item => ({
      ...item,
      itemStyle: {
        color: '#64B5F6', // 默认浅蓝色
        shadowBlur: 20,
        shadowColor: 'rgba(0, 0, 0, 0.5)'
      },
      label: {
        color: '#303133' // 深灰色标签文字
      },
      labelLine: {
        lineStyle: {
          color: '#ccc' // 浅灰色引导线
        }
      }
    }));
    myChart.setOption(option);
  });
});
</script>

<style scoped>
#main-right {
  width: 100%;
  height: 400px;
  background-color: #f5f5f5; /* 浅灰色背景，与图表背景一致 */
  border-radius: 8px; /* 添加圆角 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  padding: 20px; /* 增加内边距 */
}
</style>