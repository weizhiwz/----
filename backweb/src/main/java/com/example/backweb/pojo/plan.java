package com.example.backweb.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("排课课程信息")
public class plan {
    private String course_id;//课程编号
    private String lab_id;//实验室编号
    private String id;//排课编号
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date start_time;//那天开始
    private Integer last;//持续时间
    private Integer start;//第几节课开始
    private Integer end;//第几节课结束
    private Integer xq;//周几上课
}
