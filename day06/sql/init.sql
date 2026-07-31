-- 第六天：用户-角色-权限 多表关联

USE mybatis_demo;

-- ==================== 建表 ====================

-- 用户表
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    uid      INT          AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
    uname    VARCHAR(50)  NOT NULL COMMENT '账号',
    pwd      VARCHAR(50)  NOT NULL COMMENT '密码',
    lasttime DATETIME              COMMENT '最后访问时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
DROP TABLE IF EXISTS role;
CREATE TABLE role (
    rid   INT          AUTO_INCREMENT PRIMARY KEY COMMENT '角色id',
    rname VARCHAR(50)  NOT NULL COMMENT '角色名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 用户_角色中间表
DROP TABLE IF EXISTS users_role;
CREATE TABLE users_role (
    uid INT NOT NULL COMMENT '用户id',
    rid INT NOT NULL COMMENT '角色id',
    PRIMARY KEY (uid, rid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色中间表';

-- 访问权限表
DROP TABLE IF EXISTS permision;
CREATE TABLE permision (
    pid   INT          AUTO_INCREMENT PRIMARY KEY COMMENT '权限id',
    pname VARCHAR(50)  NOT NULL COMMENT '权限名称',
    url   VARCHAR(100) NOT NULL COMMENT '访问地址'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访问权限表';

-- 角色_权限中间表
DROP TABLE IF EXISTS role_permision;
CREATE TABLE role_permision (
    pid INT NOT NULL COMMENT '权限id',
    rid INT NOT NULL COMMENT '角色id',
    PRIMARY KEY (pid, rid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限中间表';

-- ==================== 插入数据 ====================

-- 用户表
INSERT INTO users (uname, pwd) VALUES
('张三', '111111'),
('李四', '222222'),
('王五', '333333');

-- 角色表
INSERT INTO role (rname) VALUES
('超级管理员'),
('操作员'),
('财会');

-- 用户_角色表
INSERT INTO users_role (uid, rid) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3);

-- 访问权限表
INSERT INTO permision (pname, url) VALUES
('用户管理', 'UserAction'),
('业务管理', 'ServiceAction'),
('年报管理', 'YearCountAction'),
('月报管理', 'MonthCountAction'),
('数据初始', 'InitAction'),
('租车业务', 'LeaseAction');

-- 角色_权限表
INSERT INTO role_permision (rid, pid) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(2, 2),
(2, 6),
(3, 3),
(3, 4);
