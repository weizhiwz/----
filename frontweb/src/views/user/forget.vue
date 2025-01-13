<template>
  <div class="page-box">
    <div class="whiteback">
      <left></left>
      <div class="right">
        <transition name="el-fade-in-linear">
          <el-form class="form" v-if="show">
            <p class="sign">找回密码 FORGET PASSWORD</p>
            <el-form-item>
              <p style=" font-size:15px;margin: 0;font-weight: lighter">工号 JOB NUMBER</p>
              <input v-model="username" class="input">
            </el-form-item>
            <el-form-item>
              <p style="font-size:15px;margin: 0;font-weight: lighter">身份证后六位 PASSWORD</p>
              <input v-model="idcard" class="input" type="password">
            </el-form-item>
            <div style="height: 45px;display: flex">
              <p class="hoveroptions" @click="goto(login)">返回登录?</p>
              <p class="hoveroptions" @click="goto(enroll)">现在注册?</p>
            </div>
            <el-form-item>
              <button class="submit" @click="submit">立即找回</button>
            </el-form-item>
          </el-form>
        </transition>
      </div>
    </div>
    <div class="password">
      <el-dialog
          title="设置密码"
          :visible.sync="vis"
          width="30%"
          :before-close="handleClose">
        <div style="margin: -30px"></div>
          <el-form v-if="show">
              <p style="color: #6b6969">请输入密码</p>
              <el-input v-model="password" type="password"></el-input>
              <p style="color: #6b6969">请再次输入密码</p>
              <el-input v-model="repeatpassword" type="password"></el-input>
          </el-form>
        <div style="margin: 25px auto;width: 100%;display: flex;justify-content: flex-end">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="actual">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import '@/assets/loginmain.css'
import '@/assets/loginright.css'
import '@/components/left.vue'
import Left from "@/components/left.vue";
// import './less/common.less'
export default {
  components: {Left},
  data() {
    return {
      options:[
        {
          value:1,
          label:'管理员'
        },
        {
          value:0,
          label:'教师'
        },
      ],
      username:"",
      idcard:"",
      password:"",
      repeatpassword:"",
      value:"",
      enroll:"enroll",
      login:"login",
      show:false,
      usepwd:true,
      vis: false,
      form:{}
    };
  },
  methods:{
    submit() {
      event.preventDefault();
      if(this.username===''||this.idcard==='')
      {
        this.$message.error("信息不完善");
        return;
      }
      if(this.usepwd)
      {
        this.vis = true;
      }
      else
      {
        this.form={
          tea_id:this.username,
          password:this.idcard,
          last_idcard:this.idcard,
        };
        this.toback();
      }
    },
    toback(){

      let that = this;
      this.$axios.post('/user/forget',this.form)
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
              this.$message({
                message: res.data.msg,
                type: 'error'
              });
            }
          })
          .catch(error=>{

            that.$message({
              message: '网络连接失败',
              type: 'error'
            });
          })
          .then(function () {
          });
    },
    goto(e){
      this.$router.push('/'+e);
    },
    handleClose(done) {
      this.vis=false;
    },
    cancel() {
      this.password="";
      this.vis=false;
    },
    actual(){
      if(this.repeatpassword!==this.password)
      {
        this.$message.error("两次输入密码不相同");
        this.password='';
        this.repeatpassword='';
        return;
      }
      this.form={
        tea_id:this.username,
        password:this.password,
        last_idcard:this.idcard,
      };
      this.toback();
      this.password="";
      this.vis=false;
    }
  },
  mounted() {
    this.show=!this.show;
  }
}
</script>
<style>
.password {
  z-index: 9999;
}
.whiteback{
  z-index:0;
}

</style>
