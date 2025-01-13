<template>
  <div class="user">
    <div class="title">
      <p style="position: relative;top: 2%;margin: 15px;font-weight: bold;font-size: 20px">实验室管理</p>
      <div style="display: flex;">
        <el-input
            type="text"
            prefix-icon="el-icon-search"
            v-model="searchText"
            placeholder="请输入实验室编号"
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
            height="520px"
            stripe
            style="width: 100%">
          <el-table-column
              prop="id"
              label="序号"
              width="80">
          </el-table-column>
          <el-table-column
              prop="lab_id"
              label="实验室编号"
              width="100">
          </el-table-column>
          <el-table-column
              prop="lab_name"
              label="名称"
              width="150">
          </el-table-column>
          <el-table-column
              prop="location"
              label="地点"
              width="150">
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作">
            <template slot-scope="scope" >
              <el-button type="text" size="small" @click="updaterow(scope.row)">编辑</el-button>
              <el-button @click="deleterow(scope.row)" type="text" size="small" style="color: #ff2121">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <AddLab :visible.sync="addVisible"  @confirm="refresh"></AddLab>
    <div class="password">
      <el-dialog
          title="当前选择的实验室信息"
          :visible.sync="editVisible"
          width="50%"
          left="50%">
        <el-form
            :model="infoform">
          <el-form-item label="实验室编号">
            <el-input v-model="infoform.lab_id" prop="lab_id" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="实验室名称">
            <el-input v-model="infoform.lab_name" placeholder="当前没有信息"  ></el-input>
          </el-form-item>
          <el-form-item label="实验室位置">
            <el-input v-model="infoform.location" placeholder="当前没有信息"  ></el-input>
          </el-form-item>
          <el-form-item label="实验室信息">
          </el-form-item>
          <editor ref="editorComponent" :now="now"/>
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
import AddLab from "@/components/addlab.vue";
import editor from "@/components/editor.vue";
export default {
  components:{AddLab,editor},
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
      if(this.searchText===""||e===""){
        this.$axios.get("/lab/selectall")
            .then((res)=>{
              this.tableData=[];
              let idcounter=1;
              for(let i in res.data.data){
                let now={
                  id: idcounter++,
                  lab_id:res.data.data[i].lab_id,
                  lab_name:res.data.data[i].lab_name,
                  location:res.data.data[i].location,
                  // lab_isfull:res.data.data[i].lab_isfull? '是' : '否'
                }
                this.tableData.push(now);
              }
            })
      }
      else
      {

        this.$axios.get("/lab/search",{
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
                  lab_id:res.data.data[i].lab_id,
                  lab_name:res.data.data[i].lab_name,
                  location:res.data.data[i].location,
                  // lab_isfull:res.data.data[i].lab_isfull? '是' : '否'
                }

                this.tableData.push(now);
              }
            })
      }
    },
    add(){
      this.addVisible=true;
    },
    deleterow(e){

      let that = this;
      this.$confirm("确定删除实验室"+e.lab_id+"吗?")
          .then(_ => {
            this.$axios.post("/lab/delete",{lab_id : e.lab_id})
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
        lab_id:e.lab_id,
      }
      this.$axios.get("/lab/selectone",
          {
            params: {
              lab_id:e.lab_id
            }
          })
          .then((res)=>{
            // console.log(res.data);
            if(res.data.code===1)
            {
              let data=res.data.data;
              this.infoform={
                lab_id:e.lab_id,
                lab_name:data.lab_name,
                location:data.location,
                lab_info:data.lab_info
              };
              this.now=data.lab_info;

            }
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
      const editorContent=this.$refs.editorComponent.getContent();
      this.update={
        lab_id:this.infoform.lab_id,
        lab_name:this.infoform.lab_name,
        location:this.infoform.location,
        // lab_isfull:this.infoform.lab_isfull,
        lab_info:editorContent
      };
      // console.log(this.update);
      this.$axios.post("/lab/update",this.update)
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
