import { createRouter, createWebHashHistory } from 'vue-router'
import About from '../views/About.vue';
import Home from '../views/Home.vue';
import Article from '../views/Article.vue';
import Archives from '../views/Archives/Archives.vue';
import Record from '../views/Record.vue';

import ManageAccount from '../views/Manage/ManageAccount.vue';
import ManageArticle from '../views/Manage/ManageArticle.vue';
import ManageVisitor from '../views/Manage/ManageVisitor.vue';

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
  }, {
    path: '/archives',
    component: Archives,
  }, {
    path: '/about',
    component: About,
  }, {
    path: '/record',
    component: Record,
  }, {
    path: '/accountManage',
    component: ManageAccount,
  }, {
    path: '/articleManage',
    component: ManageArticle,
  }, {
    path: '/visitorManage',
    component: ManageVisitor,
  }
  ],
})
export default router;