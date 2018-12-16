package cn.hibrant.shallwetalk.web;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import cn.hibrant.shallwetalk.entity.EnterMsg;
import cn.hibrant.shallwetalk.entity.InMsg;
import cn.hibrant.shallwetalk.entity.OutMsg;
import cn.hibrant.shallwetalk.util.UserContainer;

@Controller
public class TalkController {
	
	@Autowired
	private UserContainer userContainer;
	
	@MessageMapping("/send")
	@SendTo("/topic/show")
	public OutMsg chat(InMsg chatMsg, Message<?> message) {
		OutMsg showMsg = new OutMsg();
		showMsg.setName(userContainer.getNickname(this.getSessionId(message)));
		showMsg.setContent(this.replaceBR(HtmlUtils.htmlEscape(chatMsg.getContent())));
		showMsg.setTime(this.currentTimeStr());
		return showMsg;
	}
	
	@MessageMapping("/enter")
	@SendTo("/topic/show")
	public OutMsg enter(EnterMsg enterMsg, Message<?> message) {
		String nickname = enterMsg.getName();
		userContainer.set(this.getSessionId(message), nickname);
		
		OutMsg showMsg = new OutMsg();
		showMsg.setName("System");
		showMsg.setContent(HtmlUtils.htmlEscape("Welcome " + nickname + "!"));
		showMsg.setTime(this.currentTimeStr());
		return showMsg;
	}
	
	private String getSessionId(Message<?> message) {
		return MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class).getSessionId();
	}
	
	private String currentTimeStr() {
		return DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
	}
	
	private String replaceBR(String str) {
		return str.replaceAll("\r?\n", "<br/>");
	}
	
}
