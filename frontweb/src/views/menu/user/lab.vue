
<template>
  <div class="user">
    <div class="title" >
      <p style="position: relative;top: 2%;margin: 15px;font-weight: bold;font-size: 20px">实验管理</p>
      <div class="choose">
        <p>当前学期编号为  <span style="color: #3d7aef" >{{this.sem.sem_id}}</span>  名称为<span style="color: #3d7aef" >{{this.sem.sem_name}}</span></p>
      </div>
      <div class="content" style="display: flex;">
        <div class="classlist" >
          <p style="margin-left: 20px; font-weight: bold">课程列表</p>
          <el-collapse
              :collapse-transition="false"
              style=" margin-left: 10px" accordion element-loading-text="拼命加载中">
            <el-collapse-item v-for="item in teacher"  v-if="currentUserId[0] !== 't' || item.tea_id === currentUserId" :key="item.tea_id" :title="item.tea_id+' '+item.name" :name="item.tea_id" @click.native="handleClick(item.tea_id)">
              <el-row class="vertical-row" >
                <el-col v-if="courses.length === 0" :span="24">
                  <div class="grid-item">暂无数据</div>
                </el-col>
                <el-col  :span="23" v-for="course in courses" :key="course.Course_id">
                  <div class="grid-item" @click="choose(course)">
                    {{course.Course_name}}
                    <i class="el-icon-circle-plus-outline" @click="addrow(course)"></i>
                  </div>
                </el-col>
              </el-row>
            </el-collapse-item>
          </el-collapse>
        </div>
        <el-table
            :data="tableData"
            height="520px"
            stripe
            empty-text="点击课程获取选课记录"
            style="width: 100%;"
        >
          <el-table-column
              prop="id"
              label="序号"
              width="80">
          </el-table-column>
          <el-table-column
              prop="plan_id"
              label="记录编号"
              width="80">
          </el-table-column>
          <el-table-column
              prop="course_id"
              label="课程编号"
              width="150">
          </el-table-column>
          <el-table-column
              prop="lab_id"
              label="实验室编号"
              width="150"
              :formatter="formatlab_id">
          </el-table-column>
          <el-table-column
              prop="last"
              label="持续周数"
              width="150"
              :formatter="formatlast">
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作">
            <template slot-scope="scope">
              <el-button @click="check(scope.row)" type="text" size="small" style="position: relative;left: 10px">查看  </el-button>
              <el-button @click="planclass(scope.row)" type="text" size="small">排课</el-button>
              <el-button @click="deletee(scope.row)" type="text" size="small" style="color: red">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <el-dialog
        title="当前课表信息"
        :visible.sync="tableVisible"
        width="95%"
        style="margin-left: 200px">
      <div style="display: flex">
        <div style="margin: -30px 0"></div>
        <div style="width: 30%;margin-right: 10px">
          <div>
            <h4>当前点击的课程信息</h4>
            <el-descriptions :column="1" border
                             v-if="this.clickcourse.Course_id!==undefined">
              <el-descriptions-item>
                <template slot="label">
                  课程编号
                </template>
                {{ this.clickcourse.Course_id }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  课程名称
                </template>
                {{ this.clickcourse.Course_name }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  授课教师编号
                </template>
                {{ this.clickcourse.tea_id }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  班级
                </template>
                <el-tag size="small">{{ this.clickcourse.Class_ }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item>

                {{ this.clickcourse.Subject }}
              </el-descriptions-item>
            </el-descriptions>
            <div v-else style="display: flex;text-align: center;justify-content: center;margin: 25px 0">
              <el-empty description="当前无信息，点击课程选择信息"></el-empty>
            </div>
            <div v-if="this.clickcourse.Course_id===undefined">
              <h4>插入操作框</h4>
              <p style="color: rgb(138,138,138);text-align: center" >(TIPS:点击表格的"新增"可以动态获取信息哦awa)</p>
              <el-form
                  ref="updateform" v-model="updateform">
                <el-form-item label="当前课程编号" prop="Course_id">
                  <el-input size="small" v-model="updateform.Course_id" disabled></el-input>
                </el-form-item>
                <el-form-item label="当前排课编号" prop="id">
                  <el-input size="small" v-model="updateform.id" :disabled="isDisabled">
                    <template slot="prepend">plan</template>
                    <i slot="suffix" class="el-icon-refresh-left" @click="random"></i>
                  </el-input>
                </el-form-item>
                <el-form-item label="开始日期" prop="nowdate">
                  <el-date-picker size="small" v-model="updateform.nowdate"></el-date-picker>
                </el-form-item>
                <div style="display: flex;margin: -10px 0 10px -10px;">
                  <el-form-item label="开始节数" style="margin: 0 10px"  prop="start">
                    <el-input-number v-model="updateform.start" size="small" :max="updateform.end"></el-input-number>
                  </el-form-item>
                  <el-form-item label="结束节数" style="margin: 0 10px" prop="end">
                    <el-input-number v-model="updateform.end" size="small" :min="updateform.start"></el-input-number>
                  </el-form-item>
                </div>
                <el-form-item label="持续周数" prop="last">
                  <el-input-number v-model="updateform.last" controls-position="right" size="small" style="margin-left: 10px"></el-input-number>
                </el-form-item>
                <div>
                  <el-button type="primary" plain @click="insert" >确定</el-button>
                </div>
              </el-form>
            </div>
            <div v-else-if="this.clickcourse.tea_id===currentTeacherId" style="display: flex;margin: 20px;justify-content: center">
              <el-button @click="deletee" type="danger">是否删除选中信息</el-button>
            </div>
          </div>
        </div>
        <div style="width: 70%;" id="printView">
          <div>
            <h4>操作</h4>
            <el-select v-model="nowweek_value" placeholder="请选择周次">
              <el-option
                  v-for="item in nowweek"
                  :key="item.id"
                  :label="item.week"
                  :value="item.id">
                <span style="float: left">{{ item.week }}</span>
                <span style="float: right; color: #8492a6; font-size: 10px">{{ item.timeRange }}</span>
              </el-option>
            </el-select>
            <el-select v-model="lab_value" placeholder="请选择实验室"  style="margin: 0 10px">
              <el-option
                  v-for="item in labs"
                  :key="item.lab_id"
                  :label="item.lab_name"
                  :value="item.lab_id">
                <span style="float: left">{{ item.lab_name }}</span>
                <span style="float: right; color: #8492a6; font-size: 10px">{{ item.lab_id }}</span>
              </el-option>
            </el-select>
            <el-button type="primary" plain style="margin: 0 10px" @click="dayin">打印</el-button>
          </div>

          <div style="width: 100%">
            <vue-easy-print tableShow ref="printRef">
              <h4>预览当前学期课表</h4>
              <panel
                  :length="this.sem.dayclass"
                  :events="courseData"
                  :key="componentKey"
                  v-on:listenToChildEvent='clickcoursechange'
                  v-on:locinfo="locinfo"
                  :show="show"
              ></panel>
            </vue-easy-print>
          </div>
        </div>
      </div>

    </el-dialog>
    <el-dialog
        title="当前排课信息查询"
        :visible.sync="checkVisible"
        width="70%"
        style="margin-left: 150px">
      <div style="display: flex;">
        <div class="infomation">
          <h3 style="color: #3d7aef;position: relative;left: 40%">课程信息</h3>
          <div style="margin: 20px 80px;position: relative;left: 40px">
            <el-form>
              <el-form-item label="课程编号：">
                {{ this.cou.Course_id }}
              </el-form-item>
              <el-form-item label="课程名称：">
                {{ this.cou.Course_name }}
              </el-form-item>
              <el-form-item label="上课班级：">
                {{ this.cou.Class_ }}(人数{{this.cou.People_num}})
              </el-form-item>
              <el-form-item label="课程性质：">
                {{ this.cou.status===1?"必修":"选修" }}
              </el-form-item>
              <el-form-item label="课程种类：">
                {{ this.cou.Subject}}
              </el-form-item>
            </el-form>
          </div>
        </div>
        <div class="infomation">
          <h3 style="color: #3d7aef;position: relative;left: 40%">当前学期信息</h3>
          <div style="margin: 20px 80px;position: relative;left: 40px">
            <el-form>
              <el-form-item label="学期编号：">
                {{ sem.sem_id }}
              </el-form-item>
              <el-form-item label="学期名称：">
                {{ sem.sem_name }}
              </el-form-item>
              <el-form-item label="学期开始日期：">
                {{sem.begindate}}
              </el-form-item>
              <el-form-item label="学期所含周数：">
                {{sem.week}}
              </el-form-item>
              <el-form-item label="一天所含课程节数：" >
                {{sem.dayclass}}
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
      <div class="moreinfo">
        <h3 style="color: #3d7aef;position: relative;left: 40%;top: 20px;margin-bottom: 20px ">当前实验室信息</h3>
        <div style="margin: 20px 100px;position: relative;top: 40px;left: 100px">
          <el-form v-if="this.lab.lab_id!==undefined">
            <el-form-item label="实验室编号：">
              {{ this.lab.lab_id }}
            </el-form-item>
            <el-form-item label="实验室名称：">
              {{ this.lab.lab_name }}
            </el-form-item>
            <el-form-item label="地点：">
              {{this.lab.location}}
            </el-form-item>
          </el-form>
          <div v-else style="position: relative;left: 8%">当前没有数据</div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import panel from "@/components/panel.vue";
import editor from "@/components/editor.vue";
import moment from 'moment';
import vueEasyPrint from "vue-easy-print";
export default {
  components: {panel,editor,vueEasyPrint},
  data() {
    return {
      currentTeacherId: this.getCurrentUserTeaId(),
      show:true,
      isDisabled:false,
      updateform:{
        start:1,
        end:5,
        last:1,
        id:""
      },
      nowcourse:"",
      lab_value:-1,
      searchText:"",
      tableData:[
        {
          lab_id: ""
        }
      ],
      courses: [],
      labs:[],
      sem:{
      },
      courseData: [
        {
          xq: 4,
          title: '体育',
          class: 'sport',
          content: '1-4节',
          start: 2,
          end: 3
        },
        {
          xq: 5,
          title: '政治课',
          class: '你好',
          content: '1-4节',
          start: 4,
          end: 5
        },
        {
          xq: 4,
          title: '政治课',
          class: '你好',
          content: '1-4节',
          start: 6,
          end: 8
        },
        // 添加更多的课程信息...
      ],
      tableVisible:false,//todo
      nowweek:[],
      week:[],
      sem_name:'',
      teacher:[],
      selectedCourse:null,
      checkVisible:false,
      cou:{},
      lab:{},
      nowweek_value:-1,
      componentKey:0,
      clickcourse:{
      }
    }
  },
  methods:{
    getCurrentUserTeaId() {
      return localStorage.getItem('id') || null;
    },
    //打印信息
    dayin(){
      this.$refs.printRef.print()
    },
    //生成随机数
    random(){
      const randomNumber = Math.floor(Math.random() * 900000) + 100000;
      // 转换为字符串并返回
      this.updateform.id=randomNumber.toString();
    },
    //删除选课记录
    deletee(e) {
      let plan_id = "";
      if(this.clickcourse.plan_id!==undefined) plan_id=this.clickcourse.plan_id;
      else plan_id=e.plan_id;
      const that = this;
      this.$confirm("确定删除选课记录"+plan_id+"吗?")
          .then(_ => {
            this.$axios.get("/plan/delete",{
              params:{
                id:plan_id
              }
            })
                .then((res)=>{
                  this.$alert(res.data.msg,"温馨提示",{
                    confirmButtonText:"确定",
                    callback: () => {
                      that.flushevent(that.nowweek[that.nowweek_value-1]);
                      that.clickcourse= {};
                      that.choose(this.nowcourse);
                    }
                  },);
                  this.dialogVisible=false;
                  that.choose(this.nowcourse);
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
    //增加一行临时记录
    addrow(e){
      console.log(e);
      const that = this;

      this.$axios.get("/plan/insertnull",{
        params:{
          Course_id:e.Course_id
        }
      })
          .then((res)=>{
            that.choose(e);
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
    //获取位置信息
    locinfo(locinfo)
    {
      this.clickcourse={};
      let timerange=this.nowweek[this.nowweek_value-1].timeRange;
      let starttime = timerange.split("-")[0];
      let date = new Date(starttime);
      // console.log(this.courseData[0]);
      console.log("col"+locinfo.col);
      date.setDate(date.getDate()+locinfo.col-1);
      this.updateform.start=locinfo.row;
      this.updateform.xq=locinfo.col;
      this.updateform.nowdate=date;
      console.log(date);
    },
    //获取当前点击的课程信息
    clickcoursechange(cellData){
      console.log(cellData);
      this.$axios.get("/Course/selectone",{
        params:{
          Course_id:cellData.course_id
        }
      })
          .then((res)=>{
            this.clickcourse={
              plan_id:cellData.plan_id,
              Course_id: res.data.data.Course_id,
              Course_name: res.data.data.Course_name,
              Class_: res.data.data.Class_,
              People_num: res.data.data.People_num,
              Status: res.data.data.status === 1?"必修":"任选",
              Belong_num: res.data.data.Belong_num,
              Tool: res.data.data.Tool,
              Subject: res.data.data.Subject,
              tea_id: res.data.data.tea_id
            }
          });
    },
    //按照教师id获取课程列表
    handleClick(e) {
      this.selectedCourse=null;
      console.log(e);
      this.$axios.get("/plan/Coursegroup",{
        params:{
          tea_id:e
        }
      })
          .then((res)=>{
            this.courses=[];
            let idcounter=1;
            for(let i in res.data.data){
              let now={
                id: idcounter++,
                Course_id: res.data.data[i].Course_id,
                Course_name: res.data.data[i].Course_name,
              }
              this.courses.push(now);
            }
          })
    },
    //点击排课按钮
    planclass(e){
      // console.log(e);
      this.tableVisible=true;
      if(e.lab_id===null||e.lab_id===undefined)
      {
        this.updateform.id=e.plan_id.replace(/plan/, "");
        this.isDisabled=true;
      }
      this.updateform.Course_id=e.course_id;
      const startDate = new Date(this.sem.begindate);
      const week = this.sem.week;
      for(let i=1;i<=week;i++)
      {
        const endDate = new Date(startDate.getTime() + 6 * 24 * 60 * 60 * 1000);
        // 计算结束日期（开始日期 + 6天）
        const weekString = `第${i}周`;
        const timeRangeString = `${startDate.getFullYear()}.${startDate.getMonth() + 1}.${startDate.getDate()}-${endDate.getFullYear()}.${endDate.getMonth() + 1}.${endDate.getDate()}`;
        const weekObject = {
          id:i,
          week: weekString,
          timeRange: timeRangeString,
        };
        this.nowweek.push(weekObject);
        startDate.setDate(startDate.getDate() + 7); // 更新开始日期为下一周的起始日期
      }
      this.nowweek_value=1;
      this.lab_value=e.lab_id;
      this.$axios.get("/lab/selectall")
          .then((res)=>{
            this.labs=[];
            let idcounter=1;
            for(let i in res.data.data){
              let now={
                id: idcounter++,
                lab_id:res.data.data[i].lab_id,
                lab_name:res.data.data[i].lab_name
              }
              this.labs.push(now);
            }
            this.lab_value=this.labs[0].lab_id;
            this.choose(this.nowcourse);
          });
    },
    //获取一门课程全部信息
    choose(e){
      this.nowcourse=e;
      this.$axios.get("/plan/course",{
        params:{
          course_id:e.Course_id,
        }
      })
          .then((res)=>{
            this.tableData=[];
            let idcounter=1;
            for(let i in res.data.data)
            {
              let now={
                id: idcounter++,
                plan_id:res.data.data[i].id,
                course_id: res.data.data[i].course_id,
                lab_id: res.data.data[i].lab_id,
                end: res.data.data[i].end,
                last: res.data.data[i].last,
                start: res.data.data[i].start,
                start_time: res.data.data[i].start_time,
                xq: res.data.data[i].xq,
              }
              console.log(now);
              this.tableData.push(now);

            }
          })
    },
//初始化暂无数据
    formatlab_id(row){
      if (row.lab_id) {
        return row.lab_id; // 如果有值，则返回原始值
      } else {
        return '暂无数据'; // 如果为空，则返回默认值
      }
    },
    formatlast(row){
      if (row.xq !== 1) {
        return row.last+ 1
      }
      else if (row.last) {
        return row.last; // 如果有值，则返回原始值
      } else {
        return '暂无数据'; // 如果为空，则返回默认值
      }
    },
    //查看详细信息
    check(e){
      this.checkVisible=!this.checkVisible;
      this.$axios.get("/Course/selectone",{
        params:{
          Course_id:e.course_id
        }
      })
          .then((res)=>{
            this.cou={
              Course_id: res.data.data.Course_id,
              Course_name: res.data.data.Course_name,
              Class_: res.data.data.Class_,
              People_num: res.data.data.People_num,
              Status: res.data.data.status === 1?"必修":"任选",
              Belong_num: res.data.data.Belong_num,
              Tool: res.data.data.Tool,
              Subject: res.data.data.Subject,
              tea_id: res.data.data.tea_id
            }
          });
      if(e.lab_id===null)
      {
        this.lab={};
      }
      else
      {
        this.$axios.get("/lab/selectone",{
          params:{
            lab_id:e.lab_id
          }
        })
            .then((res)=>{
              this.lab={
                lab_id:res.data.data.lab_id,
                lab_name:res.data.data.lab_name,
                location:res.data.data.location,
              }
            });
      }
    },
    //处理lab/week信息变化
    handleDataChange(labValue, weekValue){
      // console.log(labValue);
      this.flushevent(this.nowweek[weekValue-1]);
    },
    //刷新课表
    flushevent(e){
      const that = this;
      // console.log(e);
      this.$axios.get("/plan/table",{
        params:{
          timerange:e.timeRange,
          lab_id:this.lab_value
        }
      })
          .then((res)=>{
            console.log(res.data);
            if (res.data.code === 1) {
              that.courseData = [];
              that.componentKey++; // 改变 componentKey 的值来强制刷新子组件
              let plan =res.data.data.plan;
              let Course =res.data.data.Course;
              for (let i=0;i<plan.length;i++) {
                console.log(i);
                let now = {
                  plan_id: plan[i].id,
                  xq: plan[i].xq,
                  title: Course[i].Course_name,
                  course_id: Course[i].Course_id,
                  Class_: Course[i].Class_,
                  content: Course[i].Subject,
                  start: plan[i].start,
                  end: plan[i].end
                };
                that.courseData.push(now);
              }
              console.log(that.courseData);
            }
          });
    },
    //增加选课信息
    insert(){
      let that = this;
      console.log(this.updateform);
      let update={
        plan:{
          lab_id:this.lab_value,
          course_id:this.updateform.Course_id,
          id:this.updateform.id,
          start_time:moment(this.updateform.nowdate).format('YYYY-MM-DD HH:mm'),
          start:this.updateform.start,
          end:this.updateform.end,
          last:this.updateform.last,
          xq:this.updateform.xq
        },

        timerange:this.nowweek[this.nowweek_value-1].timeRange
      };
      if (update.plan.xq !== 1) {
        update.plan.last = update.plan.last - 1;
      }
      console.log(update);
      this.$axios.post("/plan/insert",update)
          .then((res)=>{
            that.flushevent(this.nowweek[this.nowweek_value-1]);
            if(res.data.code===0)
            {
              that.$alert(res.data.msg,"温馨提示",{
                confirmButtonText:"确定"
              },);
            }

          })
          .catch(function (error) {
            that.cancel();
            console.log(error);
            that.$message({
              message: '网络连接错误',
              type: 'error'
            });
          });
    }
  },
  mounted() {
    this.$router.afterEach((to, from, next) => {
      window.scrollTo(0, 0)
    })
    this.tableData=[];
    this.$axios.get("/plan/info")
        .then((res)=>{
          // this.teacher=[];
          let idcounter=1;
          for(let i in res.data.data){
            let now={
              id: idcounter++,
              tea_id: res.data.data[i].tea_id,
              age:res.data.data[i].age,
              name:res.data.data[i].name,
              gender:res.data.data[i].gender,
            }
            this.teacher.push(now);
          }
        });
    this.$axios.get("/plan/sem")
        .then((res)=>{
          this.sem={
            sem_id:res.data.data.sem_id,
            sem_name:res.data.data.sem_name,
            begindate:res.data.data.begindate,
            dayclass:res.data.data.dayclass,
            week:res.data.data.week
          }
        });
  },
  computed: {
    currentUserId() {
      return this.getCurrentUserTeaId();
    }
  },
  watch:{
    nowweek_value(newValue) {
      this.handleDataChange(this.lab_value, newValue);
      // this.flushevent(this.nowweek[newValue-1]); // 调用后端数据请求函数，并传递newValue作为参数
    },
    lab_value(newValue) {
      this.handleDataChange(newValue, this.nowweek_value);
    },
    getStart: function(newStart) {
      if (this.updateform.end < newStart) {
        this.updateform.end = newStart; // Set end to be at least equal to start
      }
    },
  }
}
</script>
<style>
@import '../../../assets/table.css';
.el-icon-refresh-left{
  font-size: 15px;
  margin: 10px;
}
.el-icon-refresh-left:hover{
  color: #3d7aef;
}

.infomation{
  margin:0 auto;
  box-shadow: 0 12px 48px 0 rgba(0, 0, 0, 0.04);
  width: 45%;
  height: 100%;
  border-radius: 10px;
  display: block;
  position: relative;
  cursor: pointer;
}

.moreinfo{
  margin:0 auto;
  box-shadow: 0 12px 48px 0 rgba(0, 0, 0, 0.04);
  width: 60%;
  height: 300px;
  border-radius: 10px;
  display: block;
  position: relative;
  cursor: pointer;
}

.classlist{
  width: 30%;
  height: 500px;
  overflow: auto;
}

.choose{
  display: flex;
  margin: 15px;
  color: #757575;
}
.vertical-row {
  flex-direction: column;
}

.grid-item {
  margin: 0;
  height: 30px;
  display: flex;
  align-items: center;
  padding: 10px;
  color: #5d5d5d;
}
.grid-item:hover{
  background-color: #f0f0f0;
  cursor: pointer;
}
.el-icon-circle-plus-outline{
  position: relative;
  left: 5px;
  font-size: 17px;
  font-weight: 500;
}
.el-icon-circle-plus-outline:hover{
  color: #3d7aef;
}
</style>
