<template>

  <div class="container">
    <div style="width: 200px;">
      <panelOrdinary />
    </div>

    <div style="height: 100px; background-color: aqua;">

    </div>
    nihao

    <div>
     <panelOrdinaryRight />
    </div>
  </div>
</template>

<script setup>
import panelOrdinary from './echarts/panel-ordinary.vue';
import panelOrdinaryRight from './echarts/panel-ordinary-right.vue';
</script>

<style scoped>

.container {
  margin-top: 70px;
  height: 100%;
  
}
</style>