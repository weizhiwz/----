// const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true,
// })
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,  // 关闭语法检查 防止不必要的语法报错
  // 配置devServer
  devServer: {
    host: 'localhost', // 项目运行的ip
    port: 8888, // 项目运行的端口号
    client: {
      overlay: false,
    },
  }
})







