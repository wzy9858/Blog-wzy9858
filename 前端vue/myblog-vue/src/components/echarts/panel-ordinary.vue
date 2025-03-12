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
    title: {
      text: '文章热度排行'
    },
    tooltip: {},
    legend: {
      data: ['热度']
    },
    xAxis: {
      type: 'category',
      data: xAxisData.value
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '热度',
        type: 'bar',
        data: seriesData.value
      }
    ]
  };
  myChart.setOption(option);

  getPanelPopularityRankings().then(res => {

    console.log('-----res----');
    
    console.log(res.data[0].title);
    for (let i = 0; i < res.data.length; i++) {
      xAxisData.value.push(res.data[i].title);
      seriesData.value.push(res.data[i].popularition);
    }

    console.log(res.data[0].popularition);
    
    

    // xAxisData.value = ['1','2']
    // seriesData.value = ['222','444'];

      
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
}
</style>