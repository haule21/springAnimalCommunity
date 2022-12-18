package haule.raelfarm.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
	public int idCheck(String userid);
}
