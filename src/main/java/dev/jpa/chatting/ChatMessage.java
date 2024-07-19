package dev.jpa.chatting;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
	
	public enum MessageType{
			ENTER,TALK
	}
	private MessageType type; 
	private String roomId; //채팅방 아이디
	private String name;
	private String sender; //발신자
	private String message; //메시지
	

}