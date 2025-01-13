import Vue from 'vue';
import axios from 'axios';
import router from './router';
import store from "@/vuex/store";
import App from "@/App.vue";
let vm = new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');
const that = this;
axios.defaults.baseURL='http://localhost:8080';
//不进行拦截
axios.interceptors.request.use(config =>
    {
        config.headers.Authorization = localStorage.getItem('token');
        // config.headers.id = localStorage.getItem('id');
        return config;
    },
    error => {
        return Promise.reject(error);
    });
//后置拦截
axios.interceptors.response.use(response => {
    let res = response.data;
    if (res.code === 1 || res.code === 200 || res.code === 0) {
        return response;
    } else if (res.code === 401) {
        return new Promise((resolve, reject) => {
            window.alert("身份已过期，请重新登录");
            vm.$router.push("/login");
            resolve(); // 用户确认后执行跳转
        }).then(() => {
            // 在这里可以执行一些额外的操作
        });
    }
    else{
        console.log(res);
        vm.$router.push("/404");
        return Promise.reject(res.message);
    }
});
