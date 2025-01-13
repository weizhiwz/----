package com.example.backweb.mapper;

import com.example.backweb.pojo.Lab;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.util.List;

@Mapper
public interface LabMapper extends BaseMapper<Lab> {
    @Delete("DELETE FROM lab WHERE lab_id=#{lab_id}")
    boolean deletelab(String lab_id);

    @Select("SELECT * FROM lab")
    @Results({
            @Result(property = "lab_id", column = "lab_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_name", column = "lab_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_info", column = "lab_info", javaType = String.class),
            @Result(property = "location", column = "location", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_isfull", column = "lab_isfull", javaType = Boolean.class, jdbcType = JdbcType.TINYINT)
    })
    List<Lab> selectall();
    @Update({
            "<script>",
            "UPDATE lab",
            "SET",
            "<if test='lab_name != null'>lab_name = #{lab_name}</if>",
            "<if test='lab_info != null'>,lab_info = #{lab_info}</if>",
            "<if test='location != null'>,location = #{location}</if>",
            "<if test='lab_isfull != null'>,lab_isfull = #{lab_isfull}</if>",
            "WHERE lab_id = #{lab_id}",
            "</script>"
    })
    boolean updatelab(Lab lab);
    @Select("SELECT *\n" +
            "FROM lab\n" +
            "WHERE lab_id= #{lab_id};")
    @Results({
            @Result(property = "lab_id", column = "lab_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_name", column = "lab_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_info", column = "lab_info", javaType = String.class),
            @Result(property = "location", column = "location", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_isfull", column = "lab_isfull", javaType = Boolean.class, jdbcType = JdbcType.TINYINT)
    })
    Lab selectonelab(String lab_id);
    @Select("SELECT *\n" +
            "FROM lab\n" +
            "WHERE lab_id like CONCAT('%',#{lab_id}, '%');")
    @Results({
            @Result(property = "lab_id", column = "lab_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_name", column = "lab_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_info", column = "lab_info", javaType = String.class),
            @Result(property = "location", column = "location", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_isfull", column = "lab_isfull", javaType = Boolean.class, jdbcType = JdbcType.TINYINT)
    })
    List<Lab> selectlab(String lab_id);
    @Insert("INSERT INTO lab (lab_id,lab_name,lab_info,location,lab_isfull) values (#{lab_id},#{lab_name},#{lab_info},#{location},#{lab_isfull})")
    boolean insertlab(Lab lab);
}
