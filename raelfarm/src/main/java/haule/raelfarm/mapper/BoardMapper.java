package haule.raelfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import haule.raelfarm.dto.MainSelectDTO;

@Mapper
public interface BoardMapper {
	List<MainSelectDTO> SelectMainDatas();
}
