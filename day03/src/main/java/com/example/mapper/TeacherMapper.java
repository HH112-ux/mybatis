package com.example.mapper;

import com.example.pojo.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * TeacherMapper 接口 —— 使用 MyBatis 注解方式
 * 不需要 XML 映射文件，SQL 直接写在注解中
 */
public interface TeacherMapper {

    /**
     * 1. 插入一条教师记录
     */
    @Insert("INSERT INTO teacher (name, addr, age, job, sal) " +
            "VALUES (#{name}, #{addr}, #{age}, #{job}, #{sal})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Teacher teacher);

    /**
     * 2. 查询所有某省的员工信息（模糊查询）
     */
    @Select("SELECT id, name, addr, age, job, sal FROM teacher " +
            "WHERE addr LIKE CONCAT(#{addr}, '%')")
    List<Teacher> findByAddr(String addr);

    /**
     * 3. 计算某省员工的平均工资
     */
    @Select("SELECT AVG(sal) FROM teacher " +
            "WHERE addr LIKE CONCAT(#{addr}, '%')")
    Double avgSalByAddr(String addr);

    /**
     * 4. 将所有年龄超过指定值的销售员提升为销售经理
     */
    @Update("UPDATE teacher SET job = '销售经理' " +
            "WHERE job = '销售员' AND age > #{age}")
    int promoteSalesmen(@Param("age") int age);

    /**
     * 5. 删除年龄超过指定值的员工
     */
    @Delete("DELETE FROM teacher WHERE age > #{age}")
    int deleteByAge(@Param("age") int age);

    /**
     * 辅助方法：查询全部员工（用于验证结果）
     */
    @Select("SELECT id, name, addr, age, job, sal FROM teacher")
    List<Teacher> findAll();
}
