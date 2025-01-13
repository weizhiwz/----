package com.example.backweb.service;

import com.example.backweb.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface ITeacherService extends IService<Teacher> {
    Teacher login(String tea_id);
    boolean isexists(String tea_id);
    boolean enroll(Teacher teacher);
    boolean forget(Teacher teacher);
}
