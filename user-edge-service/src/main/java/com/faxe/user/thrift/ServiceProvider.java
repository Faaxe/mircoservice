package com.faxe.user.thrift;

import com.faxe.thrift.message.MessageService;
import com.faxe.thrift.user.UserService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ZhouXiang
 * @create 2018-07-08 14:46
 **/
@Component
public class ServiceProvider {
    @Value("${user.service.ip}")
    private String userServiceIp;

    @Value("${user.service.port}")
    private int userServicePort;

    @Value("${message.service.ip}")
    private String messageServiceIp;

    @Value("${message.service.port}")
    private int messageServicePort;

    public  UserService.Client getUserServiceClient(){
        TSocket socket = new TSocket(userServiceIp, userServicePort);

        //与服务端一致的传输方式
        TTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //与服务端一致的传输协议
        TProtocol protocol = new TBinaryProtocol(transport);

        UserService.Client client = new UserService.Client(protocol);
        return client;
    }

    public  MessageService.Client getMessageServiceClient(){
        TSocket socket = new TSocket(messageServiceIp, messageServicePort);

        //与服务端一致的传输方式
        TTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //与服务端一致的传输协议
        TProtocol protocol = new TBinaryProtocol(transport);

        MessageService.Client client = new MessageService.Client(protocol);
        return client;
    }
}
