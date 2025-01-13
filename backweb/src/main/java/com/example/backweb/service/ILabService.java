package com.example.backweb.service;

import com.example.backweb.pojo.Lab;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

public interface ILabService extends IService<Lab> {
    boolean delete(Lab lab);
    List<Lab> selectall();
    boolean update(Lab lab);
    Lab selectone(String lab_id);
    List<Lab> selectlab(String lab_id);
    boolean insertlab(Lab lab);
}
