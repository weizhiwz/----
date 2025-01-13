package com.example.backweb.pojo;

import java.time.LocalDate;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("学期信息")
public class Semester implements Serializable {

    private static final long serialVersionUID = 1L;
    private String sem_id;
    private String sem_name;
    private LocalDate begindate;
    private Integer week;
    private Boolean isnow;
    private Integer dayclass;//一天几节课

}
