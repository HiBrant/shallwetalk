package cn.hibrant.shallwetalk.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import cn.hibrant.shallwetalk.entity.Greeting;
import cn.hibrant.shallwetalk.entity.Hello;

@Controller
public class GreetingController {

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(Hello hello) throws InterruptedException {
		Thread.sleep(1000);
		Greeting rst = new Greeting();
		rst.setContent("Hello, " + HtmlUtils.htmlEscape(hello.getName()) + "!");
		return rst;
	}
}
