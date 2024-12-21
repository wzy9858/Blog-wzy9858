/// <reference types="vite/client" />
declare module 'vue3-particles';
declare module 'vue3-animate-onscroll';

// 解决引入模块的报错提示
declare module "nprogress";

declare module "*.vue" {
    import type { DefineComponent } from "vue";
   
    const vueComponent: DefineComponent<{}, {}, any>;
   
    export default vueComponent;
    
  }
  