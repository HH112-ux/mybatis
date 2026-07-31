package com.example;

import com.example.mapper.TeacherMapper;
import com.example.pojo.Teacher;
import com.example.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 测试类 —— 验证动态 SQL 的 5 个操作
 * 按顺序执行：批量插入 → 按城市查询 → 按省份统计平均工资 → 按城市更新 → 按ID数组删除
 */
public class TeacherTest {

    /**
     * 打印所有教师信息（工具方法）
     */
    private void printAll(TeacherMapper mapper) {
        List<Teacher> list = mapper.findAll();
        System.out.printf("%-5s %-8s %-15s %-5s %-10s %-8s%n",
                "ID", "姓名", "地址", "年龄", "职务", "工资");
        System.out.println("----------------------------------------------------------");
        for (Teacher t : list) {
            System.out.printf("%-5d %-8s %-15s %-5d %-10s %-8d%n",
                    t.getId(), t.getName(), t.getAddr(), t.getAge(), t.getJob(), t.getSal());
        }
        System.out.println("共 " + list.size() + " 条记录\n");
    }

    /**
     * 1. 动态批量插入 6 条数据
     *    动态标签：<foreach>
     */
    @Test
    public void testBatchInsert() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            List<Teacher> teachers = Arrays.asList(
                    new Teacher(null, "张三", "江苏省南京市", 35, "区域总监", 20000),
                    new Teacher(null, "李四", "江苏省苏州市", 32, "销售经理", 15000),
                    new Teacher(null, "王五", "四川省成都市", 26, "销售员", 8000),
                    new Teacher(null, "赵六", "吉林省长春市", 27, "财务", 10000),
                    new Teacher(null, "刘七", "吉林省吉林市", 24, "销售员", 6000),
                    new Teacher(null, "吴八", "陕西省西安市", 31, "销售员", 7000)
            );

            int rows = mapper.batchInsert(teachers);

            System.out.println("===== 1. 动态批量插入 =====");
            System.out.println("成功插入 " + rows + " 条记录");
            System.out.println("\n插入后全部数据：");
            printAll(mapper);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 2. 动态查询给定城市的所有员工信息
     *    动态标签：<foreach> + <where>（IN 条件）
     */
    @Test
    public void testFindByCities() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            // 查询 南京市 和 苏州市 的员工
            List<String> cities = Arrays.asList("江苏省南京市", "江苏省苏州市");
            List<Teacher> list = mapper.findByCities(cities);

            System.out.println("===== 2. 动态查询城市 [南京市, 苏州市] 员工 =====");
            System.out.printf("%-5s %-8s %-15s %-5s %-10s %-8s%n",
                    "ID", "姓名", "地址", "年龄", "职务", "工资");
            System.out.println("----------------------------------------------------------");
            for (Teacher t : list) {
                System.out.printf("%-5d %-8s %-15s %-5d %-10s %-8d%n",
                        t.getId(), t.getName(), t.getAddr(), t.getAge(), t.getJob(), t.getSal());
            }
            System.out.println("共 " + list.size() + " 条记录");
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 3. 动态统计给定省份的员工平均工资
     *    动态标签：<foreach> + <where>（OR LIKE 条件）
     */
    @Test
    public void testAvgSalByProvinces() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            // 统计 吉林省 和 四川省 的平均工资
            List<String> provinces = Arrays.asList("吉林省", "四川省");
            Double avgSal = mapper.avgSalByProvinces(provinces);

            System.out.println("===== 3. 动态统计 [吉林省, 四川省] 平均工资 =====");
            System.out.printf("平均工资：%.2f 元%n", avgSal);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 4. 动态更新给定城市员工的工资和职务
     *    动态标签：<set> + <if>
     */
    @Test
    public void testUpdateByCity() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            System.out.println("===== 4. 动态更新 [成都市] 员工的工资和职务 =====");
            System.out.println("--- 更新前 ---");
            List<Teacher> before = mapper.findByCities(Arrays.asList("四川省成都市"));
            for (Teacher t : before) {
                System.out.println(t);
            }

            // 将成都市的员工工资改为 12000，职务改为 销售经理
            int rows = mapper.updateByCity("四川省成都市", 12000, "销售经理");

            System.out.println("\n受影响行数：" + rows);
            System.out.println("--- 更新后 ---");
            List<Teacher> after = mapper.findByCities(Arrays.asList("四川省成都市"));
            for (Teacher t : after) {
                System.out.println(t);
            }
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 5. 动态删除给定 id 数组的员工
     *    动态标签：<foreach>（IN 条件）
     */
    @Test
    public void testDeleteByIds() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            System.out.println("===== 5. 动态删除 id 数组 [3, 5] 的员工 =====");
            System.out.println("--- 删除前 ---");
            printAll(mapper);

            // 删除 id 为 3 和 5 的员工
            int[] ids = {3, 5};
            int rows = mapper.deleteByIds(ids);

            System.out.println("受影响行数：" + rows);
            System.out.println("--- 删除后 ---");
            printAll(mapper);
        } finally {
            sqlSession.close();
        }
    }
}
