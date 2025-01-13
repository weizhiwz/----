<template>
  <div class="password">
    <el-dialog
        title="增加新学期"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
        width="60%"
        style="left: 10%;border-radius: 20px">
      <el-form
          :rules="rules"
          :model="form"
          ref="form"
          >
        <el-form-item label="学期编号"  prop="sem_id" >
          <el-input v-model="form.sem_id" prop="sem_id" placeholder="输入学期编号">
            <template slot="prepend">sem</template>
            <i slot="suffix" class="el-icon-refresh-left" @click="random"></i>
          </el-input>
        </el-form-item>
        <el-form-item label="学期名称"  prop="sem_name" >
          <el-input v-model="form.sem_name" placeholder="输入学期名称"  ></el-input>
        </el-form-item>
        <el-form-item label="学期开始日期" prop="begindate">
          <el-date-picker v-model="form.begindate" placeholder="日期" ></el-date-picker>
        </el-form-item>
        <el-form-item label="学期所含周数" prop="week">
          <el-input-number v-model="form.week" placeholder="0"  ></el-input-number>
        </el-form-item>
        <el-form-item label="一天所含课节数" prop="dayclass">
          <el-input-number v-model="form.dayclass" placeholder="0"  ></el-input-number>
        </el-form-item>
        <el-form-item label="是否是当前学期" prop="isnow">
          <el-checkbox v-model="form.isnow" placeholder="0"  ></el-checkbox>
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
  name:"AddSemester",
  data() {
    return {
      searchText:"",
      update:{},
      tableData:[
      ],
      value:'',
      form:{
        isnow:0,
        sem_id:""
      },
      none:"",
      rules:{
        sem_id:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        sem_name:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        begindate:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        week:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        dayclass:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        isnow:[
          {required:true,message:'不能为空',trigger:'blur'}
        ]
      },
    }
  },
  methods:{
    random(){
      const randomNumber = Math.floor(Math.random() * 900000) + 100000;
      // 转换为字符串并返回
      this.form.sem_id=randomNumber.toString();
    },
    add(){
      this.dialogVisible=true;
    },
    handleClose(done) {
      this.$confirm('您做出的更改尚未提交,确认关闭？')
          .then(_ => {
            this.form={
              isnow:0,
                  sem_id:""
            };
            done();
          })
          .catch(_ => {});
    },
    cancel() {
      this.form={
        isnow:0,
        sem_id:""
      };
      this.infoform={};
      this.infoshow=false;
      this.dialogVisible=false;
    },
    actualadd() {
      let that = this;
      this.$refs.form.validate((valid) => {
        if (valid) {
          //调用接口保存
          this.$axios.post('/semester/add', this.form)
              .then(res => {
                this.$alert(res.data.msg, "温馨提示", {
                  confirmButtonText: "确定"
                },);

                if (res.data.code === 1) {
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
        }
        else {
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
