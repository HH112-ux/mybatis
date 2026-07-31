package com.example.mapper;

import com.example.pojo.Permision;
import com.example.pojo.Role;
import com.example.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户登录及权限查询 Mapper
 * 涉及多表关联：users / role / users_role / permision / role_permision
 */
public interface UserMapper {

    /**
     * 根据账号和密码查询用户（登录验证）
     */
    User findByUnameAndPwd(@Param("uname") String uname, @Param("pwd") String pwd);

    /**
     * 根据用户 ID 查询其所有角色（多表关联：users_role + role）
     */
    List<Role> findRolesByUid(@Param("uid") int uid);

    /**
     * 根据用户 ID 查询其所有可操作权限（多表关联：users_role + role_permision + permision）
     * 使用 DISTINCT 去重（一个用户可能有多个角色，角色之间可能有重叠权限）
     */
    List<Permision> findPermisionsByUid(@Param("uid") int uid);

    /**
     * 更新用户最后访问时间
     */
    int updateLasttime(@Param("uid") int uid, @Param("lasttime") java.util.Date lasttime);
}
