package com.example.backweb.mapper;

import com.example.backweb.pojo.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("select * from teacher where tea_id = #{tea_id}")
    @Results({
            @Result(property = "tea_id", column = "tea_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "last_idcard", column = "last_idcard", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "isroot", column = "isroot", javaType = Boolean.class, jdbcType = JdbcType.TINYINT),
    })
    Teacher isexists(String tea_id);
    @Insert("INSERT INTO teacher (tea_id,password,last_idcard,isroot) values (#{tea_id},#{password},#{last_idcard},#{isroot})")
    boolean enroll(Teacher teacher);
    @Update("UPDATE teacher SET password=#{password} WHERE tea_id=#{tea_id} AND last_idcard=#{last_idcard}")
    boolean forget(Teacher teacher);

}
