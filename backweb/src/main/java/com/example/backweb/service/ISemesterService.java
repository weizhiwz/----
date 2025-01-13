package com.example.backweb.service;

import com.example.backweb.pojo.Semester;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

public interface ISemesterService extends IService<Semester> {
    boolean delete(Semester sem);
    List<Semester> selectall();
    boolean update(Semester sem);
    Semester selectone(String sem_id);
    List<Semester> selectlab(String sem_id);
    boolean insertlab(Semester sem);
}
