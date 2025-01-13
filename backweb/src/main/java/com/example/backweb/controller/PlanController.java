package com.example.backweb.controller;

import com.example.backweb.common.lang.Result;
import com.example.backweb.pojo.Course;
import com.example.backweb.pojo.plan;
import com.example.backweb.pojo.plantime;
import com.example.backweb.service.impl.CourseService;
import com.example.backweb.service.impl.PlanServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Api(tags = "实验管理")
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    PlanServiceImpl planService;
    @Autowired
    CourseService courseService;
    @GetMapping("/info")
    @ApiOperation("返回当前教师信息")
    public Result getsem_id(){
        return new Result().setCode(1).setMsg("ok").setData(planService.selectall());
    }

    @GetMapping("/Coursegroup")
    @ApiOperation("按照老师id返回课程信息")
    public Result plannow(String tea_id){
        return new Result().setCode(1).setMsg("ok").setData(planService.selectcoursebytea(tea_id));
    }

    @GetMapping("/sem")
    @ApiOperation("返回当前学期信息")
    public Result nowsem(){
        return new Result().setCode(1).setMsg("ok").setData(planService.nowsem());
    }

    @GetMapping("/course")
    @ApiOperation("返回当前课程记录的排课信息")
    public Result nowcourse(String course_id){
        return new Result().setCode(1).setMsg("ok").setData(planService.selectallplan(course_id));
    }
    @GetMapping("/table")
    @ApiOperation("按周数返回课表信息")
    public Result backplan(String timerange,String lab_id) throws ParseException {
        if(timerange==null) return new Result().setCode(400).setMsg("error");
        else
        {
            Map<Object,Object> data= new HashMap<>();
            List<plan> selectbyweek = planService.selectbyweek(timerange, lab_id);
            List<Course> courses = new ArrayList<>();
            for(plan item:selectbyweek)
            {
                Course selectone = courseService.selectone(item.getCourse_id());
                courses.add(selectone);
            }
            data.put("plan",selectbyweek);
            data.put("Course",courses);
            return new Result().setCode(1).setMsg("ok").setData(data);
        }
    }
    @PostMapping("/insert")
    @ApiOperation("插入课表信息")
    public Result inserplan(@RequestBody plantime plantime) throws ParseException {
        plan plan =plantime.getPlan();
        String timerange = plantime.getTimerange();
        plan.setId("plan"+plan.getId());
        plan isexited = planService.isexited(plan.getId());//是否该课程存在
        String[] dateParts = timerange.split("-");
        String startDateString = dateParts[0];
        String endDateString = dateParts[1];
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date startDate = format.parse(startDateString);
        Date endDate = format.parse(endDateString);
        if(isexited==null)//id不存在
        {
            List<plan> planerror = planService.planerror(plan, startDate, endDate);
            System.out.println("planerror:"+planerror);//判断插入的课程是否有问题
            if(planerror.isEmpty())
            {
                planService.insertplan(plan);
                return new Result().setCode(1).setMsg("插入成功");
            }
            else
            {
                return new Result().setCode(0).setMsg("当前时间段已存在其他课程");
            }
        }
        else if(isexited.getLab_id()==null)
        {
            if(planService.update(plan)) return new Result().setCode(1).setMsg("更新成功");
        }
        return new Result().setCode(0).setMsg("当前选课id已存在");
    }
    @GetMapping("/insertnull")
    @ApiOperation("插入未选课课表信息")
    public Result insernull(String Course_id)
    {
        if(planService.insertnull(Course_id)) return new Result().setCode(1).setMsg("添加临时记录成功");
        else return new Result().setCode(0).setMsg("添加临时记录失败");
    }
    @GetMapping("/delete")
    @ApiOperation("删除选课信息")
    public Result delete(String id)
    {
        if(planService.delete(id)) return new Result().setCode(1).setMsg("选课信息删除成功");
        else return new Result().setCode(0).setMsg("选课信息删除失败");
    }

}
