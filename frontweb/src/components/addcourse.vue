<template>
  <div class="password">
    <el-dialog
        title="增加新课程"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
        width="60%"
        style="left: 10%;border-radius: 20px">
      <el-form
          :model="form"
          ref="form"
          :rules="rules">
        <el-form-item label="课程编号" prop="Course_id">
          <el-input v-model="form.Course_id" prop="Course_id">
            <template slot="prepend">cou</template>
            <i slot="suffix" class="el-icon-refresh-left" @click="random"></i>
          </el-input>
        </el-form-item>
        <el-form-item label="课程名称" prop="Course_name">
          <el-input v-model="form.Course_name" prop="Course_name"></el-input>
        </el-form-item>
        <el-form-item label="上课班级" prop="Class_">
          <el-input v-model="form.Class_" prop="Class_"></el-input>
        </el-form-item>
        <el-form-item label="人数" prop="People_num">
          <el-input-number v-model="form.People_num" prop="People_num" :max="200" :min="1" style="margin: 0 30px"></el-input-number>
        </el-form-item>
        <el-form-item label="是否必修" prop="Status">
          <el-radio-group v-model="form.Status">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="所属学期" prop="Belong_num">
          <el-select v-model="form.Belong_num" placeholder="请选择">
            <el-option
                v-for="item in this.option"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              <span style="float: left">{{ item.label }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属学科" prop="Subject">
          <el-select v-model="form.Subject" placeholder="请选择">
            <el-option
                v-for="item in this.suboption"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任课教师" prop="tea_id">
          <el-select v-model="form.tea_id" placeholder="请选择">
            <el-option
                v-for="item in this.teaoption"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              <span style="float: left">{{ item.label }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="实验环境" prop="Tool">
          <el-input v-model="form.Tool" prop="Tool" type="textarea" @blur="handleBlur" placeholder="无环境要求"></el-input>
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
  name:"Addcourse",
  data() {
    return {
      searchText:"",
      update:{},
      tableData:[
      ],
      teavalue:'',
      semvalue:'',
      form:{
        lab_isfull:0,
        Course_id:"",
      },
      none:"",
      option:[],
      teaoption:[],
      suboption:[
        {
          value:"硬件类",
          label:"硬件类"
        },{
          value:"软件类",
          label:"软件类"
        },
      ],
      rules:{
        Course_id:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        Course_name:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        Class_:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        People_num:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        Status:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        Belong_num:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        Tool:[
          {trigger:'blur'}
        ],
        tea_id:[
          {required:true,message:'不能为空',trigger:'blur'}
        ],
        Subject:[
          {required:true,message:'不能为空',trigger:'blur'}
        ]
      },
    }
  },
  methods:{
    handleBlur() {
      if (!this.form.Tool) {
        this.form.Tool = "无环境要求";
      }
      if (!this.form.Status) {
          this.form.Status = false;
      }
    },
    random(){
      const randomNumber = Math.floor(Math.random() * 900000) + 100000;
      // 转换为字符串并返回
      this.form.Course_id=randomNumber.toString();
    },
    add(){
      this.dialogVisible=true;
    },
    handleClose(done) {
      this.$confirm('您做出的更改尚未提交,确认关闭？')
          .then(_ => {
            this.form={
              lab_isfull:0,
                  Course_id:"",
            }
            done();
          })
          .catch(_ => {});
    },
    cancel() {
      this.form={
        lab_isfull:0,
        Course_id:"",
      };
      this.infoform={};
      this.infoshow=false;
      this.dialogVisible=false;
    },
    actualadd()
    {
      let that = this;
      console.log(this.form);
      this.$refs.form.validate((valid) => {
        if (valid)
        {
          //调用接口保存
          this.$axios.post('/Course/add',this.form)
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
              message: '信息冲突或不完善',
              type: 'error'
            });
          });
        }
      });//验证
    }
  },
  mounted() {
    this.$axios.get("/semester/selectall")
        .then((res)=>{
          // console.log(res);
          for(let i in res.data.data){
            let op={
              value:res.data.data[i].sem_id,
              label:res.data.data[i].sem_name,
            }
            // console.log(now);
            this.option.push(op);
          }
        })
    this.$axios.get("/admin/selectall")
        .then((res)=>{
          for(let i in res.data.data){
            let op={
              value:res.data.data[i].tea_id,
              label:res.data.data[i].tea_id,
            }
            this.teaoption.push(op);
          }
        })

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
