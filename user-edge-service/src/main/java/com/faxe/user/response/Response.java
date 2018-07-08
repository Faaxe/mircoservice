package com.faxe.user.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息返回类
 *
 * @author ZhouXiang
 * @create 2018-07-08 14:55
 **/
@Data
public class Response<T> implements Serializable{
    /**
     * 代码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 成功返回的数据
     */
    private T data;

    /**
     *   时间戳
     */
    private Long timestamp;

    public Response(){ }

    public Response(int code, String message){
        this.code = code;
        this.message = message;
    }

    public static <T> Response<T> success(T data){
        return success(200, null, data);
    }

    public static <T> Response<T> success(String message){
        return success(200, message, null);
    }

    public static <T> Response<T> success(String message, T data){
        return success(200, message, data);
    }

    public static <T> Response<T> success(int code, String message, T data){
        Response response = new Response();
        response.code = code;
        response.message = message;
        response.data = message;
        response.timestamp = System.currentTimeMillis();
        return response;
    }

    public static <T> Response<T> error(String message){
        return error(400, message);
    }

    public static <T> Response<T> error(int code, String message){
        Response response = new Response();
        response.code = code;
        response.message = message;
        response.timestamp = System.currentTimeMillis();
        return response;
    }
}
