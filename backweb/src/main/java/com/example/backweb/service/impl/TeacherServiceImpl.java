package com.example.backweb.service.impl;

import com.example.backweb.mapper.AdminMapper;
import com.example.backweb.pojo.Teacher;
import com.example.backweb.mapper.TeacherMapper;
import com.example.backweb.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    AdminMapper adminMapper;
    @Override
    public boolean enroll(Teacher teacher) {
        adminMapper.addinfo(teacher.getTea_id());
        return teacherMapper.enroll(teacher);
    }
    @Override
    public boolean forget(Teacher teacher) {
        return teacherMapper.forget(teacher);
    }
    @Override
    public Teacher login(String tea_id) {
        return teacherMapper.isexists(tea_id);
    }

    @Override
    public boolean isexists(String tea_id) {
        return teacherMapper.isexists(tea_id)!=null;
    }
}
