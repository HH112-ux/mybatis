package com.example.mapper;

import com.example.pojo.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * TeacherMapper 接口 —— 使用 MyBatis 注解方式实现完整 CRUD
 * C: Create (insert)    增
 * R: Retrieve (select)  查
 * U: Update (update)    改
 * D: Delete (delete)    删
 */
public interface TeacherMapper {

    /**
     * 插入一条教师记录
     */
    @Insert("INSERT INTO teacher (name, addr, age, job, sal) " +
            "VALUES (#{name}, #{addr}, #{age}, #{job}, #{sal})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Teacher teacher);

    // ==================== 查 Retrieve ====================

    /**
     * 查询全部教师
     */
    @Select("SELECT id, name, addr, age, job, sal FROM teacher")
    List<Teacher> findAll();

    /**
     * 根据 ID 查询单个教师
     */
    @Select("SELECT id, name, addr, age, job, sal FROM teacher WHERE id = #{id}")
    Teacher findById(@Param("id") int id);

    /**
     * 根据姓名模糊查询
     */
    @Select("SELECT id, name, addr, age, job, sal FROM teacher WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Teacher> findByName(@Param("name") String name);

    /**
     * 查询教师总数
     */
    @Select("SELECT COUNT(*) FROM teacher")
    int count();

    // ==================== 改 Update ====================

    /**
     * 根据 ID 更新教师全部字段
     */
    @Update("UPDATE teacher SET name=#{name}, addr=#{addr}, age=#{age}, job=#{job}, sal=#{sal} WHERE id=#{id}")
    int update(Teacher teacher);

    /**
     * 根据 ID 仅更新工资
     */
    @Update("UPDATE teacher SET sal=#{sal} WHERE id=#{id}")
    int updateSal(@Param("id") int id, @Param("sal") int sal);

    // ==================== 删 Delete ====================

    /**
     * 根据 ID 删除教师
     */
    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int deleteById(@Param("id") int id);

    /**
     * 删除全部教师（慎用）
     */
    @Delete("DELETE FROM teacher")
    int deleteAll();
}
