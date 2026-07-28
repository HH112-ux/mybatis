package com.example;

import com.example.mapper.TeacherMapper;
import com.example.pojo.Teacher;
import com.example.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * 测试类 —— 验证注解方式实现的教师表 CRUD
 * 按顺序演示：查询 → 插入 → 修改 → 删除
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

    // ==================== 查 Retrieve ====================

    /**
     * 1. 查询全部教师
     */
    @Test
    public void testFindAll() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            System.out.println("===== 查 - 查询全部教师 =====");
            printAll(mapper);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 2. 根据 ID 查询单个教师
     */
    @Test
    public void testFindById() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            System.out.println("===== 查 - 根据 ID 查询 =====");
            Teacher teacher = mapper.findById(1);
            if (teacher != null) {
                System.out.println("查询结果：" + teacher);
            } else {
                System.out.println("未找到 ID=1 的教师");
            }
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 3. 根据姓名模糊查询
     */
    @Test
    public void testFindByName() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            System.out.println("===== 查 - 姓名模糊查询「张」 =====");
            List<Teacher> list = mapper.findByName("张");
            for (Teacher t : list) {
                System.out.println(t);
            }
        } finally {
            sqlSession.close();
        }
    }

    // ==================== 增 Create ====================

    /**
     * 4. 插入一条新教师记录
     */
    @Test
    public void testInsert() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            Teacher teacher = new Teacher(null, "钱十一", "天津市南开区", 40, "副教授", 16000);
            int rows = mapper.insert(teacher);

            System.out.println("===== 增 - 插入新教师 =====");
            System.out.println("受影响行数：" + rows);
            System.out.println("自增回填的 ID：" + teacher.getId());
            System.out.println("插入后数据：");
            printAll(mapper);
        } finally {
            sqlSession.close();
        }
    }

    // ==================== 改 Update ====================

    /**
     * 5. 根据 ID 更新教师全部字段
     */
    @Test
    public void testUpdate() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            // 先查询要修改的记录
            Teacher teacher = mapper.findById(1);
            if (teacher == null) {
                System.out.println("ID=1 的记录不存在，无法修改");
                return;
            }

            System.out.println("===== 改 - 更新前 =====");
            System.out.println(teacher);

            // 修改字段
            teacher.setAge(36);
            teacher.setSal(21000);
            teacher.setJob("教授");
            int rows = mapper.update(teacher);

            System.out.println("\n===== 改 - 更新后 =====");
            System.out.println("受影响行数：" + rows);
            System.out.println(mapper.findById(1));
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 6. 根据 ID 仅更新工资
     */
    @Test
    public void testUpdateSal() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            System.out.println("===== 改 - 更新工资 =====");
            int rows = mapper.updateSal(2, 17000);
            System.out.println("受影响行数：" + rows);
            System.out.println(mapper.findById(2));
        } finally {
            sqlSession.close();
        }
    }

    // ==================== 删 Delete ====================

    /**
     * 7. 根据 ID 删除教师
     */
    @Test
    public void testDeleteById() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

            System.out.println("===== 删 - 删除前全部数据 =====");
            printAll(mapper);

            int rows = mapper.deleteById(1);

            System.out.println("===== 删 - 删除 ID=1 的教师 =====");
            System.out.println("受影响行数：" + rows);
            System.out.println("\n===== 删 - 删除后全部数据 =====");
            printAll(mapper);
        } finally {
            sqlSession.close();
        }
    }
}
