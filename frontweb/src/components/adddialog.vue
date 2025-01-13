<template>
  <div class="password">
    <el-dialog
        title="增加新老师"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
        width="50%"
        left="50%">
      <el-form
          :rules="rules"
          :model="form"
          ref="form">
        <el-form-item label="添加老师的身份" prop="isroot">
          <el-select v-model="form.isroot"  prop="isroot">
            <el-option v-for="option in options" :key="option.value" :label="option.label" :value="option.value">{{ option.label }}</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工号" prop="tea_id">
          <el-input v-model="form.tea_id" prop="tea_id">
            <template slot="prepend">tea</template>
            <i slot="suffix" class="el-icon-refresh-left" @click="random"></i>
          </el-input>
        </el-form-item>
        <el-form-item label="身份证后六位" prop="last_idcard">
          <el-input v-model="form.last_idcard" prop="last_idcard" type="password"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>
      </el-form>
      <div style="margin: 25px auto;width: 100%;display: flex;justify-content: flex-end">
        <el-button @click="cancel">放弃</el-button>
        <el-button type="primary" @click="actualadd">提交表单信息</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  name:"Add",
  data() {
    return {
      searchText:"",
      update:{},
      tableData:[
      ],
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
      value:'',
      form:{
        tea_id:"",
        last_idcard:"",
        password:"",
        isroot:""
      },
      rules:{
        tea_id:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        password:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        isroot:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        last_idcard:[
          {required:true,message:'不能为空',trigger:'blur'}
        ]
      },
      none:"",
    }
  },
  methods:{
    random(){
      const randomNumber = Math.floor(Math.random() * 900000) + 100000;
      // 转换为字符串并返回
      this.form.tea_id=randomNumber.toString();
    },
    add(){
      this.dialogVisible=true;
    },
    handleClose(done) {
      this.$confirm('您做出的更改尚未提交,确认关闭？')
          .then(_ => {
            this.form={
              tea_id:"",
                  last_idcard:"",
                  password:"",
                  isroot:""
            };
            done();
          })
          .catch(_ => {});
    },
    cancel() {
      this.form={
        tea_id:"",
        last_idcard:"",
        password:"",
        isroot:""
      };
      this.infoform={};
      this.infoshow=false;
      this.dialogVisible=false;
    },
    actualadd(){
      var that=this;
      this.$refs.form.validate((valid) => {
        if (valid) {
          //调用接口保存
          this.$axios.post('/admin/add',this.form)
              .then(res=>{

                this.$alert(res.data.msg,"温馨提示",{
                  confirmButtonText:"确定"
                },);

                if(res.data.code===1)
                {
                  this.cancel();
                  this.$emit('confirm');
                }
              })
              .catch(function (error) {
                console.log(error);
                that.$message({
                  message: '信息不完善或冲突',
                  type: 'error'
                });
              });
        } else {
          this.$message.error('请完善表单相关信息！');
          return false;
        }
      });
    }
  },
  mounted() {
  },
  computed:{
    dialogVisible:{
      get:function (){
        return this.visible
      },
      set:function (val){
        this.$emit('update:visible',val)
      }
    }
  }

}
</script>
<style>
.el-icon-refresh-left{
  font-size: 20px;
  margin: 10px;
}
.el-icon-refresh-left:hover{
  color: #3d7aef;
}
</style>
