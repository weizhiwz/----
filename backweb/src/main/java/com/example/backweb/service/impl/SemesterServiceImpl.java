package com.example.backweb.service.impl;

import com.example.backweb.pojo.Semester;
import com.example.backweb.mapper.SemesterMapper;
import com.example.backweb.service.ISemesterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SemesterServiceImpl extends ServiceImpl<SemesterMapper, Semester> implements ISemesterService {
    @Autowired
    SemesterMapper semesterMapper;
    @Override
    public boolean delete(Semester sem) {
        return semesterMapper.deletesem(sem.getSem_id());
    }

    @Override
    public List<Semester> selectall() {
        return semesterMapper.selectall();
    }

    @Override
    public boolean update(Semester sem) {
        List<Semester> selectall = semesterMapper.selectall();
//        System.out.println(selectall.toString());
        if(sem.getIsnow())
        {
            for(Semester item:selectall)
            {
                if(item.getIsnow()){
//                    System.out.println(item.toString());
                    item.setIsnow(false);
                    semesterMapper.updatesem(item);
                }
            }
        }
        return semesterMapper.updatesem(sem);
    }
//将更新的学期设置为本学期，将之前的本学期设置为false
    @Override
    public Semester selectone(String sem_id) {
        return semesterMapper.selectonesem(sem_id);
    }

    @Override
    public List<Semester> selectlab(String sem_id) {
        return semesterMapper.selectsem(sem_id);
    }

    @Override
    public boolean insertlab(Semester sem) {
        List<Semester> selectall = semesterMapper.selectall();
//        System.out.println(selectall.toString());
        if(sem.getIsnow())
        {
            for(Semester item:selectall)
            {
                if(item.getIsnow()){
//                    System.out.println(item.toString());
                    item.setIsnow(false);
                    semesterMapper.updatesem(item);
                }
            }
        }
        return semesterMapper.insertsem(sem);
    }
    //将插入的新学期设置为本学期
}
