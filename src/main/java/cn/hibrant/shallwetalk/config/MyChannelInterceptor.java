package cn.hibrant.shallwetalk.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class MyChannelInterceptor implements ChannelInterceptor, InitializingBean {
	
	private ConcurrentHashMap<String, Object> map;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (StompCommand.CONNECT == accessor.getCommand()) {
			map.put(accessor.getSessionId(), channel);
			System.out.println("connected, now: " + map.size());
		} else if (StompCommand.DISCONNECT == accessor.getCommand()) {
			map.remove(accessor.getSessionId());
			System.out.println("disconnected, now: " + map.size());
		}
		return ChannelInterceptor.super.preSend(message, channel);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		map = new ConcurrentHashMap<>();
	}

}
