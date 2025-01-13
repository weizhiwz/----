<template>
  <div>
    <div class="aside">
      <div class="left">
        <i class="iconfont icon-yonghumenrenqun"></i>
        <div class="font">
          <p style="margin:15px 10px;font-weight: 1000">教师实验室<br>排课系统</p>
        </div>
      </div>
      <el-menu
          router
          :default-active="this.$route.path"
          class="el-menu-vertical-demo"
          background-color="#fff"
          text-color="black"
          active-text-color="#5B86E5"
      >
<!--        首页-->
        <el-menu-item index="/AdminHome/Home">
          <i class="el-icon-house"></i>
          <span slot="title">首页</span>
        </el-menu-item>
<!--        实验管理-->
        <el-menu-item index="/AdminHome/lab">
          <i class="el-icon-files"></i>
          <span slot="title">实验管理</span>
        </el-menu-item>
<!--        高级权限-->
        <el-submenu index="3" v-if="root">
          <template slot="title">
            <i class="el-icon-collection"></i>
            <span>高级权限</span>
          </template>
          <!-- 一组菜单项 -->
          <el-menu-item-group>
            <el-menu-item index="/AdminHome/usercontrol">人员管理</el-menu-item>
            <el-menu-item index="/AdminHome/class">课程管理</el-menu-item>
            <el-menu-item index="/AdminHome/semester">学期管理</el-menu-item>
            <el-menu-item index="/AdminHome/classroom">实验室管理</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-menu-item index="/AdminHome/setting">
          <i class="el-icon-setting"></i>
          <span slot="title">个人信息设置</span>
        </el-menu-item>
      </el-menu>
    </div>
    <router-view/>
  </div>
</template>
<script>
import '@/assets/aside.css'
import '@/components/panel.vue'
export default {
  data() {
    return {
      defaultActive: this.$route.path,
      root:false,
    };
  },
  methods:{

  },
  created() {
    this.$router.afterEach((to) => {
      if (to.path !== '/AdminHome/Home') {
        this.defaultActive = to.path;
      }
    });
  },
  computed:{
    nowtea_id(){
      return this.$store.state.id
    }
  },
  mounted() {
      this.$router.afterEach((to, from, next) => {
        window.scrollTo(0, 0)
      })

    this.$axios.post("/user/menu",this.nowtea_id)
        .then((res)=>{
          this.root=res.data.data;
        })
        .catch(function (error) {
          this.$message({
            message: '网络连接错误',
            type: 'error'
          });
        });
    this.$router.push('/AdminHome/Home');
  }
}
</script>
<style>
</style>
