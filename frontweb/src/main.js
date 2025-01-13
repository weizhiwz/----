import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import "animate.css";
import "@/assets/usericon/iconfont.css";
import axios from "axios";
import { Message } from 'element-ui'
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import Vuex from 'vuex';
import store from './vuex/store'
import './axios'
import '../src/assets/style.css'
Vue.use(Vuex);

// 创建并导出Vuex store实例

Vue.use(VueQuillEditor)
// 挂载到$message上
Vue.prototype.$message = Message
Vue.config.productionTip = false
Vue.prototype.$axios = axios;
Vue.use(ElementUI);
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

