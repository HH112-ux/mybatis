package com.example.mapper;

import com.example.pojo.Teacher;

import java.util.List;

public interface TeacherMapper {

    /**
     * 查询全部教师信息
     */
    List<Teacher> findAll();
}
