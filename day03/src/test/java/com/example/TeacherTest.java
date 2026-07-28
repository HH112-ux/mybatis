package com.example;

import com.example.mapper.TeacherMapper;
import com.example.pojo.Teacher;
import com.example.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * 测试类 —— 使用 Mapper 注解方式
 * 按顺序执行：插入 → 查询江苏 → 计算吉林平均工资 → 提升销售员 → 删除超龄员工
 */
public class TeacherTest {

    /**
     * 1. 向表中插入 6 条数据
     */
    @Test
    public void testInsert() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            Teacher[] teachers = {
                    new Teacher(null, "张三", "江苏省南京市", 35, "区域总监", 20000),
                    new Teacher(null, "李四", "江苏省苏州市", 32, "销售经理", 15000),
                    new Teacher(null, "王五", "四川省成都市", 26, "销售员", 8000),
                    new Teacher(null, "赵六", "吉林省长春市", 27, "财务", 10000),
                    new Teacher(null, "刘七", "吉林省吉林市", 24, "销售员", 6000),
                    new Teacher(null, "吴八", "陕西省西安市", 31, "销售员", 7000)
            };

            int count = 0;
            for (Teacher t : teachers) {
                count += mapper.insert(t);
            }

            System.out.println("===== 1. 插入数据 =====");
            System.out.println("成功插入 " + count + " 条记录");
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 2. 查询所有江苏省的员工信息
     */
    @Test
    public void testFindByAddr() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            List<Teacher> list = mapper.findByAddr("江苏省");

            System.out.println("===== 2. 查询江苏省员工 =====");
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

    /**
     * 3. 计算吉林省员工的平均工资
     */
    @Test
    public void testAvgSalByAddr() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            Double avgSal = mapper.avgSalByAddr("吉林省");

            System.out.println("===== 3. 吉林省员工平均工资 =====");
            System.out.printf("平均工资：%.2f 元%n", avgSal);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 4. 将所有年龄超过30岁的销售员提升为销售经理
     */
    @Test
    public void testPromoteSalesmen() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            int rows = mapper.promoteSalesmen(30);

            System.out.println("===== 4. 提升销售员为销售经理 =====");
            System.out.println("受影响行数：" + rows);

            // 打印提升后全部数据
            List<Teacher> all = mapper.findAll();
            System.out.println("\n提升后全部数据：");
            for (Teacher t : all) {
                System.out.println(t);
            }
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 5. 删除年龄超过35岁的员工
     */
    @Test
    public void testDeleteByAge() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            int rows = mapper.deleteByAge(35);

            System.out.println("===== 5. 删除年龄超过35岁的员工 =====");
            System.out.println("删除行数：" + rows);

            // 打印删除后剩余数据
            List<Teacher> all = mapper.findAll();
            System.out.println("\n删除后剩余数据：");
            for (Teacher t : all) {
                System.out.println(t);
            }
        } finally {
            sqlSession.close();
        }
    }
}
