package com.example.backweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backweb.pojo.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface Iplan extends IService<plan> {
    List<teacherinfo> selectall();
    List<Course>selectcoursebytea(String tea_id);
    Semester nowsem();
    List<plan> selectallplan(String course_id);
    List<plan> selectbyweek(String timerange,String lab_id) throws ParseException;
    boolean insertplan(plan plan);
    boolean insertnull(String course_id);
    boolean delete(String id);
    plan isexited(String plan_id);
    boolean update(plan plan);
    List<plan> planerror(plan plan, Date begindate, Date enddate);
}
