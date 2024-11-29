import { createRouter, createWebHashHistory } from 'vue-router'
import About from '../views/About.vue';
import Home from '../views/Home.vue';
import Article from '../views/Article.vue';
import Archives from '../views/Archives/Archives.vue';
const router = createRouter({
  history: createWebHashHistory(),
  routes: [{
    path: '/',
    //   path: '/', redirect: '/article',
    component: Home,
    // children: [
    //   {
    //     path: '/article',
    //     component: Article,
    //   },

    // ]
  }, {
    path: '/article',
    component: Article,
  },{
    path: '/archives',
    component: Archives,
  },{
    path: '/about',
    component: About,
  }
  ],
})
export default router;