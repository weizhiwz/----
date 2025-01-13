package com.example.backweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backweb.mapper.CourseMapper;
import com.example.backweb.pojo.Course;
import com.example.backweb.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseService  extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    private  CourseMapper courseMapper;
    @Override
    public boolean delete(Course Course) {
        return courseMapper.deleteCourse(Course.getCourse_id());
    }

    @Override
    public List<Course> selectall() {
        return courseMapper.selectall();
    }

    @Override
    public boolean update(Course Course) {
        return courseMapper.updateCourse(Course);
    }

    @Override
    public Course selectone(String Course_id) {
        return courseMapper.selectoneCourse(Course_id);
    }

    @Override
    public List<Course> selectCourse(String Course_id) {
        return courseMapper.selectCourse(Course_id);
    }

    @Override
    public boolean insertCourse(Course Course) {
        return courseMapper.insertCourse(Course);
    }
}
