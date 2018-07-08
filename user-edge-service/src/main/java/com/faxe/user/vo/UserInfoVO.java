package com.faxe.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息vo
 *
 * @author ZhouXiang
 * @create 2018-07-08 16:32
 **/
@Data
public class UserInfoVO implements Serializable{
    private String username;

    private String password;

    private String readName;

    private String email;

    private String phoneNumber;
}
