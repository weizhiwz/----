package com.example.backweb.controller;


import com.example.backweb.common.lang.Result;
import com.example.backweb.pojo.Semester;
import com.example.backweb.service.impl.SemesterServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RestController
@Api(tags = "学期管理")
@RequestMapping("/semester")
public class SemesterController {
    @Autowired
    SemesterServiceImpl semesterService;
    //增加老师
    @PostMapping("/add")
    @ApiOperation("增加学期")
    public Result add(@RequestBody Semester sem) {
        LocalDate nowdate = sem.getBegindate().plusDays(1);
        if (nowdate.getDayOfWeek() != DayOfWeek.MONDAY) {
            LocalDate monday = nowdate.with(DayOfWeek.MONDAY);
            sem.setBegindate(monday);
            System.out.println(nowdate.getDayOfWeek());
        }
        else {
            sem.setBegindate(nowdate);
        }
       //LocalDate monday = nowdate.with(DayOfWeek.MONDAY);
       //sem.setBegindate(monday);
        if(semesterService.selectone(sem.getSem_id())!=null) return Result.fail("学期信息编号已存在");
        else
        {
            sem.setSem_id("sem"+sem.getSem_id());
            semesterService.insertlab(sem);
            return Result.success("学期信息添加成功");
        }
    }
    //删除老师
    @PostMapping("/delete")
    @ApiOperation("删除学期")
    public Result delete(@RequestBody Semester sem) {
        if(semesterService.delete(sem)) return Result.success("学期信息删除成功");
        else return Result.fail("学期信息删除失败");
    }
    //更新老师
    @PostMapping("/update")
    @ApiOperation("更新学期")
    public Result update(@RequestBody Semester sem) {
        //更新操作
        if(semesterService.selectone(sem.getSem_id()).getIsnow()&&!sem.getIsnow())
        {
            return Result.fail("至少有一个当前学期");
        }
        if(sem.getBegindate()!=null)
        {
            LocalDate nowdate = sem.getBegindate().plusDays(1);
            if (nowdate.getDayOfWeek() != DayOfWeek.MONDAY) {
                LocalDate monday = nowdate.with(DayOfWeek.MONDAY);
                sem.setBegindate(monday);
            }
            else {
                sem.setBegindate(nowdate);
            }
//            LocalDate monday = nowdate.with(DayOfWeek.MONDAY);
//            sem.setBegindate(monday);
        }
        if(semesterService.update(sem)) return Result.success("学期信息更新成功");
        else return Result.fail("学期信息更新失败");
    }
    //返回所有
    @GetMapping("/selectall")
    @ApiOperation("返回所有学期信息")
    public Result selectall() {
        List<Semester> sems=semesterService.selectall();
        if(sems==null) return Result.fail("暂无数据");
        else return Result.success("ok",sems);
    }
    //回显信息
    @GetMapping("/selectone")
    @ApiOperation("学期信息回显")
    public Result selectone(String sem_id) {
        if(semesterService.selectone(sem_id)!=null) return Result.success("信息返回成功",semesterService.selectone(sem_id));
        return Result.fail("信息返回失败");
    }
    @GetMapping("/search")
    @ApiOperation("按照编号搜索学期")
    public Result search(String id){
        if(semesterService.selectlab(id)!=null) return Result.success("ok",semesterService.selectlab(id));
        else return Result.fail("暂无数据");
    }
}
