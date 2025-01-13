package com.example.backweb.service.impl;

import com.example.backweb.mapper.AdminMapper;
import com.example.backweb.pojo.Teacher;
import com.example.backweb.pojo.teacherinfo;
import com.example.backweb.pojo.tti;
import com.example.backweb.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Teacher> implements IAdminService {

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    TeacherServiceImpl teacherService;
    @Autowired
    public boolean delete(Teacher teacher) {
        if("admin".equals(teacher.getTea_id())) return false;
        return adminMapper.deleteUser(teacher.getTea_id())&& adminMapper.deleteUserinfo(teacher.getTea_id());
    }
//如果id等于admin不允许删除
    @Override
    public List<Teacher> selectall() {
        return adminMapper.selectall();
    }

    @Override
    public teacherinfo selectone(String tea_id) {
        return adminMapper.selectone(tea_id);
    }

    @Override
    public List<Teacher> selectteacher(String id) {
        return adminMapper.selectteacher(id);
    }

    @Override
    public boolean update(tti t) {
        Teacher teacher=t.getTeacher();
        if(teacher.getTea_id()==null&&teacher.getPassword()==null&&teacher.getLast_idcard()==null) {
            return adminMapper.updateuserinfo(t.getInfo());//如果教师的 tea_id、password 和 Last_idcard 都为空，就直接调用 adminMapper 的 updateuserinfo 方法
        }
        else if(teacher.getTea_id()==null)
        {
            teacher.setTea_id(t.getInfo().getTea_id());//如果id为空就将详细信息里面的id补充到教师信息里面
        }
        String pwd=teacher.getPassword();
        String ls=teacher.getLast_idcard();
        if(pwd==null) pwd=ls;
        if(ls!=null)
        {
            String idcard= new Md5Hash(ls,"",3).toString();
            teacher.setLast_idcard(idcard);
        }
        if(pwd!=null)
        {
            String lockpwd= new Md5Hash(pwd,"",3).toString();
            teacher.setPassword(lockpwd);
        }
        //对不为空的信息进行加密
        teacherinfo info=t.getInfo();
        if(info==null) return adminMapper.updateuser(teacher);//详细信息为空就只修改教师信息
        return adminMapper.updateuser(teacher)&&adminMapper.updateuserinfo(info);//修改教师信息和教师的详细信息
    }

    @Override
    public Boolean selectrole(String tea_id) {
        return adminMapper.selectrole(tea_id);
    }
}
