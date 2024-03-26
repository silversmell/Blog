package dev.mvc.tool;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Tool {
	
	public static synchronized String checkNull(String str) {
		if(str==null || str.equals("null")) {
			return "";
		}
		else {
			System.out.println(str+" null 아님");
			return str;
		}
	}
	public static synchronized String encode(String str) {
		return URLEncoder.encode(str,StandardCharsets.UTF_8);
	}

}
