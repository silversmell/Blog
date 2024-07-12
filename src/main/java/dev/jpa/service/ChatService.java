package dev.jpa.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.jpa.chatting.ChatProc;
import dev.jpa.chatting.ChatRoom;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
	
	private final ObjectMapper objectMapper;
	private Map<String,ChatRoom> chatRooms;
	
	@Autowired
	private ChatProc chatProc; 
	
	
	@PostConstruct
	private void init() {
	    // chatRooms를 LinkedHashMap으로 초기화
	    chatRooms = new LinkedHashMap<>();

	    ArrayList<ChatRoom> list = this.chatProc.read_chat();
	    for(int i = 0; i < list.size(); i++) {
	        chatRooms.put(list.get(i).getRoomId(), list.get(i));
	    }
	}
	   public List<ChatRoom> findAllRoom() {
		   ArrayList<ChatRoom> list = this.chatProc.read_chat();
		   for(int i = 0;i<list.size();i++) {
			   chatRooms.put(list.get(i).getRoomId(),list.get(i));
			   System.out.println(chatRooms.get(list.get(i).getRoomId()));
		   }
		   return chatProc.read_chat();
	    }
	  
	   public ChatRoom findRoomById(String roomId) {
//		   ArrayList<ChatRoom> list = this.chatProc.read_chat();
//		   for(int i = 0;i<list.size();i++) {
//			   chatRooms.put(list.get(i).getRoomId(),(ChatRoom)list.get(i));
//		   }
		   return chatRooms.get(roomId);
	   }
	   
	   public ChatRoom createRoom(String name) { //채팅방 만듦.
		   JSONObject json = new JSONObject();
		   System.out.println("name :" +name);
		   String randomId = UUID.randomUUID().toString();
		   ChatRoom chatRoom = ChatRoom.builder()
				   .roomId(randomId)
				   .name(name)
				   .build();
		   chatRooms.put(randomId, chatRoom);
		   HashMap<String,Object> map = new HashMap();
		   map.put("roomId", randomId);
		   map.put("name",name);
		  int cnt = this.chatProc.create_chat(map);

		   return chatRoom;
	   }
	   public <T> void sendMessage(WebSocketSession session, T message) {
		   try {
			   session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
		   }catch(IOException e) {
			   log.error(e.getMessage(),e);
		   }
	   }

}
