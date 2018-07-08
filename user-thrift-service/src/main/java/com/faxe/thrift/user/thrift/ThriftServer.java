package com.faxe.thrift.user.thrift;

import com.faxe.thrift.user.UserService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * thrift服务端配置
 *
 * @author ZhouXiang
 * @create 2018-07-07 21:39
 **/
public class ThriftServer {

    @Value("${service.port}")
    private int port;

    @Autowired
    private UserService.Iface  userService;

    @PostConstruct
    public void startThriftServer(){
        TProcessor processor = new UserService.Processor<>(userService);

        //非阻塞
        TNonblockingServerSocket serverSocket = null;
        try {
            serverSocket = new TNonblockingServerSocket(port);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        TNonblockingServer.Args args = new TNonblockingServer.Args(serverSocket)
                .processor(processor)
                .transportFactory(new TFramedTransport.Factory())
                .protocolFactory(new TBinaryProtocol.Factory());

        TServer server = new TNonblockingServer(args);
        //thrift server启动
        System.out.println("======thrift server start=======");
        server.serve();
        System.out.println("======thrift server end=======");
    }
}
