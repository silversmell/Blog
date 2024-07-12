package dev.jpa.chatting;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import dev.jpa.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
	
	private String roomId;
	private String name;
	private Set<WebSocketSession> sessions = new HashSet();
	
	@Builder
	public ChatRoom(String roomId, String name) {
		this.roomId=roomId;
		this.name=name;
	}
	
	public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
		if(chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
			System.out.println("enter로 들어옴");
			sessions.add(session); // 웹소켓 연결
			chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
		}
		sessions.add(session);
		sendMessage(chatMessage,chatService);
	}
	
    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream() //여러 스레드에서 병렬로 처리 됨, 성능을 높임
                .forEach(session -> chatService.sendMessage(session, message));
    }

}
