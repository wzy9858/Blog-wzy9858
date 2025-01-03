import { createRouter, createWebHashHistory } from 'vue-router'
import About from '../views/About.vue';
import Home from '../views/Home.vue';
import Article from '../views/Article.vue';
import Archives from '../views/Archives/Archives.vue';
import Record from '../views/Record.vue';

import ManageAccount from '../views/Manage/ManageAccount.vue';
import ManageArticle from '../views/Manage/ManageArticle.vue';
import ManageVisitor from '../views/Manage/ManageVisitor.vue';
import EditAboutMe from '../views/EditAboutMe.vue';
import EditArticle from '../views/EditArticle.vue';
import NProgress from 'nprogress';

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
  }, {
    path: '/edit',
    component: EditArticle,
  },{
    path: '/editAboutMe',
    component:EditAboutMe
  }

  ],
})
router.beforeEach((to, from, next) => {
  NProgress.start();
  next();
});

router.afterEach(() => {
  NProgress.done();
});
export default router;