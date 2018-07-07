# coding: utf-8
from message.api import MessageService

from thrift.transport import TSocket, TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender = "15951653519@163.com"
# 客户端授权码
authcode = "faxe1128"

class MessageServiceHandler:
    # 发送短信
    # 需要调用第三方接口
    def sendMobileMessage(self, mobile, message):
        print "sendMobileMessage, mobile:" + mobile + ", message:" + message
        return True

    # 发送邮件
    # 需开启邮箱服务器
    # 发邮件的代码网上很多，不多说
    def sendEmailMessage(self, email, message):
        print "sendEmailMessage, email:" + email + ", message:" + message
        messageObj = MIMEText(message, "plain", "utf-8")
        messageObj['From'] = sender
        messageObj['To'] = email
        messageObj['Subject'] = Header('Faxe邮件', "utf-8")
        try:
            smtpObj = smtplib.SMTP('smtp.163.com')
            smtpObj.login(sender, authcode)
            smtpObj.sendmail(sender, email, messageObj.as_string())
            print "send email success"
            return True
        except smtplib.SMTPException, ex:
            print "send email fail caused by " + ex
            return False

# thrift属于C/S
# 消息服务的提供者 Server
if __name__ == '__main__':
    processor = MessageService.Processor(MessageServiceHandler())
    # 传输方式 TServerSocket：非阻塞型 socket，用于服务器端
    transport = TSocket.TServerSocket("localhost", "9090")
    # 传输协议
    tfactory = TTransport.TFramedTransportFactory()
    # thrift的默认协议，使用二进制编码格式进行数据传输，基本上直接发送原始数据
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
    print "python thrift server start"
    server.serve()
    print "python thrift server exit"