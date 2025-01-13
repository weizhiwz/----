import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../views/user/login.vue'
import Enroll from '../views/user/enroll.vue'
import Forget from '../views/user/forget.vue'
import Home from '@/views/menu/AdminHome.vue'
import '@/views/menu/admin/usercontrol.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/enroll',
    name: 'enroll',
    component: Enroll
  },
  {
    path: '/forget',
    name: 'forget',
    component: Forget
  },
  {
    path: '/AdminHome',
    name: 'AdminHome',
    component: ()=> import("@/views/menu/AdminHome.vue"),
    // redirect:'/AdminHome/Home',
    children:[
      {
        path:'usercontrol',
        name:"usercontrol",
        component:()=> import ('@/views/menu/admin/usercontrol.vue')
      },
      {
        path:'Home',
        name:"Home",
        component:()=> import ('@/views/menu/user/Home.vue')
      },
      {
        path:'class',
        name:"class",
        component:()=> import ('@/views/menu/user/class.vue')
      },
      {
        path:'lab',
        name:"lab",
        component:()=> import ('@/views/menu/user/lab.vue')
      },
      {
        path:'semester',
        name:"semester",
        component:()=> import ('@/views/menu/admin/semester.vue')
      },
      {
        path:'setting',
        name:"setting",
        component:()=> import ('@/views/menu/user/setting.vue')
      },
      {
        path:'classroom',
        name:"classroom",
        component:()=> import ('@/views/menu/admin/classroom.vue')
      },
    ]
  },
  {
    path: '/404',
    name:"404",
    component:()=> import ('@/views/404.vue')
  },
  {
    path:'*',
    name:'any',
    redirect: '/login'
  }
]

const router = new VueRouter({
  routes
});

export default router; // 导出路由实例
