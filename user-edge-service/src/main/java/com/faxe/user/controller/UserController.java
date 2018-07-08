package com.faxe.user.controller;

import com.faxe.thrift.user.UserInfo;
import com.faxe.user.dao.RedisDao;
import com.faxe.user.response.Response;
import com.faxe.user.thrift.ServiceProvider;
import com.faxe.user.vo.UserInfoVO;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 用户Controller
 *
 * @author ZhouXiang
 * @create 2018-07-08 14:47
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisDao redisDao;

    @PostMapping("/login")
    public Response<String> login(@RequestParam("username")String username, @RequestParam("password")String password,
                          @RequestParam(required = false)String token){
        //0. 若携带token，先验证token
        if(null != token){
            UserInfoVO user = (UserInfoVO)redisDao.get(token);
            if(username.equals(user.getUsername()) && md5(password).equals(user.getPassword())){
                return Response.success("login success");
            }
        }
        //1. 验证用户名密码
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserServiceClient().getUserByUserName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.error("login failed");
        }
        if(!password.equals(userInfo.password)){
            return Response.error("wrong password");
        }
        //2. 生成token（32随机字符串）
        String tokenStr = getToken();
        //3. redis缓存token
        UserInfoVO user = new UserInfoVO();
        BeanUtils.copyProperties(userInfo, user);
        redisDao.set(tokenStr, user);
        return Response.success("login success and return token", tokenStr);
    }

    /**
     * 发生验证码
     *
     * @param email
     * @param phoneNumber
     * @return
     */
    @PostMapping("/code")
    public Response<String> getVerificationCode(@RequestParam(value = "email", required = false) String email,
                                                @RequestParam(value = "phoneNumber", required = false)String phoneNumber){
        if(null != email){
            String code = randomCode("0123456789", 6);
            try {
                boolean result = serviceProvider.getMessageServiceClient().sendEmailMessage(email, code);
                if(result){
                    // 缓存code
                    redisDao.set(email, code);
                }
            } catch (TException e) {
                e.printStackTrace();
                return Response.error("send email failed");
            }
            Response.success("send email code success ");
        }else if(null != phoneNumber ){
            String code = randomCode("0123456789", 6);
            try {
                boolean result = serviceProvider.getMessageServiceClient().sendMobileMessage(phoneNumber, code);
                if(result){
                    // 缓存code
                    redisDao.set(phoneNumber, code);
                }
            } catch (TException e) {
                e.printStackTrace();
                return Response.error("send mobile failed");
            }
            Response.success("send mobile code success ");
        }
        return Response.error("at least email or phoneNumber given");
    }

    @PostMapping("/register")
    public Response register(@RequestBody UserInfoVO vo, @RequestParam("code")String code){
        // 验证码确认
        String key = vo.getEmail() != null? vo.getEmail():vo.getPhoneNumber();
        if(null == key){
            Response.error("at least email or phoneNumber given");
        }
        String getCode = (String)redisDao.get(key);
        if(!code.equals(code)){
            Response.error("wrong code");
        }

        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(vo, user);
        // 注册用户
        try {
            serviceProvider.getUserServiceClient().registerUser(user);
        } catch (TException e) {
            e.printStackTrace();
            return Response.success("register failed");
        }
        return Response.success("register success");
    }

    /**
     * 生成token
     * @return 字符串
     */
    private String getToken() {
        return randomCode("0123456789abcdefghijklmnopqrstuvwxyz", 32);
    }

    private String randomCode(String s, int size) {
        StringBuffer result = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < size; i++){
            int index = random.nextInt(s.length());
            result.append(s.charAt(index));
        }
        return result.toString();
    }

    /**
     * 取密码的md5值，密文存储数据表中
     * @param message
     * @return
     */
    private String md5(String message){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] md5Byte = digest.digest(message.getBytes());
        return HexUtils.toHexString(md5Byte);
    }

}
