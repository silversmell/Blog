package dev.jpa.chatting;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

public interface ChatDAOInter {
	public int create_chat(HashMap<String,Object> map);
	
	public ArrayList<ChatRoom> read_chat();
	
	public ChatRoom find(String roomId);

}