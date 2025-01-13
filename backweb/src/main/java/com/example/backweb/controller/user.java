package com.example.backweb.controller;

import com.example.backweb.common.lang.Result;
import com.example.backweb.common.util.JwtUtil;
import com.example.backweb.pojo.Param;
import com.example.backweb.pojo.Teacher;
import com.example.backweb.service.impl.TeacherServiceImpl;
import com.example.backweb.token.JwtToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "用户操作")
public class user {
    @Autowired
    TeacherServiceImpl teacherService;

    //登录
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result test(@RequestBody Param param) {
        //获取参数
        Teacher teacher=param.getTeacher();
        Boolean rememberMe=param.getRememberMe();
        //获取传来的账户密码权限
        String name=teacher.getTea_id();
        String pwd=teacher.getPassword();

        Subject subject = SecurityUtils.getSubject();
        //创建jwt
        String jwt= JwtUtil.createJWT(name,"back","user",1000*60*60);
        JwtToken token = new JwtToken(jwt,pwd);
        try {
            subject.login(token);
            Map<String,String> data = new HashMap();
            data.put("token",token.getToken());
            data.put("tea_id",name);
            return Result.success("登录成功",data);
        }
        catch (IncorrectCredentialsException e) {
            // 处理密码错误的逻辑
            e.printStackTrace();
            return Result.fail("密码错误");
        } catch (UnknownAccountException e) {
            // 处理用户名错误的逻辑
            e.printStackTrace();
            return Result.fail("用户名错误");
        } catch (AuthenticationException e) {
            // 处理其他身份验证异常
            e.printStackTrace();
            return Result.fail("用户名或密码失败");
        }
    }
    //注册
    @PostMapping("/enroll")
    @ApiOperation("注册")
    public Result enroll(@RequestBody Teacher teacher) {
        String id=teacher.getTea_id();
        if(teacherService.isexists(id)) return Result.fail("用户已存在");
        String pwd=teacher.getPassword();
        String ls=teacher.getLast_idcard();
        if(pwd==null) pwd=ls;
        String lockpwd= new Md5Hash(pwd,"",3).toString();
        String idcard= new Md5Hash(ls,"",3).toString();
        teacher.setPassword(lockpwd);
        teacher.setLast_idcard(idcard);
        if(teacherService.enroll(teacher)) return Result.success("注册成功");
        else return Result.fail("未知错误");
    }
    //忘记密码
    @PostMapping("/forget")
    @ApiOperation("忘记密码")
    public Result forget(@RequestBody Teacher teacher) {
        String pwd=teacher.getPassword();
        String ls=teacher.getLast_idcard();
        String lockpwd= new Md5Hash(pwd,"",3).toString();
        String idcard= new Md5Hash(ls,"",3).toString();
        teacher.setPassword(lockpwd);
        teacher.setLast_idcard(idcard);
        if(teacherService.forget(teacher)) return Result.success("修改密码成功");
        else return Result.fail("修改密码失败");
    }
    @PostMapping("/menu")
    @ApiOperation("判断角色身份")
    public Result checkrole(@RequestBody String tea_id){
        String str = tea_id.substring(0,tea_id.length()-1);
        Teacher teacher = teacherService.login(str);
        return new Result().setCode(200).setMsg("ok").setData(teacher.getIsroot());
    }
    @GetMapping("/logout")
    @ApiOperation("登出")
    public Result logout(){
        Subject lvSubject=SecurityUtils.getSubject();
        lvSubject.logout();
        return new Result().setCode(200);
    }
}
