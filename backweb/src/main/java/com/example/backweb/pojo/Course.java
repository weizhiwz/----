package com.example.backweb.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("课程")
public class Course {
    public String Course_id;
    public String Course_name;
    public String Class_;//班级
    public Integer People_num;
    public Boolean Status;//是否必修 1必修 0选修
    public String Belong_num;//所属学期
    public String Tool;//实验环境
    public String Subject;
    public String tea_id;
}
