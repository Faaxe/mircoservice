package com.faxe.thrift.user.mapper;

import com.faxe.thrift.user.UserInfo;
import org.apache.ibatis.annotations.*;

/**
 * 用户mapper
 *
 * @author ZhouXiang
 * @create 2018-07-07 21:13
 **/
@Mapper
public interface UserMapper {

    /**
     * 根据id查询用户信息
     * @param id
     * @return UserInfo
     */
    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "realName", column = "real_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phone_number")
    })
    @Select("select * from pe_user where id=#{id}")
    UserInfo getUserById(@Param("id")int id);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return UserInfo
     */
    @Select("select id, username, password, real_name as realName, email, phone_number as phoneNumber from pe_user where username=#{username}")
    UserInfo getUserByUserName(@Param("username")String username);

    /**
     * 用户新增
     * @param userInfo
     */
    @Insert("insert into pe_user(id, username, password, real_name, email, phone_number) " +
            "values(#{userInfo.id}, #{userInfo.username}, #{userInfo.password}, #{userInfo.realName}, #{userInfo.email}, #{userInfo.phoneNumber})")
    void save(@Param("userInfo")UserInfo userInfo);
}
