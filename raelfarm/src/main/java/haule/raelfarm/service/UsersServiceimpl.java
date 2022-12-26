package haule.raelfarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import haule.raelfarm.dto.UserInsertDTO;
import haule.raelfarm.mapper.UsersMapper;

@Service
public class UsersServiceimpl implements UsersService {
	
	@Autowired
	UsersMapper usersMapper;
	
	@Override
	public int idCheck(String userid) {
		return usersMapper.idCheck(userid) == 1 ? 1 : 0;
	}
	@Override
	public int nicknameCheck(String nickname) {
		return usersMapper.nicknameCheck(nickname) == 1 ? 1 : 0;
	}
	@Override
	public int phoneCheck(String phonenumber) {
		return usersMapper.phoneCheck(phonenumber) == 1 ? 1 : 0;
	}
	@Override
	public int createUser(UserInsertDTO userinsertdto) {
		return usersMapper.createUser(userinsertdto);
	}
}
