<template>
  <div id="main-left" style="width: 100%; height: 400px;"></div>
</template>

<script setup>
import { onMounted } from 'vue';
import * as echarts from 'echarts';
import {getVisitorDateAndNum} from '../../ts/axios/visitorHttp.ts';

onMounted(() => {
  var chartDom = document.getElementById('main-left');
  var myChart = echarts.init(chartDom);
  var option = {
    title: {
      text: '文章热度统计'
    },
    tooltip: {},
    legend: {
      data: ['分类']
    },
    xAxis: {
      data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
    },
    yAxis: {},
    series: [
      {
        name: '分类',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
      }
    ]
  };
  myChart.setOption(option);
});
</script>

<style scoped>
#main {
  width: 100%;
  height: 400px;
}
</style>