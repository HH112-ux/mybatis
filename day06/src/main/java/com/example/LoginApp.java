package com.example;

import com.example.mapper.UserMapper;
import com.example.pojo.Permision;
import com.example.pojo.Role;
import com.example.pojo.User;
import com.example.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * 登录主程序
 * 从键盘录入账号和密码，验证成功后显示角色和权限，并更新最后访问时间
 */
public class LoginApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========== 用户登录系统 ==========");
        System.out.print("请输入账号：");
        String uname = scanner.nextLine();
        System.out.print("请输入密码：");
        String pwd = scanner.nextLine();

        SqlSession sqlSession = MyBatisUtils.openSessionAutoCommit();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            // 1. 登录验证
            User user = mapper.findByUnameAndPwd(uname, pwd);
            if (user == null) {
                System.out.println("账号或密码错误，登录失败！");
                return;
            }

            System.out.println("\n登录成功！欢迎 " + user.getUname());

            // 2. 查询角色
            List<Role> roles = mapper.findRolesByUid(user.getUid());

            // 3. 查询权限
            List<Permision> permisions = mapper.findPermisionsByUid(user.getUid());

            // 4. 显示角色
            System.out.println("\n" + user.getUname() + " 是：" + formatRoles(roles));

            // 5. 显示可操作菜单
            System.out.println("\n可操作菜单：");
            for (Permision p : permisions) {
                System.out.println("<a href='" + p.getUrl() + "'>" + p.getPname() + "</a>");
            }

            // 6. 更新最后访问时间
            Date now = new Date();
            mapper.updateLasttime(user.getUid(), now);
            System.out.println("\n最后访问时间已更新：" +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));

        } finally {
            sqlSession.close();
        }
    }

    /**
     * 格式化角色名称列表，如 "操作员" 或 "超级管理员、操作员"
     */
    private static String formatRoles(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return "无角色";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < roles.size(); i++) {
            if (i > 0) {
                sb.append("、");
            }
            sb.append(roles.get(i).getRname());
        }
        return sb.toString();
    }
}
