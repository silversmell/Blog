package dev.jpa.chatting;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.jpa.chatting.ChatDAOInter;

@Service("dev.jpa.chatting.ChatProc")
public class ChatProc implements ChatProcInter {
	
	@Autowired
	private ChatDAOInter chatDAO;

	@Override
	public int create_chat(HashMap<String, Object> map) {
		int cnt = this.chatDAO.create_chat(map);
		return cnt;
	}

	@Override
	public ArrayList<ChatRoom> read_chat() {
		ArrayList<ChatRoom> list = this.chatDAO.read_chat();
		return list;
	}

	@Override
	public ChatRoom find(String roomId) {
		ChatRoom chatroom = this.chatDAO.find(roomId);
		return chatroom;
	}

}
