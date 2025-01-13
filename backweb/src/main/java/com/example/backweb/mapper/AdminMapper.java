package com.example.backweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backweb.pojo.Teacher;
import com.example.backweb.pojo.teacherinfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.util.List;
@Mapper

public interface AdminMapper extends BaseMapper<Teacher> {
    @Delete("DELETE FROM teacher WHERE tea_id=#{tea_id}")
    boolean deleteUser(String tea_id);
    @Delete("DELETE FROM teainfo WHERE tea_id=#{tea_id}")
    boolean deleteUserinfo(String tea_id);
    @Select("SELECT * FROM teacher")
    @Results({
            @Result(property = "tea_id", column = "tea_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "last_idcard", column = "last_idcard", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "isroot", column = "isroot", javaType = Boolean.class, jdbcType = JdbcType.TINYINT)
    })
    List<Teacher> selectall();
    @Update({
            "<script>",
            "UPDATE teacher",
            "SET",
            "<trim prefixOverrides=',' suffixOverrides=','>",
            "<if test='isroot != null'>isroot = #{isroot},</if>",
            "<if test='password != null'>password = #{password},</if>",
            "<if test='last_idcard != null'>last_idcard = #{last_idcard},</if>",
            "</trim>",
            "WHERE tea_id = #{tea_id}",
            "</script>"
    })

    boolean updateuser(Teacher teacher);
    @Update({
            "<script>",
            "UPDATE teainfo",
            "SET",
            "<trim prefixOverrides=',' suffixOverrides=','>",
            "<if test='name != null'>name = #{name},</if>",
            "<if test='gender != null'>gender = #{gender},</if>",
            "<if test='age != null'>age = #{age}</if>",
            "</trim>",
            "WHERE tea_id = #{tea_id}",
            "</script>"
    })
    boolean updateuserinfo(teacherinfo teacherinfo);
    @Select("SELECT name,gender,age\n" +
            "FROM teainfo as t\n" +
            "WHERE t.tea_id=#{tea_id};")
    @Results({
            @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "gender", column = "gender", javaType = Boolean.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "age", column = "age", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
    })
    teacherinfo selectone(String tea_id);
    @Insert("INSERT INTO teainfo(`tea_id`) VALUES (#{tea_id})")
    void addinfo(String tea_id);
    @Select("SELECT *\n" +
            "FROM teacher as t\n" +
            "WHERE t.tea_id like CONCAT('%',#{tea_id}, '%');")
    @Results({
            @Result(property = "tea_id", column = "tea_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "last_idcard", column = "last_idcard", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "isroot", column = "isroot", javaType = Boolean.class, jdbcType = JdbcType.TINYINT)
    })
    List<Teacher> selectteacher(String tea_id);
    @Select("SELECT isroot\n" +
            "FROM teacher as t\n" +
            "WHERE t.tea_id=#{tea_id};")
    @Results({
            @Result(property = "isroot", column = "isroot", javaType = Boolean.class, jdbcType = JdbcType.TINYINT)
    })
    Boolean selectrole(String tea_id);
}
