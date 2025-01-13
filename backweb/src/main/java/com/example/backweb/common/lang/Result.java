package com.example.backweb.common.lang;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("结果类")
public class Result implements Serializable {
    private int code;
    private String msg;
    private Object data;
    public static Result success(String msg) {
        Result m=new Result();
        m.setCode(1);
        m.setMsg(msg);
        return m;
    }
    public static Result success(String msg,Object data) {
        Result m=new Result();
        m.setCode(1);
        m.setMsg(msg);
        m.setData(data);
        return m;
    }
    public static Result fail(String msg) {
        Result m=new Result();
        m.setCode(0);
        m.setMsg(msg);
        return m;
    }
    public static Result fail(String msg,Object data) {
        Result m=new Result();
        m.setCode(0);
        m.setMsg(msg);
        m.setData(data);
        return m;
    }
    public Result(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
