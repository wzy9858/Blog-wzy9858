import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import Particles from "vue3-particles";
import { OhVueIcon, addIcons } from "oh-vue-icons";
import { FaFlag, RiZhihuFill, FaHome, GiArchiveResearch,
     RiMenuFoldFill, PxSortNumeric,PiCharizardShiny,PxLabelAltMultiple,GiCampfire,IoTimeOutline,PiRhydon,GiRocketThruster } from "oh-vue-icons/icons";
import './assets/fonts/text.css'

addIcons(FaFlag,GiRocketThruster,PxLabelAltMultiple, RiZhihuFill,IoTimeOutline,GiCampfire, FaHome,PiRhydon,PiCharizardShiny,GiArchiveResearch, RiMenuFoldFill, PxSortNumeric);


const app = createApp(App)
app.component("v-icon", OhVueIcon);
app.use(Particles);
app.mount('#app')
