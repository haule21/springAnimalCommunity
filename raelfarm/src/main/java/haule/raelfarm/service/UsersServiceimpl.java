package haule.raelfarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import haule.raelfarm.dto.UserInsertDTO;
import haule.raelfarm.mapper.UsersMapper;

@Service
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션
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
	@Transactional // 쓰기 작업은 별도로 트랜잭션 적용
	public int createUser(UserInsertDTO userinsertdto) {
		return usersMapper.createUser(userinsertdto);
	}
}
