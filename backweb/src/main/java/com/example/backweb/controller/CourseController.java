package com.example.backweb.controller;

import com.example.backweb.common.lang.Result;
import com.example.backweb.pojo.Course;
import com.example.backweb.service.impl.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@Api(tags = "课程管理")
@RequestMapping("/Course")
public class CourseController {
    @Autowired
    CourseService courseService;
    //增加
    @PostMapping("/add")
    @ApiOperation("增加新课程")
    public Result add(@RequestBody Course Course) {
        System.out.println(Course);
        if(courseService.selectone(Course.getCourse_id())!=null) return Result.fail("课程编号已存在");
        else
        {
            Course.setCourse_id("cou"+Course.getCourse_id());
            courseService.insertCourse(Course);
            return Result.success("课程添加成功");
        }
    }
    //删除
    @PostMapping("/delete")
    @ApiOperation("删除课程")
    public Result delete(@RequestBody Course Course) {
        if(courseService.delete(Course)) return Result.success("课程删除成功");
        else return Result.fail("课程删除失败");
    }
    //更新
    @PostMapping("/update")
    @ApiOperation("更新课程信息")
    public Result update(@RequestBody Course Course) {
        //更新操作
        if (courseService.update(Course)) return Result.success("课程更新成功");
        else return Result.fail("课程更新失败");
    }
    //返回
    @GetMapping("/selectall")
    @ApiOperation("返回所有课程信息")
    public Result selectall() {
        List<Course> Courses=courseService.selectall();
        if(Courses==null) return Result.fail("暂无数据");
        else return Result.success("ok",Courses);
    }
    //回显
    @GetMapping("/selectone")
    @ApiOperation("课程信息回显")
    public Result selectone(String Course_id) {
        if(courseService.selectone(Course_id)!=null) return Result.success("信息返回成功",courseService.selectone(Course_id));
        return Result.fail("信息返回失败");
    }
    @GetMapping("/search")
    @ApiOperation("搜索课程")
    public Result search(String id){
        if(courseService.selectCourse(id)!=null) return Result.success("ok",courseService.selectCourse(id));
        else return Result.fail("暂无数据");
    }
}
