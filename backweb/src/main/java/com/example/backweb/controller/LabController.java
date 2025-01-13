package com.example.backweb.controller;

import com.example.backweb.common.lang.Result;
import com.example.backweb.pojo.Lab;
import com.example.backweb.service.impl.LabServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Api(tags = "实验室管理")
@RequestMapping("/lab")
public class LabController {
    @Autowired
    LabServiceImpl labService;
    //增加
    @PostMapping("/add")
    @ApiOperation("增加新实验室")
    public Result add(@RequestBody Lab lab) {
        if(labService.selectone(lab.getLab_id())!=null) return Result.fail("实验室编号已存在");
        else
        {
            lab.setLab_isfull(false);
            lab.setLab_id("lab"+lab.getLab_id());
            labService.insertlab(lab);
            return Result.success("实验室添加成功");
        }
    }
    //删除
    @PostMapping("/delete")
    @ApiOperation("删除实验室")
    public Result delete(@RequestBody Lab lab) {
        if(labService.delete(lab)) return Result.success("实验室删除成功");
        else return Result.fail("实验室删除失败");
    }
    //更新
    @PostMapping("/update")
    @ApiOperation("更新实验室信息")
    public Result update(@RequestBody Lab lab) {
        //更新操作
        if(labService.update(lab)) return Result.success("实验室更新成功");
        else return Result.fail("实验室更新失败");
    }
    //返回
    @GetMapping("/selectall")
    @ApiOperation("返回所有实验室信息")
    public Result selectall() {
        List<Lab> labs=labService.selectall();
        if(labs==null) return Result.fail("暂无数据");
        else return Result.success("ok",labs);
    }
    //回显
    @GetMapping("/selectone")
    @ApiOperation("实验室信息回显")
    public Result selectone(String lab_id) {
        if(labService.selectone(lab_id)!=null) return Result.success("信息返回成功",labService.selectone(lab_id));
        return Result.fail("信息返回失败");
    }
    @GetMapping("/search")
    @ApiOperation("搜索实验室")
    public Result search(String id){
        if(labService.selectlab(id)!=null) return Result.success("ok",labService.selectlab(id));
        else return Result.fail("暂无数据");
    }
}
