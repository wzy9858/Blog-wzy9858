import { createRouter, createWebHashHistory } from 'vue-router'

import Home from '../views/Home.vue';
import Article from '../views/Article.vue';
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
  }
  ],
})
export default router;