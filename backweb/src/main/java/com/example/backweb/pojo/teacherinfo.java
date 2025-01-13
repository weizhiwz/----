package com.example.backweb.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("教师详细信息")
public class teacherinfo {
    private String tea_id;
    private int age;
    private String name;
    private Boolean gender;//1男 0女
}
