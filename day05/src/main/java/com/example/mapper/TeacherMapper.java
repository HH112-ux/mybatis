package com.example.mapper;

import com.example.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TeacherMapper 接口 —— 配合 XML 动态 SQL 使用
 * 所有 SQL 在 TeacherMapper.xml 中通过动态标签 <foreach> <if> <where> <set> 实现
 */
public interface TeacherMapper {

    /**
     * 1. 动态拼接 SQL：批量插入数据
     *    使用 <foreach> 拼接多条 VALUES
     */
    int batchInsert(List<Teacher> teachers);

    /**
     * 2. 动态拼接 SQL：查询给定城市（可多个）的所有员工信息
     *    使用 <foreach> + <where> 拼接 IN 条件
     */
    List<Teacher> findByCities(@Param("cities") List<String> cities);

    /**
     * 3. 动态拼接 SQL：统计给定省份（可多个）的员工平均工资
     *    使用 <foreach> + <where> 拼接 OR LIKE 条件
     */
    Double avgSalByProvinces(@Param("provinces") List<String> provinces);

    /**
     * 4. 动态拼接 SQL：更新给定城市员工的工资和职务
     *    使用 <set> + <if> 动态拼接 SET 子句
     */
    int updateByCity(@Param("city") String city,
                     @Param("sal") Integer sal,
                     @Param("job") String job);

    /**
     * 5. 动态拼接 SQL：删除给定 id 数组的员工
     *    使用 <foreach> 拼接 IN 条件
     */
    int deleteByIds(@Param("ids") int[] ids);

    /**
     * 辅助方法：查询全部（验证结果用）
     */
    List<Teacher> findAll();
}
