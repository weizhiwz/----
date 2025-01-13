package com.example.backweb.pojo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Component
@ApiModel("教师账户信息")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;
    private String tea_id;
    private String password;
    private String last_idcard;//身份证后6为
    private Boolean isroot = null;//是不是管理员

}
