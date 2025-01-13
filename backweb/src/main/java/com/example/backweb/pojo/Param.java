package com.example.backweb.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("登陆封装信息")
public class Param {
    private Teacher teacher;
    private Boolean rememberMe;
}
