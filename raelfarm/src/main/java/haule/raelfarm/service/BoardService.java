package haule.raelfarm.service;

import java.util.List;

import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.dto.ViewBoardsDTO;

public interface BoardService {
	public List<MainSelectDTO> SelectMainDatas();
	public void ViewCount(String iboardnum);
	public List<ViewBoardsDTO> SelectBoards(int category_num, int Starting, int Ending);
	public String ViewCategoryName(int category_num);
}
