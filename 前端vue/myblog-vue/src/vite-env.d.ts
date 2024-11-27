/// <reference types="vite/client" />
declare module 'vue3-particles';

declare module "*.vue" {
    import type { DefineComponent } from "vue";
   
    const vueComponent: DefineComponent<{}, {}, any>;
   
    export default vueComponent;
  }