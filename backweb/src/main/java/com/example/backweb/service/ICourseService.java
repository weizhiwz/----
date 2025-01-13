package com.example.backweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backweb.pojo.Course;
import java.util.List;

public interface ICourseService extends IService<Course> {
    boolean delete(Course Course);
    List<Course> selectall();
    boolean update(Course Course);
    Course selectone(String Course_id);
    List<Course> selectCourse(String Course_id);
    boolean insertCourse(Course Course);
}
