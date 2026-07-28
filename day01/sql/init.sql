-- 创建数据库
CREATE DATABASE IF NOT EXISTS mybatis_demo DEFAULT CHARACTER SET utf8mb4;
USE mybatis_demo;

-- 创建 teacher 表
DROP TABLE IF EXISTS teacher;
CREATE TABLE teacher (
    id   INT          AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    name VARCHAR(50)  NOT NULL COMMENT '姓名',
    addr VARCHAR(255)         COMMENT '地址',
    age  INT                  COMMENT '年龄',
    job  VARCHAR(255)         COMMENT '职务',
    sal  INT                  COMMENT '工资'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表';

-- 插入测试数据
INSERT INTO teacher (name, addr, age, job, sal) VALUES
('张三', '北京市海淀区', 35, '教授', 15000),
('李四', '上海市浦东新区', 42, '副教授', 12000),
('王五', '广州市天河区', 28, '讲师', 8000),
('赵六', '深圳市南山区', 50, '教授', 18000),
('孙七', '杭州市西湖区', 32, '讲师', 9000),
('周八', '成都市武侯区', 38, '副教授', 13000),
('吴九', '武汉市洪山区', 26, '助教', 6000),
('郑十', '南京市鼓楼区', 45, '教授', 20000);
