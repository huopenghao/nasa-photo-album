package com.nasa.album.mapper;

import com.nasa.album.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户 Mapper 接口
 * 使用注解方式编写 SQL，无需 XML
 */
public interface UserMapper {

    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM user WHERE username = #{username} LIMIT 1")
    User findByUsername(@Param("username") String username);

    /**
     * 根据用户名和密码查询用户（登录）
     */
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password} LIMIT 1")
    User findByUsernameAndPassword(@Param("username") String username,
                                    @Param("password") String password);

    /**
     * 新增用户（注册）
     */
    @Insert("INSERT INTO user (username, password, create_time, update_time) " +
            "VALUES (#{username}, #{password}, NOW(), NOW())")
    int insert(User user);
}
