<template>
  <div class="page-box">
      <div class="whiteback">
        <left></left>
          <div class="right">
            <transition name="el-fade-in-linear">
              <el-form class="form" v-if="show">
                <p class="sign">登录 LOGIN</p>
                <el-form-item>
                  <p style=" font-size:15px;margin: 0;font-weight: lighter">工号 JOB NUMBER</p>
                    <input v-model="username" class="input">
                </el-form-item>
                <el-form-item>
                  <p style="font-size:15px;margin: 0;font-weight: lighter">密码 PASSWORD</p>
                  <input v-model="password" class="input" type="password">
                </el-form-item>
                <div style="height: 45px;display: flex">
                  <el-checkbox style="height: 100%" v-model="rememberMe">记住我</el-checkbox>
                  <p class="hoveroptions" @click="goto(enroll)">现在注册?</p>
                  <p class="hoveroptions" @click="goto(forget)">忘记密码?</p>
                </div>
                <el-form-item>
                  <div style="display: flex">
                    <button class="submit" @click="submit">登录</button>
                  </div>
                </el-form-item>
              </el-form>
            </transition>
          </div>
      </div>

  </div>
</template>
<script>
import '@/assets/loginmain.css'
import '@/assets/loginright.css'
import Left from "@/components/left.vue"
export default {
  name:"page-box",
  components: {Left},
  data() {
    return {
      username:"",
      password:"",
      value:"",
      enroll:"enroll",
      forget:"forget",
      show:true,
      form:{},
      subform:{},
      rememberMe:false,
    };
  },
  methods:{
    submit() {
      event.preventDefault();
      if(this.username===''||this.password==='')
      {
        this.$message.error("信息不完善");
        return;
      }
      this.subform={
        tea_id:this.username,
        password:this.password
      };
      this.form={
        teacher:this.subform,
        rememberMe:this.rememberMe
      }
      let that = this;
      this.$axios.post('/user/login',this.form)
          .then(res=>{
           if(res.data.code===1)
           {
             that.$message({
               message: res.data.msg,
               type: 'success'
             });
             this.$store.commit('SET_TOKEN',res.data.data.token);
             this.$store.commit('SET_ID',res.data.data.tea_id);
             this.$router.replace('/AdminHome');
           }
           else
           {
             this.$message({
               message: res.data.msg,
               type: 'error'
             });
           }
          })
          .catch(function (error) {
            console.log(error);
            that.$message({
              message: '网络连接错误',
              type: 'error'
            });
          });

    },
    goto(e){
      this.$router.push('/'+e);
    }
  },
  created() {
    // this.show = true
  },
  mounted() {
    // this.show=!this.show
  }
}
</script>
<style>


</style>
