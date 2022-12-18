package haule.raelfarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import haule.raelfarm.mapper.UsersMapper;

@Service
public class UsersServiceimpl implements UsersService {
	
	@Autowired
	UsersMapper usersMapper;
	
	@Override
	public int idCheck(String userid) {
		return usersMapper.idCheck(userid) == 1 ? 1 : 0;
	}
}
