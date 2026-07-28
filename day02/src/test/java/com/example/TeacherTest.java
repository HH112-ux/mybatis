package com.example;

import com.example.mapper.TeacherMapper;
import com.example.pojo.Teacher;
import com.example.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class TeacherTest {

    @Test
    public void testInsert() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            Teacher[] teachers = {
                    new Teacher(1, "张三", "江苏省南京市", 35, "区域总监", 20000),
                    new Teacher(2, "李四", "江苏省苏州市", 32, "销售经理", 15000),
                    new Teacher(3, "王五", "四川省成都市", 26, "销售员", 8000),
                    new Teacher(4, "赵六", "吉林省长春市", 27, "财务", 10000),
                    new Teacher(5, "刘七", "吉林省吉林市", 24, "销售员", 6000),
                    new Teacher(6, "吴八", "陕西省西安市", 31, "销售员", 7000)
            };

            int count = 0;
            for (Teacher t : teachers) {
                count += mapper.insert(t);
            }

            System.out.println("成功插入 " + count + " 条记录");
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFindByAddr() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            List<Teacher> list = mapper.findByAddr("江苏省");

            System.out.println("========== 江苏省员工信息 ==========");
            System.out.printf("%-5s %-6s %-15s %-5s %-10s %-8s%n",
                    "ID", "姓名", "地址", "年龄", "职务", "工资");
            System.out.println("------------------------------------------------");
            for (Teacher t : list) {
                System.out.printf("%-5d %-6s %-15s %-5d %-10s %-8d%n",
                        t.getId(), t.getName(), t.getAddr(), t.getAge(), t.getJob(), t.getSal());
            }
            System.out.println("共 " + list.size() + " 条记录");
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAvgSalByAddr() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            Double avgSal = mapper.avgSalByAddr("吉林省");

            System.out.println("========== 吉林省员工平均工资 ==========");
            System.out.printf("平均工资：%.2f 元%n", avgSal);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPromoteSalesmen() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            int rows = mapper.promoteSalesmen(30);

            System.out.println("========== 提升销售员为销售经理 ==========");
            System.out.println("受影响行数：" + rows);

            // 打印提升后的结果验证
            List<Teacher> all = sqlSession.getMapper(TeacherMapper.class).findByAddr("");
            System.out.println("\n提升后全部数据：");
            for (Teacher t : all) {
                System.out.println(t);
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteByAge() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            int rows = mapper.deleteByAge(35);

            System.out.println("========== 删除年龄超过35岁的员工 ==========");
            System.out.println("删除行数：" + rows);
        } finally {
            sqlSession.close();
        }
    }
}
