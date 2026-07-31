package com.example;

import com.example.mapper.UserMapper;
import com.example.pojo.Permision;
import com.example.pojo.Role;
import com.example.pojo.User;
import com.example.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * 测试类 —— 验证多表关联查询
 */
public class UserMapperTest {

    /**
     * 测试登录验证
     */
    @Test
    public void testLogin() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            // 测试李四登录
            User user = mapper.findByUnameAndPwd("李四", "222222");
            System.out.println("===== 登录验证 =====");
            if (user != null) {
                System.out.println("登录成功：" + user);
            } else {
                System.out.println("登录失败");
            }
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 测试查询用户角色（多表关联）
     */
    @Test
    public void testFindRolesByUid() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            // 查询张三(uid=1)的角色
            List<Role> roles = mapper.findRolesByUid(1);
            System.out.println("===== 张三的角色 =====");
            for (Role r : roles) {
                System.out.println(r);
            }
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 测试查询用户权限（三表关联 + DISTINCT 去重）
     */
    @Test
    public void testFindPermisionsByUid() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            // 查询张三(uid=1)的权限（张三有超级管理员+操作员两个角色）
            List<Permision> permisions = mapper.findPermisionsByUid(1);
            System.out.println("===== 张三的权限 =====");
            for (Permision p : permisions) {
                System.out.println("<a href='" + p.getUrl() + "'>" + p.getPname() + "</a>");
            }
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 测试更新最后访问时间
     */
    @Test
    public void testUpdateLasttime() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            int rows = mapper.updateLasttime(2, new Date());
            System.out.println("===== 更新最后访问时间 =====");
            System.out.println("受影响行数：" + rows);

            // 验证更新结果
            User user = mapper.findByUnameAndPwd("李四", "222222");
            System.out.println("更新后：" + user);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 完整流程测试：模拟李四登录
     */
    @Test
    public void testFullLoginFlow() {
        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            // 1. 登录
            User user = mapper.findByUnameAndPwd("李四", "222222");
            if (user == null) {
                System.out.println("登录失败！");
                return;
            }

            // 2. 查询角色
            List<Role> roles = mapper.findRolesByUid(user.getUid());

            // 3. 查询权限
            List<Permision> permisions = mapper.findPermisionsByUid(user.getUid());

            // 4. 输出结果
            System.out.println("===== 模拟李四登录 =====");
            System.out.println("登录成功！欢迎 " + user.getUname());
            System.out.println();

            StringBuilder roleNames = new StringBuilder();
            for (int i = 0; i < roles.size(); i++) {
                if (i > 0) roleNames.append("、");
                roleNames.append(roles.get(i).getRname());
            }
            System.out.println(user.getUname() + " 是：" + roleNames);

            System.out.println("\n可操作菜单：");
            for (Permision p : permisions) {
                System.out.println("<a href='" + p.getUrl() + "'>" + p.getPname() + "</a>");
            }

            // 5. 更新访问时间
            mapper.updateLasttime(user.getUid(), new Date());
            System.out.println("\n最后访问时间已更新");
        } finally {
            sqlSession.close();
        }
    }
}
