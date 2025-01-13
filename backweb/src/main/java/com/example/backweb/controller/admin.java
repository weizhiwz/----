package com.example.backweb.controller;

import com.example.backweb.common.lang.Result;
import com.example.backweb.pojo.Teacher;
import com.example.backweb.pojo.tti;
import com.example.backweb.service.impl.AdminServiceImpl;
import com.example.backweb.service.impl.TeacherServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController

@Api(tags = "管理员人员控制")
@RequestMapping("/admin")
public class admin {
    @Autowired
    AdminServiceImpl AdminService;
    @Autowired
    TeacherServiceImpl teacherService;
    //增加老师
    @PostMapping("/add")
    @ApiOperation("增加用户")
    @RequiresRoles("admin")
    public Result add(@RequestBody Teacher teacher) {
        //判断用户是否已经存在,若存在则返回error,不存在就进行插入操作
        if(teacherService.isexists(teacher.getTea_id())) return Result.fail("教师工号已存在");
        else
        {
            String pwd=teacher.getPassword();
            String ls=teacher.getLast_idcard();
            if(pwd==null) pwd=ls;
            String lockpwd= new Md5Hash(pwd,"",3).toString();
            String idcard= new Md5Hash(ls,"",3).toString();
            teacher.setPassword(lockpwd);
            teacher.setLast_idcard(idcard);
            teacher.setTea_id("tea"+ teacher.getTea_id());
            if(teacherService.enroll(teacher)) return Result.success("教师信息添加成功");
            else return Result.fail("未知错误");
        }
    }
    //删除老师
    @PostMapping("/delete")
    @ApiOperation("删除用户")
    @RequiresRoles("admin")
    public Result delete(@RequestBody Teacher teacher) {
        if(AdminService.delete(teacher)) return Result.success("教师删除成功");
        else return Result.fail("教师删除失败");
    }
    //更新老师
    @PostMapping("/update")
    @ApiOperation("更新用户")
    public Result update(@RequestBody tti t) {
        //更新操作
        System.out.println(t.toString());
        if(AdminService.update(t)) return Result.success("更新成功");
        else return Result.fail("更新失败");
    }
    //返回所有
    @GetMapping("/selectall")
    @ApiOperation("返回全部用户")
    public Result selectall() {
        List<Teacher> teachers=AdminService.selectall();
        if(teachers==null) return Result.fail("暂无数据");
        else return Result.success("ok",teachers);
    }
    //回显信息
    @GetMapping("/selectone")
    @ApiOperation("单个用户信息回显")
    public Result selectone(String tea_id) {
        if(AdminService.selectone(tea_id)!=null) return Result.success("信息返回成功",AdminService.selectone(tea_id));
        return Result.fail("信息返回失败");
    }
    @GetMapping("/search")
    @ApiOperation("搜索")
    @RequiresRoles("admin")
    public Result search(String id){
        if(AdminService.selectteacher(id)!=null) return Result.success("ok",AdminService.selectteacher(id));
        else return Result.fail("暂无数据");
    }
}
