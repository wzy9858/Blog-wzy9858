<template>
  <div id="main-right" style="width: 100%; height: 400px;"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import {getHomePanelData} from '../../ts/axios/articleHttp'

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
    backgroundColor: '#ffffff',
    title: {
      text: '文章标签分布',
      left: 'center',
      top: 20,
      textStyle: {
        color: '#000'
      }
    },
    tooltip: {
      trigger: 'item'
    },
    visualMap: {
      show: false,
      min: 80,
      max: 600,
      inRange: {
        colorLightness: [0, 1]
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
          color: 'rgba(255, 255, 255, 0.3)'
        },
        labelLine: {
          lineStyle: {
            color: 'rgba(255, 255, 255, 0.3)'
          },
          smooth: 0.2,
          length: 10,
          length2: 20
        },
        itemStyle: {
          color: '#c23531',
          shadowBlur: 200,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
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
        color: '#384fad',
        shadowBlur: 200,
        shadowColor: 'rgba(0, 0, 0, 1)'
      },
      label: {
        color: 'rgba(0, 0, 0, 1)'
      },
      labelLine: {
        lineStyle: {
          color: 'rgba(0.09, 0.34, 0.54, 0.3)'
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
}
</style>