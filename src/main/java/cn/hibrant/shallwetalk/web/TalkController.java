package cn.hibrant.shallwetalk.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import cn.hibrant.shallwetalk.entity.InMsg;
import cn.hibrant.shallwetalk.entity.OutMsg;

@Controller
public class TalkController {

	@MessageMapping("/send")
	@SendTo("/topic/show")
	public OutMsg chat(InMsg chatMsg) {
		OutMsg showMsg = new OutMsg();
		showMsg.setName("TODO");
		showMsg.setContent(HtmlUtils.htmlEscape(chatMsg.getContent()));
		return showMsg;
	}
}
