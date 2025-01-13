import Vue from 'vue'
import Vuex from 'vuex'

// 告诉 vue “使用” vuex
Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        token:localStorage.getItem("token"),
        id:localStorage.getItem("id")
    },
    mutations:{
        SET_TOKEN:(state,token)=>{
            state.token=token;
            localStorage.setItem("token",token);
        },
        SET_ID:(state,id)=>{
            state.id=id;
            localStorage.setItem("id",id);

        },
        REMOVE:(state)=>{
            state.token='';
            state.id='';
            localStorage.setItem("token",'');
            localStorage.setItem("id",'');
        }
    },
    getters:{
        getToken:state => {
            if(state.token ===null){
                return '';
            }
            else {
                return state.token;
            }
        },
        getId:state => {
            if(state.id ===null){
                return '';
            }
            else {
                return state.id;
            }
        }
    }
    //saveCurrDbSource
});