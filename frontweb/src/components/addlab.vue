<template>
  <div class="password">
    <el-dialog
        title="增加新实验室"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
        width="60%"
        style="left: 10%;border-radius: 20px">
      <el-form
          :model="form"
          ref="form"
      :rules="rules">
        <el-form-item label="添加实验室的编号" prop="lab_id">
          <el-input v-model="form.lab_id" prop="lab_id">
            <template slot="prepend">lab</template>
            <i slot="suffix" class="el-icon-refresh-left" @click="random"></i>
          </el-input>
        </el-form-item>
        <el-form-item label="实验室名称" prop="lab_name">
          <el-input v-model="form.lab_name" prop="lab_name"></el-input>
        </el-form-item>
        <el-form-item label="实验室位置" prop="location">
          <el-input v-model="form.location" prop="location"></el-input>
        </el-form-item>
        <el-form-item label="实验室详情">
        </el-form-item>
        <editor ref="editorComponent"/>
      </el-form>
      <div style="margin: 25px auto;width: 100%;display: flex;justify-content: flex-end">
        <el-button @click="cancel">放弃</el-button>
        <el-button type="primary" @click="actualadd">提交表单信息</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import editor from "@/components/editor.vue";

export default {
  components:{editor},
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  name:"AddLab",
  data() {
    return {
      searchText:"",
      update:{},
      tableData:[
      ],
      value:'',
      form:{
        lab_isfull:0,
        lab_id:""
      },
      none:"",
      rules:{
        location:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        lab_name:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        lab_id:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        lab_info:[
          {required:true,message:'不能为空',trigger:'blur'}
        ]
      },
    }
  },
  methods:{
    random(){
      const randomNumber = Math.floor(Math.random() * 900000) + 100000;
      // 转换为字符串并返回
      this.form.lab_id=randomNumber.toString();
    },
    add(){
      this.dialogVisible=true;
    },
    handleClose(done) {
      this.$confirm('您做出的更改尚未提交,确认关闭？')
          .then(_ => {
            this.form={
              lab_isfull:0,
                  lab_id:""
            };
            done();
          })
          .catch(_ => {});
    },
    cancel() {
      this.form={
        lab_isfull:0,
        lab_id:""
      };
      this.infoform={};
      this.infoshow=false;
      this.dialogVisible=false;
    },
    actualadd(){
      let that = this;
      this.form.lab_info=this.$refs.editorComponent.getContent();
      this.$refs.form.validate((valid) => {
        if (valid) {
          //调用接口保存
          this.$axios.post('/lab/add',this.form)
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
