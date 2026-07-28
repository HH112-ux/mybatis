package com.example.mapper;

import com.example.pojo.Teacher;

import java.util.List;

public interface TeacherMapper {

    /**
     * 插入一条教师记录
     */
    int insert(Teacher teacher);

    /**
     * 根据地址模糊查询（查询所有某省的员工）
     */
    List<Teacher> findByAddr(String addr);

    /**
     * 计算某省员工的平均工资
     */
    Double avgSalByAddr(String addr);

    /**
     * 将所有年龄超过30岁的销售员提升为销售经理
     */
    int promoteSalesmen(int age);

    /**
     * 删除年龄超过指定值的员工
     */
    int deleteByAge(int age);
}
