import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],

  server: {
    proxy: {
      '/api': {
        // target: 'http://wzy9858.top:8080', // 目标接口的域名
        target: 'http://127.0.0.1:8080', // 目标接口的域名
        // target: 'http://123.56.28.80:8080', // 目标接口的域名
        changeOrigin: true, // 是否改变源地址
        rewrite: (path) => path.replace(/^\/api/, '') // 路径重写
      }
    }
  },
})




