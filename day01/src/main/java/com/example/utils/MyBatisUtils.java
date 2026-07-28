package com.example.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyBatis 工具类：用于获取 SqlSession
 */
public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // 加载 MyBatis 核心配置文件
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            // 构建 SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("MyBatis 配置文件加载失败", e);
        }
    }

    /**
     * 获取 SqlSession（非自动提交）
     */
    public static SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }

    /**
     * 获取 SqlSession（自动提交）
     */
    public static SqlSession openSessionAutoCommit() {
        return sqlSessionFactory.openSession(true);
    }
}
