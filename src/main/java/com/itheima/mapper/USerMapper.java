package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author LIXUBO
 * @Date 2022-07-25 22:08
 * @description
 * @Version 1.0
 */
public interface USerMapper {
    /**
     * 根据用户名和密码查询用户对象
     * 参数大于1个 ，加@Param注解，
     *
     * @param username
     * @param password
     * @return
     */
    @Select("select  * from tb_user where username = #{username} and password = #{password}")
    User select(@Param("username") String username,
                @Param("password") String password);

    /**
     * 根据用户名查询用户对象
     *一个参数 ，不加@Param注解
     * @param username
     * @return
     */
    @Select("select * from tb_user where username = #{username}")
    User selectByUsername(String username);

    /**
     * 添加用户
     * @param user
     */
    @Insert("insert into tb_user values (null,#{username},#{password})")
    void add(User user);
}
