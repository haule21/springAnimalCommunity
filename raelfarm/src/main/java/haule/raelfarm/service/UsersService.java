package haule.raelfarm.service;

import haule.raelfarm.dto.UserInsertDTO;

public interface UsersService {
	public int idCheck(String userid);
	public int nicknameCheck(String nickname);
	public int phoneCheck(String phonenumber);
	public int createUser(UserInsertDTO userinsertdto);
}
