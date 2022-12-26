package haule.raelfarm.mapper;

import org.apache.ibatis.annotations.Mapper;

import haule.raelfarm.dto.UserInsertDTO;

@Mapper
public interface UsersMapper {
	public int idCheck(String userid);
	public int nicknameCheck(String nickname);
	public int phoneCheck(String phonenumber);
	public int createUser(UserInsertDTO userinsertdto);
}
