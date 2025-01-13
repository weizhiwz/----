<template>
  <div class="page-box">
    <div class="whiteback">
      <left></left>
      <div class="right">
        <transition name="el-fade-in-linear">
          <el-form class="form" v-if="show">
            <p class="sign">注册教师账号 ENROLL</p>
            <el-form-item>
              <p style=" font-size:15px;margin: 0;font-weight: lighter">工号 JOB NUMBER</p>
              <input v-model="username" class="input">
            </el-form-item>
            <el-form-item>
              <p style=" font-size:15px;margin: 0;font-weight: lighter">身份证号 ID</p>
              <input v-model="idcard" class="input" type="password" >
            </el-form-item>
            <el-form-item>
              <p style="font-size:15px;margin: 0;font-weight: lighter">密码 PASSWORD</p>
              <input v-model="password" class="input" type="password">
            </el-form-item>
            <div style="display: flex;width: 100%">
              <button class="submit" style="width: 80%" @click="submit">注册</button>
              <div style="display: flex;margin: 0" >
                <p class="back" @click="goto">返回登录?</p>
              </div>
            </div>
          </el-form>
        </transition>
      </div>
    </div>
  </div>
</template>
<script>
import '@/assets/loginmain.css'
import '@/assets/loginright.css'
import Left from "@/components/left.vue";
export default {
  components: {Left},
  data() {
    return {
      username:"",
      password:"",
      idcard:"",
      show:false,
      subform:{}
    };
  },
  methods:{
    submit() {
      event.preventDefault();
      if(this.idcard===''||this.username===''||this.password==='')
      {
        this.$message.error("信息不完善");
        return;
      }
      this.subform={
        isroot:0,
        last_idcard:this.idcard,
        tea_id:this.username,
        password:this.password
      };

      const that = this;
      this.$axios.post('/user/enroll',this.subform)
          .then(res=>{
            if(res.data.code===1)
            {
              this.$message({
                message: res.data.msg,
                type: 'success'
              });
              this.$router.push('/login');
            }
            else
            {
              that.$message({
                message: res.data.msg,
                type: 'error'
              });
            }
          })
          .catch(error=>{
            console.log(error);
          })
          .then(function () {
          });
    },
    goto(){
      this.$router.back();
    }
  },
  mounted() {
    this.show=!this.show
  }
}
</script>
<style>
</style>
