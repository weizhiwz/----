
<template>
  <div class="Home">
    <div class="classtable">
      <div style="margin: 20px">
        <h4>当前学期课表</h4>
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
        <el-button type="info" plain style="margin: 0 10px" @click="dayin">打印</el-button>

          <div style="margin-top: 10px;width: 70%" >
            <vue-easy-print tableShow ref="printRef">
            <panel
                :length="this.sem.dayclass"
                :events="courseData"
                :key="componentKey"
                :show="showadd"
            ></panel>
            </vue-easy-print>
          </div>

      </div>

    </div>
    <div class="classtable">
      <div style="margin: 20px">
        <div style="display: flex">
          <h4>实验室信息</h4>
          <el-select v-model="lab_value" placeholder="请选择实验室"  style="margin:10px 20px ">
            <el-option
                v-for="item in labs"
                :key="item.lab_id"
                :label="item.lab_name"
                :value="item.lab_id">
              <span style="float: left">{{ item.lab_name }}</span>
              <span style="float: right; color: #8492a6; font-size: 10px">{{ item.lab_id }}</span>
            </el-option>
          </el-select>
        </div>
        <div style="width: 30%">
          <el-descriptions :column="1" border>
            <el-descriptions-item>
              <template slot="label">
                实验室编号
              </template>
              {{ this.nowlab.lab_id }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                实验室名称
              </template>
              {{ this.nowlab.lab_name }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                位置
              </template>
              <el-tag size="small">{{ this.nowlab.location }}</el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </div>

      </div>

    </div>
    <div class="classtable">
      <div style="margin: 20px">
        <h4>实验室详情展示区</h4>
        <quill-editor v-model="this.nowlab.lab_info" @focus="focus($event)" />
      </div>

    </div>
  </div>

</template>
<script>
import '@/assets/Home.css'
import panel from "@/components/panel.vue";
import vueEasyPrint from "vue-easy-print";
import editor from "@/components/editor.vue";
export default {
  computed: {
  },
  components: {panel,editor,vueEasyPrint},
  data() {
    return {
      nowweek_value:0,
      nowweek:[],
      lab_value:0,
      labs:[],
      componentKey:0,
      courseData:[],
      showadd:false,
      nowlab:{}
    }
  },
  methods:{
    //获取焦点事件
    focus(event){
      event.enable(false);  //设置富文本编辑器不可编辑
    },
    dayin(){
      this.$refs.printRef.print()
    },
    calculateWeeks() {
      const startDate = new Date(this.sem.begindate);
      const week = this.sem.week;
      for (let i = 1; i <= week; i++) {
        const endDate = new Date(startDate.getTime() + 6 * 24 * 60 * 60 * 1000); // 计算结束日期（开始日期 + 6天）
        const weekString = `第${i}周`;
        const timeRangeString = `${startDate.getFullYear()}.${startDate.getMonth() + 1}.${startDate.getDate()}-${endDate.getFullYear()}.${endDate.getMonth() + 1}.${endDate.getDate()}`;
        const weekObject = {
          id: i,
          week: weekString,
          timeRange: timeRangeString,
        };
        this.nowweek.push(weekObject);
        this.nowweek_value=1;
        startDate.setDate(startDate.getDate() + 7); // 更新开始日期为下一周的起始日期
      }
    },
    flushevent(e){
      var that=this;
      this.$axios.get("/plan/table",{
        params:{
          timerange:this.nowweek[this.nowweek_value-1].timeRange,
          lab_id:this.lab_value
        }
      })
        .then((res)=>{
          if (res.data.code === 1) {
            that.courseData = [];
            that.componentKey++; // 改变 componentKey 的值来强制刷新子组件
            let plan =res.data.data.plan;
            let Course =res.data.data.Course;
            for (let i=0;i<plan.length;i++) {
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
          }
          });
    },
    handleDataChange(labValue, weekValue){
      this.flushevent(this.nowweek[weekValue-1]);
    },
    getnowlab(){
      this.$axios.get("/lab/selectone",
          {
            params: {
              lab_id:this.lab_value
            }
          })
          .then((res)=>{
            // console.log(res.data);
            if(res.data.code===1)
            {
              let data=res.data.data;
              this.nowlab={
                lab_id:this.lab_value,
                lab_name:data.lab_name,
                location:data.location,
                lab_info:data.lab_info
              };
            }
          })
          .catch(function (error) {
            console.log(error);
          });
    }
  },
  created() {
    this.$axios
        .get("/plan/sem")
        .then((res) => {
          this.sem = res.data.data;
        })
        .then(() => {
          this.calculateWeeks();
        });
    this.$axios
        .get("/lab/selectall")
        .then((res) => {
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
        })
        .then(() => {
          this.getnowlab();
        });
  },
  mounted() {
    if (this.sem) {
      this.calculateWeeks();
    }
    if(this.lab_value) {
      this.getnowlab();
    }
  },
  watch:{
    nowweek_value(newValue) {
      console.log(this.nowweek);
      this.handleDataChange(this.lab_value, newValue);
    },
    lab_value(newValue) {
      this.handleDataChange(newValue, this.nowweek_value);
      this.getnowlab();
    }
  }
}
</script>
<style scoped>


</style>
