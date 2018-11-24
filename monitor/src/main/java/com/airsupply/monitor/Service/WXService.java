package com.airsupply.monitor.Service;

import com.airsupply.monitor.model.Constant;
import com.airsupply.monitor.model.ReplyTextMsg;
import com.airsupply.monitor.util.XMLUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;

@Service
public class WXService {

    public static void replyMessage(String message, PrintWriter out) {
        Document document = XMLUtil.readString2XML(message);
        Element root = document.getRootElement();
        String MsgType = XMLUtil.readNode(root, "MsgType");
        if (MsgType.equals(Constant.MSGTYPE_TEXT)) {
            ReplyTextMsg textMsg = new ReplyTextMsg();
            textMsg.setFromUserName("cdg");
            textMsg.setToUserName(XMLUtil.readNode(root, "FromUserName"));
            textMsg.setCreateTime();
            //将XML消息的参数都转化内容回复给微信
            XMLUtil.content = "";
            String nodeString = XMLUtil.readNodes(root);
            textMsg.setContent(nodeString);
            textMsg.setMsgType(Constant.MSGTYPE_TEXT);
            try {
                //将对象转化为XML
                String replyMsg = textMsg.Msg2Xml();
                out.println(replyMsg);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
