import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import Particles from "vue3-particles";
import { OhVueIcon, addIcons } from "oh-vue-icons";
import 'animate.css';
import {
     FaFlag, RiZhihuFill, FaHome, BiBatteryFull,
     RiMenuFoldFill, PxSortNumeric, PiCharizardShiny,
     PxLabelAltMultiple, GiCampfire, IoTimeOutline, PiRhydon,
     GiRocketThruster, PxArchive, MdPersonsearchOutlined, HiLogin, IoSettingsOutline,CoVerticalAlignTop,
     CoAirbnb,BiTranslate,CoIcloud,BiGithub,SiGitee,RiBilibiliFill,BiFileEarmarkText,HiUserGroup,FcFeedback,
     MdManagesearchTwotone,MdThumbupaltTwotone
} from "oh-vue-icons/icons";
import './assets/fonts/text.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 黑暗主题
import 'element-plus/theme-chalk/dark/css-vars.css'

import router from './router/index.ts';

import { createPinia } from 'pinia';
import VueCookies from 'vue3-cookies';
addIcons(FaFlag, GiRocketThruster, MdPersonsearchOutlined, CoVerticalAlignTop,HiLogin, PxArchive, IoSettingsOutline, PxLabelAltMultiple, RiZhihuFill, IoTimeOutline, GiCampfire, FaHome, PiRhydon, PiCharizardShiny, BiBatteryFull, RiMenuFoldFill,
      PxSortNumeric,CoAirbnb,BiTranslate,CoIcloud,BiGithub,SiGitee,RiBilibiliFill,BiFileEarmarkText,FcFeedback,
      HiUserGroup,MdManagesearchTwotone,MdThumbupaltTwotone

     );

// 加载进度条动画
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';


import VueAnimateOnScroll from 'vue3-animate-onscroll';

NProgress.configure({ 
     easing: 'linear', // 缓冲动画类型，可选值如'ease'、'linear'等 
     speed: 500, // 动画速度，单位为毫秒 
     trickleSpeed: 200, // 每次进度条步进的速度，单位为毫秒 
     showSpinner: true, // 是否显示环形进度动画 
     minimum: 0.2 // 设置开始时最低百分比  同inc
   });


const app = createApp(App)

app.component("v-icon", OhVueIcon);
app.use(Particles);
app.use(ElementPlus);
app.use(router);
app.use(createPinia())
app.use(VueCookies);
app.use(VueAnimateOnScroll);
app.mount('#app')
