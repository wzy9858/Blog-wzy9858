<template>
  <!-- 本页面放一些柱状图 -->
  <div class="panel-container">

    <div class="panel-top">
      <div class="panel-top-item part1">
        <div>
          <v-icon name="bi-file-earmark-text" scale="3" />
        </div>
        <div style="display: flex; flex-direction: column;">
          <h1 style="font-family: myfont1;">{{articleNum}}</h1>

          <span style="font-weight: 100; font-size: 15px;">文章总数</span>
        </div>
      </div>
      <div class="panel-top-item part2">
        <div>
          <v-icon name="hi-user-group" scale="3" />
        </div>
        <div style="display: flex; flex-direction: column;">
          <h1 style="font-family: myfont1;">{{visitorNum}}</h1>
          <span style="font-weight: 100; font-size: 15px;">访客总数</span>
        </div>
      </div>

      <div class="panel-top-item part3">
        <div>
          <v-icon name="hi-login" scale="3" />
        </div>
        <div style="display: flex; flex-direction: column;">
          <h1 style="font-family: myfont1;">{{userNum}}</h1>
          用户总数
        </div>
      </div>

    </div>


    <!-- 访客量图 -->
    <div ref="barChart" class="chart"></div>

    <!-- 原主页饼图 和 待定图 -->

    <div class="flex-container">
      <div class="flex-item">

        <panel-ordinary />
      </div>

      <div class="flex-item">
        <panel-ordinary-right />
      </div>

    </div>

  </div>
</template>

<script setup>
import panelOrdinaryRight from '../../components/echarts/panel-ordinary-right.vue';
import panelOrdinary from '../../components/echarts/panel-ordinary.vue';
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
import { getVisitorDateAndNum } from '../../ts/axios/visitorHttp.ts';
import { getPanelSomeInfo } from '../../ts/axios/visitorHttp.ts';


let articleNum = ref('');
let visitorNum = ref('');
let userNum = ref('');




// Generate data
let category = []; // 日期类别数据
let lineData = [];
let barData = [];

const barChart = ref(null);

const initBarChart = () => {
  const chartDom = barChart.value;
  const myChart = echarts.init(chartDom);

  const option = {
    title: {
      text: '访问量统计直观图',
      left: 'right',
      textStyle: {
        color: '#ccc'
      }
    },
    backgroundColor: '#0f375f',
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: ['line', 'bar'],
      textStyle: {
        color: '#ccc'
      }
    },
    xAxis: {
      data: category,
      axisLine: {
        lineStyle: {
          color: '#ccc'
        }
      }
    },
    yAxis: {
      splitLine: { show: false },
      axisLine: {
        lineStyle: {
          color: '#ccc'
        }
      }
    },
    series: [
      {
        name: 'line',
        type: 'line',
        smooth: true,
        showAllSymbol: true,
        symbol: 'emptyCircle',
        symbolSize: 15,
        data: lineData
      },
      {
        name: 'bar',
        type: 'bar',
        barWidth: 10,
        itemStyle: {
          borderRadius: 5,
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#14c8d4' },
            { offset: 1, color: '#43eec6' }
          ])
        },
        data: barData
      },
      {
        name: 'line',
        type: 'bar',
        barGap: '-100%',
        barWidth: 10,
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(20,200,212,0.5)' },
            { offset: 0.2, color: 'rgba(20,200,212,0.2)' },
            { offset: 1, color: 'rgba(20,200,212,0)' }
          ])
        },
        z: -12,
        data: lineData
      },
      {
        name: 'dotted',
        type: 'pictorialBar',
        symbol: 'rect',
        itemStyle: {
          color: '#0f375f'
        },
        symbolRepeat: true,
        symbolSize: [12, 4],
        symbolMargin: 1,
        z: -10,
        data: lineData
      }
    ]
  };

  myChart.setOption(option);
};

onMounted(() => {
  getPanelSomeInfo().then(
  s => {
    // console.log(s.data);
    articleNum.value =  s.data.split('-')[0];
    visitorNum.value =  s.data.split('-')[1];
    userNum.value =  s.data.split('-')[2];
  }
).catch(err => {
  // console.error(err);
});

  getVisitorDateAndNum().then(
    s => {
      s.data.forEach(item => {
        category.push(item.date);
        barData.push(item.num);
        lineData.push(item.num);
      });
      initBarChart();
    }
  ).catch(err => {
    console.error(err);
  });
});
</script>

<style scoped>
.panel-top-item:hover {
  background-color: antiquewhite;
  transform: scale(1.05);
  /* 轻微放大 */
}

.panel-top-item {
  height: 80px;
  background-color: aqua;
  transition: transform 0.3s ease;
  /* 添加过渡效果 */


  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}


.panel-top {
  display: flex;
  direction: column;
}

.panel-container {
  width: 80%;
  margin: auto;
  margin-top: 90px;
}

.chart {
  width: 100%;
  height: 400px;
}

.flex-container {
  display: flex;
  justify-content: space-around;
  width: 100%;
}

.flex-item {


  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  max-width: 50%;
  /* 确保每个组件最多占据50%的宽度 */
}


.part1 {
  background-color: #4CAF50;
}

.part1:hover {
  background-color: #388E3C;
}

.part2 {
  background-color: #FF9800;
}

.part2:hover {
  background-color: #F57C00;
}

.part3 {
  background-color: #2196F3;
}

.part3:hover {
  background-color: #1976D2;
}
</style>