package com.example.backweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backweb.pojo.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.util.Date;
import java.util.List;

@Mapper
public interface PlanMapper extends BaseMapper<plan> {
    @Select("SELECT *\n" +
            "FROM teainfo as t\n" )
    @Results({
            @Result(property = "tea_id", column = "tea_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "gender", column = "gender", javaType = Boolean.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "age", column = "age", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
    })
    List<teacherinfo> selectallteacher();
    @Select("SELECT *\n" +
            "FROM course\n"+
            "WHERE tea_id=#{tea_id}")
    @Results(id="resultcourse",value = {
            @Result(property = "Course_id", column = "Course_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "Course_name", column = "Course_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "class_", column = "class_", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "people_num", column = "people_num", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "status", column = "status", javaType = Boolean.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "belong_num", column = "belong_num", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "tool", column = "tool", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "subject", column = "subject", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "tea_id", column = "tea_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
    })
    List<Course> selectcoursebytea(String tea_id);
    @Select("SELECT * FROM semester WHERE isnow=1")
    @Results(value = {
            @Result(property = "sem_id", column = "sem_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "sem_name", column = "sem_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
    })
    Semester nowsem();
    @Select("SELECT * FROM classplan WHERE course_id=#{course_id}")
    @Results(value = {
            @Result(property = "id", column = "id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "course_id", column = "course_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_id", column = "lab_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "start", column = "start", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "end", column = "end", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "start_time", column = "start_time", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "last", column = "last", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "xq", column = "xq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
    })
    List<plan> nowplan(String course_id);
//    @Select("SELECT * FROM classplan\n" +
//            "WHERE start_time BETWEEN #{startDate} AND #{endDate} AND lab_id=#{lab_id};")
    @Select("SELECT * FROM classplan \n" +
            "WHERE lab_id = #{lab_id} \n" +
            "AND #{startDate} < date_add(start_time, interval last week)\n" +
            "AND #{endDate} >= start_time;")
    @Results(id="planResults",value = {
            @Result(property = "id", column = "id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "course_id", column = "course_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_id", column = "lab_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "start", column = "start", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "end", column = "end", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "start_time", column = "start_time", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "last", column = "last", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "xq", column = "xq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
    })
    List<plan> selectplan(Date startDate,Date endDate,String lab_id);
    @Insert("INSERT INTO classplan " +
            "(`course_id`,`id`,`lab_id`,`start`,`end`,`start_time`,`last`, `xq`) " +
            "VALUES(#{course_id},#{id},#{lab_id},#{start},#{end},#{start_time},#{last},#{xq})")
    boolean insertplan(plan plan);
    @Insert("INSERT INTO classplan\n" +
            "    (`course_id`,`id`)\n" +
            "    VALUES(#{course_id},#{id});")
    boolean insertnull(plan plan);

    @Delete("DELETE FROM classplan\n" +
            "WHERE id=#{id}")
    boolean delete(String id);
    @Select("SELECT * FROM classplan WHERE id=#{plan_id}")
    @Results(value = {
            @Result(property = "id", column = "id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "course_id", column = "course_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_id", column = "lab_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "start", column = "start", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "end", column = "end", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "start_time", column = "start_time", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "last", column = "last", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "xq", column = "xq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
    })
    plan isexit(String plan_id);
    @Update("UPDATE classplan\n" +
            "SET \n" +
            "  course_id = #{course_id},\n" +
            "  lab_id = #{lab_id},\n" +
            "  start = #{start},\n" +
            "  end = #{end},\n" +
            "  start_time = #{start_time},\n" +
            "  last = #{last},\n" +
            "  xq = #{xq}\n" +
            "WHERE id = #{id};\n")
    @Results(value = {
            @Result(property = "id", column = "id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "course_id", column = "course_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_id", column = "lab_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "start", column = "start", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "end", column = "end", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "start_time", column = "start_time", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "last", column = "last", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "xq", column = "xq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
    })
    boolean update(plan plan);
    @Select("SELECT *\n" +
            "FROM classplan\n" +
            "WHERE start_time <= #{enddate}\n" +
            "  AND DATE_ADD(start_time, INTERVAL last week) >= #{begindate}\n" +
            "  AND xq = #{plan.xq} \n" +
            "  AND start <= #{plan.end}\n" +
            "  AND lab_id <= #{plan.lab_id}\n" +
            "  AND end >= #{plan.start};")
    @Results(value = {
            @Result(property = "id", column = "id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "course_id", column = "course_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lab_id", column = "lab_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "start", column = "start", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "end", column = "end", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "start_time", column = "start_time", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "last", column = "last", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "xq", column = "xq", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
    })
    List<plan> selecterror(plan plan,Date begindate,Date enddate);

}
