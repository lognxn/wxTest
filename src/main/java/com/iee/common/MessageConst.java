package com.iee.common;
   /**
    * @ClassName: MessageConst
	* @Description:
    * @author 龙小南
    * @email longxiaonan@163.com
    * @date 2017年11月19日 下午7:46:04
    * @version 1.0
    */
public interface MessageConst {
	//消息类型
	String message_text = "text";
	String message_image = "image";
	String message_voice = "voice";
	String message_video = "video";
	String message_link = "link";
	String message_location = "location";
	//消息事件
	String message_event = "event";
	String message_event_subscribe = "subscribe";
	String message_event_unsubscribe = "unsubscribe";
	String message_event_click = "click";
	String message_event_view = "view";
}
