<template>
  <div class="user">
    <div class="title">
      <p style="position: relative;top: 2%;margin: 15px;font-weight: bold;font-size: 20px">人员管理</p>
      <div style="display: flex;">
        <el-input
            type="text"
            prefix-icon="el-icon-search"
            v-model="searchText"
            placeholder="请输入工号"
            style="width: 270px; cursor: pointer"
            @enter="handleSearch"
            class="search"
        ></el-input>
        <el-button style="height:100%;margin: 15px 15px;" type="primary" @click="handleSearch">搜索</el-button>
        <el-button style="height:100%;margin: 15px auto;position:relative;left:160px" @click="add">新增</el-button>
      </div>
      <div class="content">
        <el-table
            :data="tableData"
            stripe
            height="520px"
            style="width: 100%">
          <el-table-column
              prop="id"
              label="序号"
              width="80">
          </el-table-column>
          <el-table-column
              prop="tea_id"
              label="工号"
              width="150">
          </el-table-column>
          <el-table-column
              prop="isroot"
              label="是否为管理员"
              width="150">
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作">
            <template slot-scope="scope" >
              <el-button type="text" size="small" :style="{ color: scope.row.isroot==='是' ? '#ff2121' : '#21b1ff' }" @click="changerole(scope.row)">
                <i class="el-icon-user-solid"></i>  {{ scope.row.isroot==='是' ? '取消管理员' : '设为管理员' }}
              </el-button>
              <el-button type="text" size="small" @click="updaterow(scope.row)">编辑</el-button>
              <el-button @click="deleterow(scope.row)" type="text" size="small" style="color: #ff2121">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <Add :visible.sync="addVisible"  @confirm="refresh"></Add>
      <div class="password">
        <el-dialog
            title="当前选择的老师信息"
            :visible.sync="editVisible"
            width="50%"
            left="50%">
          <el-form
              :model="infoform">
            <el-form-item label="工号">
              <el-input v-model="infoform.tea_id" prop="tea_id" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="姓名">
              <el-input v-model="infoform.name" placeholder="当前没有信息"  ></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-select v-model="infoform.gender">
                <el-option v-for="gender in genders" :key="gender.value" :label="gender.label" :value="gender.value">{{ gender.label }}</el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="年龄">
              <el-input-number v-model="infoform.age" :min="0" :max="120">s</el-input-number>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="infoform.password" placeholder="隐私数据(默认不修改)" type="password"></el-input>
            </el-form-item>
            <el-form-item label="身份证后六位">
              <el-input v-model="infoform.last_idcard" placeholder="隐私数据(默认不修改)" type="password"></el-input>
            </el-form-item>
          </el-form>
          <div style="margin: 25px auto;width: 100%;display: flex;justify-content: flex-end">
            <el-button @click="cancel">放弃</el-button>
            <el-button type="primary" @click="actualupdate">提交表单信息</el-button>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>

</template>
<script>
import Add from "@/components/adddialog.vue";

export default {
  components:{Add},
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
      genders:[
        {
          value:1,
          label:'男'
        },
        {
          value: 0,
          label:'女'
        }
      ],
      addVisible:false,
      editVisible:false,
      value:'',
      none:"",
      infoshow:false,
      infoform:{
      },
      roottest:"设为管理员",
    }
  },
  computed:{
    nowtea_id(){
      return this.$store.state.id
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
        this.$axios.get("/admin/selectall")
            .then((res)=>{

              this.tableData=[];
              let idcounter=1;
              for(let i in res.data.data){
                let now={
                  id: idcounter++,
                  tea_id:res.data.data[i].tea_id,
                  isroot:res.data.data[i].isroot? '是' : '否'
                }
                // console.log(now);
                this.tableData.push(now);
              }
            })
      }
      else
      {
        // console.log("nihao");
        this.$axios.get("/admin/search",{
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
                  tea_id:res.data.data[i].tea_id,
                  isroot:res.data.data[i].isroot? '是' : '否'
                }
                // console.log(now);
                this.tableData.push(now);
              }
            })
      }
    },
    changerole(e){
      if(e.tea_id===this.nowtea_id)
      {
        alert("不能对自己进行此项操作");
        return;
      }
      this.update={
        teacher:{
          tea_id:e.tea_id,
          isroot:e.isroot==="是"?0:1
        },
        info:{}
      };
      let that = this;

      this.$axios.post("/admin/update",this.update)
          .then((res)=>{
            this.cancel();
            this.handleSearch(this.searchText);
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
      // console.log(e.tea_id);
      if(e.tea_id===this.nowtea_id)
      {
        alert("不能对自己进行此项操作");
        return;
      }
      let that = this;
      this.$confirm("确定删除教师"+e.tea_id+"吗?")
          .then(_ => {
            this.$axios.post("/admin/delete",{tea_id : e.tea_id})
                .then((res)=>{

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
        tea_id:e.tea_id,
      }
      this.$axios.get("/admin/selectone",
          {
            params: {
              tea_id:e.tea_id
            }
          })
          .then((res)=>{
            // console.log(res.data);
            this.infoform.isroot=e.isroot==="是"?1:0;
            if(res.data.code===1)
            {
              let data=res.data.data;
              this.infoform={
                tea_id:e.tea_id,
                name:data.name,
                gender:data.gender?1:0,
                age:data.age
              }
            }
            // console.log(this.infoform);
          })
          .catch(function (error) {
            console.log(error);
            // this.$message({
            //   message: '网络连接错误',
            //   type: 'error'
            // });
          });
    },
    actualupdate(){
      let that = this;
      this.update={
        teacher:{
          password:this.infoform.password,
          last_idcard:this.infoform.last_idcard,
        },
        info:{
          tea_id:this.infoform.tea_id,
          age:this.infoform.age,
          gender:this.infoform.gender,
          name:this.infoform.name
        }
      };
      // console.log(this.update);
      this.$axios.post("/admin/update",this.update)
          .then((res)=>{
            this.cancel();
            this.$alert(res.data.msg,"温馨提示",{
              confirmButtonText:"确定"
            },);
          })
          .catch(function (error) {
            this.cancel();
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
