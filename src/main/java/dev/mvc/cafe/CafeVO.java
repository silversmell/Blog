package dev.mvc.cafe;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CafeVO {
  
  private int cafeno;
  
  /** 중분류명 */
  @NotEmpty(message=" 지역명은 필수 입력 항목입니다.")
  @Size(min=2, max=10, message="지역명의 입력 글자 수는 최소 2자에서 10자 이어야합니다.")
  private String name;

  /** 소분류명 */
  @NotEmpty(message="카페명은 필수 입력 항목입니다.")
  @Size(min=1, max=20, message="카페명의 입력 글자 수는 최소 1자에서 20자(한글 10자) 이어야합니다.")
  private String namesub;
  
  /** 관련 자료수 */
  @NotNull(message="관련자료수는 필수 입력 항목입니다.")
  @Min(value = 0)
  @Max(value = 1000000)
  private Integer cnt=0;
  
  /** 등록일 */
  private String rdate;
  
  /** 출력 순서 */
  @NotNull(message="출력 순서는 필수 입력 항목입니다.")
  @Min(value = 1)
  @Max(value = 1000000)
  private Integer seqno;

  /** 출력 모드 */
  @NotEmpty(message="출력 모드는 필수 입력 항목입니다.")
  @Pattern(regexp="^[YN]$", message="Y 또는 N만 입력 가능합니다.")
  private String visible = "N";

}
