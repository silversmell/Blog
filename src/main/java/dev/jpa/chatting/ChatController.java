package dev.jpa.chatting;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.jpa.service.ChatService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;
	
   public ChatController() {
		System.out.println("chatController create");
	}
    

    @PostMapping("/create")
    public ChatRoom createRoom(@RequestBody CreateRoomRequest createroomRequest) {
    	JSONObject json = new JSONObject();
    	String name = createroomRequest.getName();
    	System.out.println("들어옴");
        return chatService.createRoom(name); //여기서 id가 있으면 확인
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
    
    @GetMapping("/find")
    public ChatRoom findById(@RequestParam("roomId") String roomId){
    	ChatRoom chatRoom = chatService.findRoomById(roomId);
    	return chatService.findRoomById(roomId);
    }
    
//	@GetMapping(value="/read") // http://localhost:9092
//    public String read() { // 파일명 return
//	  System.out.println("/에 들어옴");
//       return "chat.html"; // /templates/index.html  
//  }
}
