package cn.hibrant.shallwetalk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import cn.hibrant.shallwetalk.util.UserContainer;

@Component
public class MyChannelInterceptor implements ChannelInterceptor {
	
	@Autowired
	private UserContainer userContainer;
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (StompCommand.DISCONNECT == accessor.getCommand()) {
			userContainer.remove(accessor.getSessionId());
		}
		return ChannelInterceptor.super.preSend(message, channel);
	}

}
