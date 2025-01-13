
<template>
  <div class="Home">
    <div class="classtable">
      <p style="position: relative;top: 2%;margin: 15px;font-weight: bold;font-size: 20px">个人信息设置</p>
      <div class="setting">
        <el-descriptions :column="2" border style="width: 70%">
          <el-descriptions-item>
            <template slot="label">
              教师编号
            </template>
            {{ this.infoform.tea_id }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              教师姓名
            </template>
            {{ this.infoform.name }}
            <i class="el-icon-edit editicon" @click="edit"></i>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              性别
            </template>
            {{ this.infoform.gender?"男":"女" }}
            <i class="el-icon-edit editicon" @click="edit"></i>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              年龄
            </template>
            {{ this.infoform.age }}
            <i class="el-icon-edit editicon" @click="edit"></i>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              密码
            </template>
            ******
            <i class="el-icon-edit editicon" @click="edit"></i>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              身份证后六位
            </template>
            ******
            <i class="el-icon-edit editicon" @click="edit"></i>
          </el-descriptions-item>
        </el-descriptions>
        <div style="margin: 20px auto">
          <el-button type="danger" plain @click="logout">退出登录</el-button>
          <!--          <el-button type="danger" plain @click="dayin">打印</el-button>-->
        </div>
      </div>
    </div>
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
          <el-input v-model="infoform.password" placeholder="隐私数据(必须修改)" type="password"></el-input>
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

</template>
<script>
import '@/assets/Home.css'
export default {
  computed: {
    id(){
      return this.$store.getters.getId
    }
  },
  data() {
    return {
      form:{
        tea_id:this.id,
        password:"********"
      },
      infoform:{
      },
      editVisible:false,
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
    }
  },
  methods: {
    cancel() {
      this.form={};
      this.infoform={};
      this.addVisible=false;
      this.editVisible=false;
      this.flush(this.id);
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
    edit(){
      this.editVisible=true;
      this.infoform = JSON.parse(JSON.stringify(this.infoform));
    },
    logout() {
      this.$axios.get("/user/logout")
          .then(res => {
            this.$store.commit("REMOVE");
            this.$router.replace("/login");
          })
          .catch(error => {
            console.log(error);
          })
    },
    flush(e) {
      // console.log("刷新方法执行！！",e);
      this.infoform = {
        tea_id: e,
      }
      this.$axios.get("/admin/selectone",
          {
            params: {
              tea_id: e
            }
          })
          .then((res) => {
            // console.log(res.data);
            this.infoform.isroot = e.isroot === "是" ? 1 : 0;
            if (res.data.code === 1) {
              let data = res.data.data;
              // console.log(data);
              this.infoform = {
                tea_id: e,
                name: data.name,
                gender: data.gender ? 1 : 0,
                age: data.age
              }
            }
            // console.log(this.infoform);
          })
          .catch(function (error) {
            console.log(error);
          });
    }
  },
  mounted ()
  {
    this.$router.afterEach((to, from, next) => {
      window.scrollTo(0, 0)
    })
  },
  watch: {
    '$store.getters.getId': {
      immediate: true,
      handler(newValue) {
        // 发送请求
        this.flush(newValue);
      },
    },
  },
}
</script>
<style>
.setting {
  margin: 50px;
  width: 60%;
}

.editicon {
  font-size: 15px;
  float: right;
  position: relative;
  top: 3px;
  left: -5px;
}

.editicon:hover {
  color: #2d55de;
  cursor: pointer;
}
</style>
