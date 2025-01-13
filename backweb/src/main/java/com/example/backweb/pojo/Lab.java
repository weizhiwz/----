package com.example.backweb.pojo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("实验室")
public class Lab implements Serializable {

    private static final long serialVersionUID = 1L;
    private String lab_id;
    private String lab_name;
    private String lab_info;
    private String location;
    private Boolean lab_isfull;


}
