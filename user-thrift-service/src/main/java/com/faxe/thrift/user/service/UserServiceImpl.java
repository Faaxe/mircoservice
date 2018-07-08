package com.faxe.thrift.user.service;

import com.faxe.thrift.user.UserInfo;
import com.faxe.thrift.user.UserService;
import com.faxe.thrift.user.mapper.UserMapper;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务器端：用户服务实现类
 *
 * @author ZhouXiang
 * @create 2018-07-07 20:15
 **/
@Service
public class UserServiceImpl implements UserService.Iface {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserById(int id) throws TException {
        return userMapper.getUserById(id);
    }

    @Override
    public UserInfo getUserByUserName(String username) throws TException {
        return userMapper.getUserByUserName(username);
    }

    @Override
    public void registerUser(UserInfo userInfo) throws TException {
        userMapper.save(userInfo);
    }
}
