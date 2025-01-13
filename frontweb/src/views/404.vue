// error404.vue
<template>
  <div class="main">
    <p class="now">404</p>
    <div class="bullshit">
      <p class="text">努力更新中，敬请期待...</p>
      <p class="text">您可以点击下面按钮返回</p>
      <el-button @click="handleBack" type="info" style="margin: 5px" >返回</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Page404",

  computed: {
    message() {
      return "没有找到你要访问的页面。。。";
    },
    token(){
      if(this.$store.state.token===undefined) return '';
      else return this.$store.state.token;
    }
  },
  methods: {
    handleBack() {
      if(this.token==='')
      {
        this.$router.push("/login");
      }
      else this.$router.push("/AdminHome/Home");
    },
  },
};
</script>

<style>
.now{
font-size: 150px;
  margin-right: 100px;
  font-weight: bolder;
}
.main{
  height: 100vh;
  width: 100%;
  display: flex;
  text-align: center;
  justify-content: center;
  align-items: center;
}
.text{
  font-weight: lighter;
}
</style>

