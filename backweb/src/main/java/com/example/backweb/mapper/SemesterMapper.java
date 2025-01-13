package com.example.backweb.mapper;

import com.example.backweb.pojo.Semester;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.util.List;

@Mapper
public interface SemesterMapper extends BaseMapper<Semester> {
    @Delete("DELETE FROM semester WHERE sem_id=#{sem_id}")
    boolean deletesem(String sem_id);

    @Select("SELECT * FROM semester")
    @Results({
            @Result(property = "sem_id", column = "sem_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "sem_name", column = "sem_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "begindate", column = "begindate", javaType = java.time.LocalDate.class,jdbcType = JdbcType.DATE),
            @Result(property = "week", column = "week", javaType = Integer.class),
            @Result(property = "isnow", column = "isnow", javaType = Boolean.class,jdbcType = JdbcType.TINYINT),
            @Result(property = "dayclass", column = "dayclass", javaType = Integer.class)
    })
    List<Semester> selectall();
    @Update({
            "<script>",
            "UPDATE semester",
            "SET",
            "<if test='sem_name != null'>sem_name = #{sem_name},</if>",
            "<if test='begindate != null'>begindate = #{begindate},</if>",
            "<if test='week != null'>week = #{week},</if>",
            "<if test='dayclass != null'>dayclass = #{dayclass},</if>",
            "<if test='isnow != null'>isnow = #{isnow}</if>",
            "WHERE sem_id = #{sem_id}",
            "</script>"
    })
    boolean updatesem(Semester sem);
    @Select("SELECT *\n" +
            "FROM semester\n" +
            "WHERE sem_id= #{sem_id};")
    @Results({
            @Result(property = "sem_id", column = "sem_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "sem_name", column = "sem_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "begindate", column = "begindate", javaType = java.time.LocalDate.class,jdbcType = JdbcType.DATE),
            @Result(property = "week", column = "week", javaType = Integer.class),
            @Result(property = "isnow", column = "isnow", javaType = Boolean.class,jdbcType = JdbcType.TINYINT),
            @Result(property = "dayclass", column = "dayclass", javaType = Integer.class)
    })
    Semester selectonesem(String sem_id);
    @Select("SELECT *\n" +
            "FROM semester\n" +
            "WHERE sem_id like CONCAT('%',#{sem_id}, '%');")
    @Results({
            @Result(property = "sem_id", column = "sem_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "sem_name", column = "sem_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "begindate", column = "begindate", javaType = java.time.LocalDate.class,jdbcType = JdbcType.DATE),
            @Result(property = "week", column = "week", javaType = Integer.class),
            @Result(property = "isnow", column = "isnow", javaType = Boolean.class,jdbcType = JdbcType.TINYINT),
            @Result(property = "dayclass", column = "dayclass", javaType = Integer.class)
    })
    List<Semester> selectsem(String sem_id);
    @Insert("INSERT INTO semester (sem_id,sem_name,begindate,week,isnow,dayclass) values (#{sem_id},#{sem_name},#{begindate},#{week},#{isnow},#{dayclass})")
    boolean insertsem(Semester sem);
}
