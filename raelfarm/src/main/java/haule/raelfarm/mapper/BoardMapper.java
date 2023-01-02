package haule.raelfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.dto.ViewBoardsDTO;

@Mapper
public interface BoardMapper {
	List<MainSelectDTO> SelectMainDatas();
	List<ViewBoardsDTO> SelectBoards(@Param("category_num") int category_num, @Param("category_num_st") int Starting, @Param("category_num_ed") int Ending);
}
