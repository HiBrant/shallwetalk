package cn.hibrant.shallwetalk.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import cn.hibrant.shallwetalk.entity.ChatMsg;
import cn.hibrant.shallwetalk.entity.ShowMsg;

@Controller
public class GreetingController {

	@MessageMapping("/send")
	@SendTo("/topic/show")
	public ShowMsg chat(ChatMsg chatMsg) {
		ShowMsg showMsg = new ShowMsg();
		showMsg.setName("TODO");
		showMsg.setContent(HtmlUtils.htmlEscape(chatMsg.getContent()));
		return showMsg;
	}
}
