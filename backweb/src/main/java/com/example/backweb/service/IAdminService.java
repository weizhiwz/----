package com.example.backweb.service;

import com.example.backweb.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backweb.pojo.teacherinfo;
import com.example.backweb.pojo.tti;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface IAdminService extends IService<Teacher> {
    List<Teacher> selectall();
    boolean update(tti t);
    teacherinfo selectone(String tea_id);
    List<Teacher> selectteacher(String id);
    Boolean selectrole(String tea_id);
}
