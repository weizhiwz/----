
<template>
  <div class="user">
    <div class="title">
      <p style="position: relative;top: 2%;margin: 15px;font-weight: bold;font-size: 20px">学期管理</p>
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
            :data="tableData"
            height="520px"
            stripe
            style="width: 100%">
          <el-table-column
              prop="id"
              label="序号"
              width="80">
          </el-table-column>
          <el-table-column
              prop="sem_id"
              label="学期编号"
              width="100">
          </el-table-column>
          <el-table-column
              prop="sem_name"
              label="名称"
              width="120">
          </el-table-column>
          <el-table-column
              prop="begindate"
              label="开始日期"
              width="150">
          </el-table-column>
          <el-table-column
              prop="week"
              label="总周数"
              width="150">
          </el-table-column>
          <el-table-column
              prop="dayclass"
              label="每天课数"
              width="150">
          </el-table-column>
          <el-table-column
              prop="isnow"
              label="当前学期"
              width="150">
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          width="200">
            <template slot-scope="scope" >
              <el-button type="text" size="small" @click="changerole(scope.row)" :style="{ color: scope.row.isnow==='是' ? '#ff2121' : '#21b1ff' }">
                {{ scope.row.isnow==="是" ? '取消当前学期' : '设为当前学期' }}
              </el-button>
              <el-button type="text" size="small" @click="updaterow(scope.row)">编辑</el-button>
              <el-button @click="deleterow(scope.row)" type="text" size="small" style="color: #ff2121">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <AddSemester :visible.sync="addVisible"  @confirm="refresh"></AddSemester>
    <div class="password">
      <el-dialog
          title="当前选择的学期信息"
          :visible.sync="editVisible"
          width="50%"
          left="50%">
        <el-form
            :model="infoform">
          <el-form-item label="学期编号">
            <el-input v-model="infoform.sem_id" prop="sem_id" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="学期名称">
            <el-input v-model="infoform.sem_name" placeholder="当前没有信息"  ></el-input>
          </el-form-item>
          <el-form-item label="学期开始日期">
            <el-date-picker v-model="infoform.begindate" ></el-date-picker>
            <p>TIPS:只能选择星期一哦，选择其他日期会自动设为本周周一</p>
          </el-form-item>
          <el-form-item label="学期所含周数">
            <el-input-number v-model="infoform.week" placeholder="当前没有信息"  ></el-input-number>
          </el-form-item>
          <el-form-item label="一天所含课节数">
            <el-input-number v-model="infoform.dayclass" placeholder="当前没有信息"  ></el-input-number>
          </el-form-item>
          <el-form-item label="是否是当前学期">
            <el-checkbox v-model="infoform.isnow" placeholder="当前没有信息"  ></el-checkbox>
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
import AddSemester from "@/components/addsemster.vue";

export default {
  components:{AddSemester},
  data() {
    return {
      searchText:"",
      update:{},
      tableData:[
      ],
      addVisible:false,
      editVisible:false,
      value:'',
      none:"",
      infoshow:false,
      infoform:{
      },
      roottest:"设为管理员",
      now:"",
    }
  },
  methods:{
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
        this.$axios.get("/semester/selectall")
            .then((res)=>{

              this.tableData=[];
              let idcounter=1;
              for(let i in res.data.data){
                let now={
                  id: idcounter++,
                  sem_id:res.data.data[i].sem_id,
                  sem_name:res.data.data[i].sem_name,
                  week:res.data.data[i].week,
                  begindate:res.data.data[i].begindate,
                  dayclass:res.data.data[i].dayclass,
                  isnow:res.data.data[i].isnow? '是' : '否'
                }
                // console.log(now);
                this.tableData.push(now);
              }
            })
      }
      else
      {

        this.$axios.get("/semester/search",{
          params:{
            id:this.searchText
          }
        })
            .then((res)=>{
              // console.log(res.data.data);
              this.tableData=[];
              let idcounter=1;
              for(let i in res.data.data){
                let now={
                  id: idcounter++,
                  sem_id:res.data.data[i].sem_id,
                  sem_name:res.data.data[i].sem_name,
                  week:res.data.data[i].week,
                  begindate:res.data.data[i].begindate,
                  dayclass:res.data.data[i].dayclass,
                  isnow:res.data.data[i].isnow? '是' : '否'
                }
                // console.log(now);
                this.tableData.push(now);
              }
            })
      }
    },
    changerole(e){
      console.log(e);
      if(e.isnow==='是')
      {
        alert("至少有一个当前学期");
        return;
      }
      this.update={
          sem_id:e.sem_id,
          isnow:e.isnow==="是"?0:1
      };
      let that = this;
      this.$axios.post("/semester/update",this.update)
          .then((res)=>{
            this.cancel();
            if(res.data.code===0){
              this.$alert(res.data.msg, '温馨提示', {
                confirmButtonText: '确定'
              });
            }
            this.handleSearch(this.searchTexts);
          })
          .catch(function (error) {
            // this.cancel();
            this.handleSearch(this.searchText);
            console.log(error);
            that.$message({
              message: '网络连接错误',
              type: 'error'
            });
          });
    },
    add(){
      this.addVisible=true;
    },
    deleterow(e){
      console.log(e);
      if(e.isnow==='是')
      {
        alert("无法删除当前学期");
        return;
      }
      let that = this;
      this.$confirm("确定删除学期"+e.sem_id+"吗?")
          .then(_ => {
            this.$axios.post("/semester/delete",{sem_id : e.sem_id})
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
      // console.log(e);
      this.infoform={
        sem_id:e.sem_id,
        sem_name:e.sem_name,
        week:e.week,
        begindate:e.begindate,
        dayclass:e.dayclass,
        isnow:e.isnow==='是'
      }
    },
    actualupdate(){
      let that = this;
      this.update={
        sem_id:this.infoform.sem_id,
        sem_name:this.infoform.sem_name,
        week:this.infoform.week,
        begindate:this.infoform.begindate,
        dayclass:this.infoform.dayclass,
        isnow:this.infoform.isnow,
      };

      this.$axios.post("/semester/update",this.update)
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
