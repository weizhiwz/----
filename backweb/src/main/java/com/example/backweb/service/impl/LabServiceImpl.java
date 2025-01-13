package com.example.backweb.service.impl;

import com.example.backweb.pojo.Lab;
import com.example.backweb.mapper.LabMapper;
import com.example.backweb.service.ILabService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LabServiceImpl extends ServiceImpl<LabMapper, Lab> implements ILabService {
    @Autowired
    LabMapper labMapper;
    @Override
    public boolean delete(Lab lab) {
        return labMapper.deletelab(lab.getLab_id());
    }

    @Override
    public List<Lab> selectall() {
        return labMapper.selectall();
    }

    @Override
    public boolean update(Lab lab) {
        return labMapper.updatelab(lab);
    }

    @Override
    public Lab selectone(String lab_id) {
        return labMapper.selectonelab(lab_id);
    }

    @Override
    public List<Lab> selectlab(String lab_id) {
        return labMapper.selectlab(lab_id);
    }

    @Override
    public boolean insertlab(Lab lab) {
        return labMapper.insertlab(lab);
    }
}
