<template>
  <div class="user">
    <div class="title">
      <p style="position: relative;top: 2%;margin: 15px;font-weight: bold;font-size: 20px">课程管理</p>
      <div style="display: flex;">
        <el-input
            type="text"
            prefix-icon="el-icon-search"
            v-model="searchText"
            placeholder="请输入"
            style="width: 270px; cursor: pointer"
            @enter="handleSearch"
            class="search"
        ></el-input>
        <el-button style="height:100%;margin: 15px 15px;" type="primary" @click="handleSearch">搜索</el-button>
        <el-button style="height:100%;margin: 15px auto;position:relative;left:160px" @click="add">新增</el-button>
      </div>
      <div class="content" style="width: 80%">
        <el-table
            :data="filteredTableData"
            height="520px"
            stripe
            style="width: 100%">
          <el-table-column
              prop="id"
              label="序号"
              width="40">
          </el-table-column>
          <el-table-column
              prop="Course_id"
              label="课程编号"
              width="100">
          </el-table-column>
          <el-table-column
              prop="Course_name"
              label="课程名称"
              width="130">
          </el-table-column>
          <el-table-column
              prop="Class_"
              label="所属班级"
              width="100">
          </el-table-column>
          <el-table-column
              prop="People_num"
              label="选课人数"
              width="100">
          </el-table-column>
          <el-table-column
              prop="Status"
              label="课程性质"
              width="100">
            <template slot-scope="scope">
              {{scope.row.Status?"必修":"任选"}}
            </template>
          </el-table-column>
          <el-table-column
              prop="Belong_num"
              label="学期编号"
              width="100">
          </el-table-column>
<!--          <el-table-column-->
<!--              prop="Tool"-->
<!--              label="实验环境"-->
<!--              width="100">-->
<!--          </el-table-column>-->
          <el-table-column
              prop="Subject"
              label="所属学科"
              width="80">
          </el-table-column>
          <el-table-column
              prop="tea_id"
              label="教师编号"
              width="100">
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
              width="200">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="updaterow(scope.row)">编辑</el-button>
              <el-button @click="deleterow(scope.row)" type="text" size="small" style="color: #ff2121">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <AddCourse :visible.sync="addVisible"  @confirm="refresh"></AddCourse>
    <div class="password">
      <el-dialog
          title="当前选择的学期信息"
          :visible.sync="editVisible"
          width="50%"
          left="50%">
        <el-form :model="infoform" ref="infoform">
          <el-form-item label="课程编号" prop="Course_id">
            <el-input v-model="infoform.Course_id" prop="Course_id"></el-input>
          </el-form-item>
          <el-form-item label="课程名称" prop="Course_name">
            <el-input v-model="infoform.Course_name" prop="Course_name"></el-input>
          </el-form-item>
          <el-form-item label="上课班级" prop="Class_">
            <el-input v-model="infoform.Class_" prop="Class_"></el-input>
          </el-form-item>
          <el-form-item label="人数" prop="People_num">
            <el-input-number v-model="infoform.People_num" prop="People_num" :max="200" :min="1" style="margin: 0 30px"></el-input-number>
          </el-form-item>
          <el-form-item label="是否必修" prop="Status">
            <el-checkbox v-model="infoform.Status" prop="Status" style="margin: 0 30px"></el-checkbox>
          </el-form-item>
          <el-form-item label="所属学期" prop="Belong_num">
            <el-select v-model="infoform.Belong_num" placeholder="请选择">
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
            <el-select v-model="infoform.Subject" placeholder="请选择">
              <el-option
                  v-for="item in this.suboption"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="任课教师" prop="tea_id">
            <el-select v-model="infoform.tea_id" placeholder="请选择">
              <el-option
                  v-for="item in this.teaoption"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="实验环境" prop="Tool">
            <el-input v-model="infoform.Tool" prop="Tool" type="textarea"></el-input>
          </el-form-item>
        </el-form>
        <div style="margin: 25px auto;width: 100%;display: flex;justify-content: flex-end">
          <el-button @click="cancel">放弃</el-button>
          <el-button type="primary" @click="actualupdate">提交表单信息</el-button>
        </div>
      </el-dialog>
    </div>
  </div>

</template>
<script>
import AddCourse from "@/components/addcourse.vue";

export default {
  components:{AddCourse},
  data() {
    return {
      searchText:"",
      update:{},
      tableData:[],
      currentTeacherId:this.getCurrentUserTeaId(),
      addVisible:false,
      editVisible:false,
      value:'',
      none:"",
      infoshow:false,
      infoform:{
      },
      now:"",
      suboption:[
        {
          value:"硬件类",
          label:"硬件类"
        },{
          value:"软件类",
          label:"软件类"
        }
      ],
      teaoption:[],
      option:[],
    }
  },
  computed: {
    filteredTableData() {
      if (this.currentTeacherId.charAt(0).toLowerCase() === 't') {
        return this.tableData.filter(item => item.tea_id === this.currentTeacherId);
      } else {
        return this.tableData;
      }
    },
    currentUserId() {
      return this.getCurrentUserTeaId();
    }
  },
  methods:{
    getCurrentUserTeaId() {
      return localStorage.getItem('id') || null;
    },
    refresh() {
      this.handleSearch(this.none);
    },
    cancel() {
      this.form={};
      this.infoform={};
      this.addVisible=false;
      this.editVisible=false;
    },
    handleSearch(e) {
      // console.log(this.searchText);
      if(this.searchText===""||e===""){
        this.$axios.get("/Course/selectall")
            .then((res)=>{
              console.log(res);
              this.tableData=[];
              let idcounter=1;
              for(let i in res.data.data){
                let now={
                  id: idcounter++,
                  Course_id: res.data.data[i].course_id,
                  Course_name: res.data.data[i].Course_name,
                  Class_: res.data.data[i].Class_,
                  People_num: res.data.data[i].People_num,
                  Status: res.data.data[i].Status,
                  Belong_num: res.data.data[i].Belong_num,
                  Tool: res.data.data[i].Tool,
                  Subject: res.data.data[i].subject,
                  tea_id: res.data.data[i].tea_id
                }
                // console.log(now);
                this.tableData.push(now);
              }
            })
      }
      else
      {
        this.$axios.get("/Course/search",{
          params:{
            id:this.searchText
          }
        })
            .then((res)=>{
              this.tableData=[];
              let idcounter=1;
              for(let i in res.data.data){
                let now={
                  id: idcounter++,
                  Course_id: res.data.data[i].course_id,
                  Course_name: res.data.data[i].Course_name,
                  Class_: res.data.data[i].Class_,
                  People_num: res.data.data[i].People_num,
                  Status: res.data.data[i].Status,
                  Belong_num: res.data.data[i].Belong_num,
                  Tool: res.data.data[i].Tool,
                  Subject: res.data.data[i].subject,
                  tea_id: res.data.data[i].tea_id
                }
                // console.log(now);
                this.tableData.push(now);
              }
            })
      }
    },
    add(){
      this.addVisible=true;
    },
    deleterow(e){
      // console.log(e.tea_id);
      let that = this;
      this.$confirm("确定删除课程"+e.Course_id+"吗?")
          .then(_ => {
            this.$axios.post("/Course/delete",{Course_id : e.Course_id})
                .then((res)=>{
                  // console.log(res);
                  this.$alert(res.data.msg,"温馨提示",{
                    confirmButtonText:"确定",
                    callback: action => {
                      this.handleSearch(this.none);
                    }
                  },);
                  this.dialogVisible=false;
                })
                .catch(function (error) {
                  console.log(error);
                  that.$message({
                    message: '网络连接错误',
                    type: 'error'
                  });
                });
          })
          .catch(_ => {});
    },
    updaterow(e){
      this.editVisible=true;
      console.log(e);
      this.infoform={
        Course_id: e.Course_id,
        Course_name: e.Course_name,
        Class_: e.Class_,
        People_num: e.People_num,
        Status: e.Status,
        Belong_num: e.Belong_num,
        Tool: e.Tool,
        Subject: e.Subject,
        tea_id: e.tea_id
      }
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
            // console.log(res);
            for(let i in res.data.data){
              let op={
                value:res.data.data[i].tea_id,
                label:res.data.data[i].tea_id,
              }
              // console.log(now);
              this.teaoption.push(op);
            }
          })
    },
    actualupdate(){
      let that = this;
      this.update={
        Course_id: this.infoform.Course_id,
        Course_name: this.infoform.Course_name,
        Class_: this.infoform.Class_,
        People_num: this.infoform.People_num,
        Status: this.infoform.Status,
        Belong_num: this.infoform.Belong_num,
        Tool: this.infoform.Tool,
        Subject: this.infoform.Subject,
        tea_id: this.infoform.tea_id
      };

      this.$axios.post("/Course/update",this.update)
          .then((res)=>{
            that.cancel();
            this.handleSearch(this.none);
            that.$alert(res.data.msg,"温馨提示",{
              confirmButtonText:"确定"
            },);
          })
          .catch(function (error) {
            that.cancel();
            console.log(error);
            that.$message({
              message: '网络连接错误',
              type: 'error'
            });
          });
    },
  },
  mounted() {
    this.$router.afterEach((to, from, next) => {
      window.scrollTo(0, 0)
    })
    this.handleSearch(this.searchText);
  }
}
</script>
<style>
@import '../../../assets/table.css';

</style>
