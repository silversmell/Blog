package dev.mvc.cafe;

import java.util.ArrayList;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CafeVOMenu {
	
  @NotEmpty(message="중분류명은 필수 입력 항목입니다.")
  @Size(min=2, max=10, message="중분류명의 입력 글자 수는 최소 2자에서 10자 이어야합니다.")	
 public String name;
	
/*
 * 중분류를 이용한소분류 목록
 */
 public ArrayList<CafeVO> list_namesub;
    
	

}
