package dev.jpa.chatting;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.jpa.service.ChatService;
@Controller
@RequestMapping("/chatting")
public class ChattingController {
	
	@Autowired
	private ChatProc chatProc; 
	
	@Autowired
	private ChatService chatService; 
	
	public ChattingController() {
		System.out.println("chattingController created");
	}

	@GetMapping("/read")
	public String read(Model model) {
		System.out.println("read애 들어옴");
		return "/chat";
	}
	
    @GetMapping("/chatList")
    public String chatList(Model model){
    	System.out.println("chatList에 들어옴");
        ArrayList<ChatRoom> roomList = chatProc.read_chat();
        model.addAttribute("roomList",roomList);
        return "chatting/chatList";
    }
    @GetMapping("/chatRoom")
    public String chatRoom(Model model, @RequestParam("roomId") String roomId){
        ChatRoom room = chatService.findRoomById(roomId);
        model.addAttribute("room",room);   //현재 방에 들어오기위해서 필요한데...... 접속자 수 등등은 실시간으로 보여줘야 돼서 여기서는 못함
        return "chatting/chat";
    }
    
    @PostMapping("/createRoom")
    public String createRoom(@RequestParam("name") String name) {
      System.out.println("create로 들어옴");
      JSONObject json = new JSONObject();
      chatService.createRoom(name);
      return "redirect:/chatting/chatList";
    }
    


}
