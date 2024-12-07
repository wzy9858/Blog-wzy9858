import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import Particles from "vue3-particles";
import { OhVueIcon, addIcons } from "oh-vue-icons";
import {
     FaFlag, RiZhihuFill, FaHome, BiBatteryFull,
     RiMenuFoldFill, PxSortNumeric, PiCharizardShiny,
     PxLabelAltMultiple, GiCampfire, IoTimeOutline, PiRhydon,
     GiRocketThruster, PxArchive, CoAboutMe, HiLogin, IoSettingsOutline
} from "oh-vue-icons/icons";
import './assets/fonts/text.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import router from './router/index.ts';

import { createPinia } from 'pinia';
addIcons(FaFlag, GiRocketThruster, CoAboutMe, HiLogin, PxArchive, IoSettingsOutline, PxLabelAltMultiple, RiZhihuFill, IoTimeOutline, GiCampfire, FaHome, PiRhydon, PiCharizardShiny, BiBatteryFull, RiMenuFoldFill, PxSortNumeric);

const app = createApp(App)

app.component("v-icon", OhVueIcon);
app.use(Particles);
app.use(ElementPlus);
app.use(router);
app.use(createPinia())
app.mount('#app')
