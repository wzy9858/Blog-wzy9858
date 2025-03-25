<template>
    <div class="custom-cursor" :style="{ left: x + 'px', top: y + 'px' }"></div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  
  const x = ref(-100)
  const y = ref(-100)
  
  onMounted(() => {
    document.addEventListener('mousemove', (e) => {
      x.value = e.clientX - 4  // 居中偏移修正
      y.value = e.clientY - 4
    })
  })
  </script>