package com.iee.servlet;

import org.dom4j.DocumentException;

import com.iee.common.MessageConst;
import com.iee.entity.TextMessage;
import com.iee.util.CheckUtil;
import com.iee.util.MessageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@WebServlet(urlPatterns = "/wx")
public class WxServlet extends HttpServlet {
	/**
	 * 第一次连接平台认证 微信公众平台接入指南部分, 公众号认证的时候传人的参数
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String signature = req.getParameter("signature");
		// 时间戳
		String timestamp = req.getParameter("timestamp");
		// 随机数
		String nonce = req.getParameter("nonce");
		// 随机字符串
		String echostr = req.getParameter("echostr");
		PrintWriter writer = resp.getWriter();

		// 如果校验成功, 需要将随机字符串返回
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			writer.print(echostr);
		}
	}

	/**
	 * 对话开发为post请求,当普通微信用户向公众账号发消息时，微信服务器将POST消息的XML数据包到开发者填写的URL上。
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter writer = resp.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String s = null;
			if ("text".equals(msgType)) {// 收到文本消息之后回复
				// 输入关键字后进行回复
				if ("1".equals(content)) {
					s = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}
				if ("2".equals(content)) {
					s = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secodMenu());
				}
				if ("?".equals(content) || "?".equals(content)) {
					s = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}

				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setMsgType("text");
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setContent("您发送的消息为:" + content);
				s = MessageUtil.textMessageToXml(textMessage);
				System.out.println(s);
			} else if ("event".equals(msgType)) {// 事件类型
				String eventType = map.get("Event");
				if (MessageConst.message_event_subscribe.equals(eventType)) {
					// 订阅事件, 回复主菜单
					s = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}
			System.out.println(s);
			writer.print(s);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}

	}
}
