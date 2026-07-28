package com.example;

import com.example.mapper.TeacherMapper;
import com.example.pojo.Teacher;
import com.example.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class TeacherTest {

    @Test
    public void testFindAll() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);

            List<Teacher> teacherList = teacherMapper.findAll();

            System.out.println("========== 查询全部教师信息 ==========");
            System.out.printf("%-5s %-10s %-20s %-5s %-10s %-8s%n",
                    "ID", "姓名", "地址", "年龄", "职务", "工资");
            System.out.println("--------------------------------------------------------------");
            for (Teacher teacher : teacherList) {
                System.out.printf("%-5d %-10s %-20s %-5d %-10s %-8d%n",
                        teacher.getId(),
                        teacher.getName(),
                        teacher.getAddr(),
                        teacher.getAge(),
                        teacher.getJob(),
                        teacher.getSal());
            }
            System.out.println("共查询到 " + teacherList.size() + " 条记录");
        } finally {
            sqlSession.close();
        }
    }
}
