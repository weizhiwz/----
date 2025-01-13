package com.example.backweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backweb.pojo.Course;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    @Delete("DELETE FROM Course WHERE Course_id=#{Course_id}")
    boolean deleteCourse(String Course_id);

    @Select("SELECT * FROM Course")
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
    List<Course> selectall();
    @Update({
            "<script>",
            "UPDATE Course",
            "SET",
            "<if test='Course_name != null'>Course_name = #{Course_name},</if>",
            "<if test='class_ != null'>class_ = #{class_},</if>",
            "<if test='people_num != null'>people_num = #{people_num},</if>",
            "<if test='status != null'>status = #{status},</if>",
            "<if test='belong_num != null'>belong_num = #{belong_num},</if>",
            "<if test='tool != null'>tool = #{tool},</if>",
            "<if test='subject != null'>subject = #{subject},</if>",
            "<if test='tea_id != null'>tea_id = #{tea_id}</if>",
            "WHERE Course_id = #{Course_id}",
            "</script>"
    })
    boolean updateCourse(Course Course);
    @Select("SELECT *\n" +
            "FROM Course\n" +
            "WHERE Course_id= #{Course_id};")
    @ResultMap("resultcourse")
    Course selectoneCourse(String Course_id);
    @Select("SELECT *\n" +
            "FROM Course\n" +
            "WHERE Course_id like CONCAT('%',#{Course_id}, '%');")
    @ResultMap("resultcourse")
    List<Course> selectCourse(String Course_id);
    @Insert("INSERT INTO Course (Course_id,Course_name,class_,people_num,status,belong_num,tool,subject,tea_id) " +
            "values (#{Course_id},#{Course_name},#{class_},#{people_num},#{status},#{belong_num},#{tool},#{subject},#{tea_id})")
    boolean insertCourse(Course Course);
}
