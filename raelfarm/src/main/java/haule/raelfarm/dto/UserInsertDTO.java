package haule.raelfarm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserInsertDTO {
	private String userid;
	private String password;
	private String nickname;
	private String name;
	private String email;
	private String phone_number;
	private String address;
	
	@Builder
	public UserInsertDTO(String userid, String password, String nickname,
			String name, String email, String phone_number, String address){
		this.userid = userid;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.email = email;
		this.phone_number = phone_number;
		this.address = address;
	}
}
