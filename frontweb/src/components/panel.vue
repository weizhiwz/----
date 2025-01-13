<template>
  <div>
    <div class="panel">
      <el-table :data="timetable" :span-method="objectSpanMethod" border
                :header-cell-style="{background:'#ffffff', color:'#464545', fontWeight: 1000}"
                :cell-style="tableCellStyle"
                :row-style="{ height: '80px' }"
                :row-key="getRowKey"
                :cell-class-name="tableCellClassName" @cell-click="cellClick">
        <el-table-column prop="jc" label="节次" width="80" align="center">
        </el-table-column>
        <el-table-column prop="mon" label="星期一" align="center">
          <template slot-scope="scope">
            <div v-if="!scope.row.mon.title" class="addbutton"> <!-- 如果没有标题 -->
              <i class="el-icon-plus" v-if="show"></i>
            </div>
            <div v-else> <!-- 如果有标题 -->
              <div class="cell-button" @click="Click(scope.row.mon)">
                <h4>{{ scope.row.mon.title }}</h4>
                <p style="margin: 0;font-size: 11px" >{{ scope.row.mon.Class_ }}</p>
                <p v-html="scope.row.mon.content" style="margin: 5px"></p>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="tue" label="星期二" align="center">
          <template slot-scope="scope">
            <div v-if="!scope.row.tue.title" class="addbutton"> <!-- 如果没有标题 -->
              <i class="el-icon-plus" v-if="show"></i>
            </div>
            <div v-else> <!-- 如果有标题 -->
              <div class="cell-button" @click="Click(scope.row.tue)">
                <h4>{{ scope.row.tue.title }}</h4>
                <p style="margin: 0;font-size: 11px" >{{ scope.row.tue.Class_ }}</p>
                <p v-html="scope.row.tue.content" style="margin: 5px"></p>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="wed" label="星期三" align="center">
          <template slot-scope="scope">
            <div v-if="!scope.row.wed.title" class="addbutton"> <!-- 如果没有标题 -->
              <i class="el-icon-plus" v-if="show"></i>
            </div>
            <div v-else> <!-- 如果有标题 -->
              <div class="cell-button" @click="Click(scope.row.wed)">
                <h4>{{ scope.row.wed.title }}</h4>
                <p style="margin: 0;font-size: 11px" >{{ scope.row.wed.Class_ }}</p>
                <p v-html="scope.row.wed.content" style="margin: 5px"></p>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="thu" label="星期四" align="center">
          <template slot-scope="scope">
            <div v-if="!scope.row.thu.title" class="addbutton"> <!-- 如果没有标题 -->
              <i class="el-icon-plus" v-if="show"></i>
            </div>
            <div v-else> <!-- 如果有标题 -->
              <div class="cell-button" @click="Click(scope.row.thu)">
                <h4>{{ scope.row.thu.title }}</h4>
                <p style="margin: 0;font-size: 11px" >{{ scope.row.thu.Class_ }}</p>
                <p v-html="scope.row.thu.content" style="margin: 5px"></p>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="fri" label="星期五" align="center">
          <template slot-scope="scope">
            <div v-if="!scope.row.fri.title" class="addbutton"> <!-- 如果没有标题 -->
              <i class="el-icon-plus" v-if="show"></i>
            </div>
            <div v-else> <!-- 如果有标题 -->
              <div class="cell-button" @click="Click(scope.row.fri)">
                <h4>{{ scope.row.fri.title }}</h4>
                <p style="margin: 0;font-size: 11px" >{{ scope.row.fri.Class_ }}</p>
                <p v-html="scope.row.fri.content" style="margin: 5px"></p>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="sat" label="星期六" align="center">
          <template slot-scope="scope">
            <div v-if="!scope.row.sat.title" class="addbutton"> <!-- 如果没有标题 -->
              <i class="el-icon-plus" v-if="show"></i>
            </div>
            <div v-else> <!-- 如果有标题 -->
              <div class="cell-button" @click="Click(scope.row.sat)">
                <h4>{{ scope.row.sat.title }}</h4>
                <p style="margin: 0;font-size: 11px" >{{ scope.row.sat.Class_ }}</p>
                <p v-html="scope.row.sat.content" style="margin: 5px"></p>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="sun" label="星期日" align="center">
          <template slot-scope="scope">
            <div v-if="!scope.row.sun.title" class="addbutton"> <!-- 如果没有标题 -->
              <i class="el-icon-plus" v-if="show"></i>
            </div>
            <div v-else> <!-- 如果有标题 -->
              <div class="cell-button" @click="Click(scope.row.sun)">
                <h4>{{ scope.row.sun.title }}</h4>
                <p style="margin: 0;font-size: 11px" >{{ scope.row.sun.Class_ }}</p>
                <p v-html="scope.row.sun.content" style="margin: 5px"></p>
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>

    </div>
  </div>
</template>
<script>
export default {
  name:"panel",
  props: {
    //展示加号
    show: {
      type: Boolean,
      default: true
    },
    // 总节次
    length: {
      type: [String, Number],
      default: 5
    },
    // 课表数据
    events: {
      type: Array,
      default: null
    }
  },
  data () {
    return {
      // 课程表数据
      timetable: [],
      hoverOrderArr: [],
      weeks: ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun'],
      selectedCells:[],
      clickcourse:'',
      testdata:'123213',
      num:[],
    }
  },
  mounted () {
    this.mergeData()
  },
  watch: {
    events: {
      handler (newVal, oldVal) {
        this.mergeData()
      },
      deep: true
    }
  },
  created () {
    this.makeTimetable()
  },
  methods: {
    // 单元格添加背景色
    getRowKey(row, index){return index;},
    tableCellStyle (row) {
      console.log(row);
      if (row.row[row.column.property].title !== undefined) {
        let color='#b49edb;'
        if(row.row[row.column.property].content === "软件类") color='#66ccff;'
        if(row.row[row.column.property].content === "硬件类") color='#ee0000;'
        return 'background-color: ' + color + '\n' +
            '  color: #ffffff;\n' +
            '  border-radius:10px;\n' +
            '  padding:6px;\n';
    }},
    // 构造课程表完整数据
    makeTimetable () {
      this.timetable = []
      for (let i = 0; i < this.length; i++) {
        let one = {
          jc: (i+1),
          mon: {},
          tue: {},
          wed: {},
          thu: {},
          fri: {},
          sat: {},
          sun: {}
        }
        this.timetable.push(one)
      }
    },
    mergeData () {
      // 合并数据
      if (this.events.length > 0) {
        for (let i = 0; i < this.events.length; i++) {
          // 获取星期几
          let week = this.weeks[this.events[i].xq - 1];
          let start = this.events[i].start;
          let end = this.events[i].end;
          this.timetable[start - 1][week] = {
            ...this.events[i],
            start: start,
            end: end
          };
        }
      }
    },
    objectSpanMethod ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 1 ||columnIndex === 2 || columnIndex === 3 || columnIndex === 4 || columnIndex === 5 || columnIndex === 6 || columnIndex === 7 ) {
        const day = this.weeks[columnIndex - 1]; // 获取对应的星期几
        if (row[day].title !== undefined) { // 如果该单元格有课程信息
          return {
            rowspan: row[day].end - row[day].start + 1, // 计算 rowspan
            colspan: 1 // 每个单元格默认 colspan 为 1
          };
        } else {
          return {
            rowspan: 1,
            colspan: 1
          };
        }
      }
    },
    Click(cellData){
      this.$emit('listenToChildEvent',cellData);
    },
    tableCellClassName({row, column, rowIndex, columnIndex}){//注意这里是解构
      //利用单元格的 className 的回调方法，给行列索引赋值
      row.index=rowIndex;
      column.index=columnIndex;
    },
    cellClick(row, column, cell, event){
      let locinfo={
        row:row.index+1,
        col:column.index
      }
      console.log(locinfo);
      this.$emit('locinfo',locinfo);
    }
  },
}
</script>
<style>s,
.panel{
  background-color: rgb(129, 203, 196);
  color: #ffffff;
  border-radius:10px;
  padding:6px;
}
.cell-button{
  cursor: pointer;
  width: 100%;
  height: 100%;
  padding:10px 5px;
}
.addbutton{
  color: #cecece;
}
.addbutton:hover{
  cursor: pointer;
  color: #575757;
}
</style>
