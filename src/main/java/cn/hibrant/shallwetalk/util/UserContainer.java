package cn.hibrant.shallwetalk.util;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class UserContainer {

	private ConcurrentHashMap<String, String> userMap = new ConcurrentHashMap<>();
	
	public void set(String sessionId, String nickname) {
		userMap.put(sessionId, nickname);
	}
	
	public String getNickname(String sessionId) {
		return userMap.get(sessionId);
	}
	
	public boolean contains(String sessionId) {
		return userMap.containsKey(sessionId);
	}
	
	public void remove(String sessionId) {
		userMap.remove(sessionId);
	}
}
