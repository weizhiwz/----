package com.example.backweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backweb.mapper.PlanMapper;
import com.example.backweb.pojo.Course;
import com.example.backweb.pojo.Semester;
import com.example.backweb.pojo.plan;
import com.example.backweb.pojo.teacherinfo;
import com.example.backweb.service.Iplan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, plan> implements Iplan {
    @Autowired
    PlanMapper planMapper;

    @Override
    public List<teacherinfo> selectall() {
        return planMapper.selectallteacher();
    }

    @Override
    public List<Course> selectcoursebytea(String tea_id) {
        return planMapper.selectcoursebytea(tea_id);
    }

    @Override
    public Semester nowsem() {
        return planMapper.nowsem();
    }

    @Override
    public List<plan> selectallplan(String course_id) {
        return planMapper.nowplan(course_id);
    }

    @Override
    public List<plan> selectbyweek(String timerange,String lab_id) throws ParseException {
//        System.out.println(timerange);
        String[] dateParts = timerange.split("-");//将传入的表示日期范围的字符串 timerange 通过 split("-") 方法按照 “-” 进行分割
        String startDateString = dateParts[0];//开始时间
        String endDateString = dateParts[1];//结束时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date startDate = format.parse(startDateString);
        Date endDate = format.parse(endDateString);//将时间改为指定的日期格式
        return planMapper.selectplan(startDate,endDate,lab_id);
    }

    @Override
    public boolean insertplan(plan plan) {
        return planMapper.insertplan(plan);
    }

    @Override
    public boolean insertnull(String course_id) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("plan");

        // 生成指定位数的随机数字
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // 生成0-9的随机数字
            sb.append(digit);
        }
        plan plan= new plan().setCourse_id(course_id).setId(sb.toString());
        return planMapper.insertnull(plan);
    }
//只有课程id时的插入方法
    @Override
    public boolean delete(String id) {
        return planMapper.delete(id);
    }

    @Override
    public plan isexited(String plan_id) {
        return planMapper.isexit(plan_id);
    }

    @Override
    public boolean update(plan plan) {
        return planMapper.update(plan);
    }

    @Override
    public List<plan> planerror(plan plan, Date begindate, Date enddate) {
        System.out.println("plan"+plan.toString());
        System.out.println("begindate"+begindate);
        System.out.println("enddate"+enddate);
        return planMapper.selecterror(plan,begindate,enddate);
    }
    //有错误时的处理方法
}
