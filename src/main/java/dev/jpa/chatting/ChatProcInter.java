package dev.jpa.chatting;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import dev.jpa.chatting.ChatDAOInter;

public interface ChatProcInter {
	public int create_chat(HashMap<String,Object> map);
	
	public ArrayList<ChatRoom> read_chat();
	
	public ChatRoom find(String roomId);

}
