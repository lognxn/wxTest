package com.iee.util;

import com.iee.common.MessageConst;
import com.iee.entity.TextMessage;
import com.thoughtworks.xstream.XStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/9/12.
 */
public class MessageUtil {
	
    /**
     * xml转map
     * @param req
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String,String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException {
        HashMap<String, String> map = new HashMap<String, String>();
        SAXReader saxReader = new SAXReader();
        ServletInputStream inputStream = req.getInputStream();
        Document document = saxReader.read(inputStream);

        Element rootElement = document.getRootElement();

        List <Element> elements = rootElement.elements();

        for (Element el : elements){
            map.put(el.getName(),el.getText());
        }
        inputStream.close();
        return map;
    }

    /**
     * 文本消息对象转换为xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml (TextMessage textMessage){
        XStream xStream = new XStream();
        //将根节点设置成xml
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);
    }
    
    /**
     * 拼接文本消息, 用于回复
     * @param toUserName
     * @param fromUserName
     * @param content
     * @return
     */
    public static String initText(String toUserName, String fromUserName, String content){
    	TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setMsgType(MessageConst.message_text);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setContent("您发送的消息为:"+content);
        return textMessageToXml(textMessage);
    }
    
    /** 拼接成一个主菜单, 作为关注后的回复消息 */
    public static String menuText(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("欢迎您的关注, 请按照菜单提示进行操作:\n\n");
    	sb.append("1. 课程介绍\n");
    	sb.append("2. 慕课网介绍\n\n");
    	sb.append("回复问号调出主菜单. ");
    	return sb.toString();
    }
    
    public static String firstMenu(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("本套课程介绍公众号开发, 编辑模式, 和开发模式");
    	return sb.toString();
    }
    
    public static String secodMenu(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("慕课网是一个非常流弊的软件");
    	return sb.toString();
    }
    
    
}
