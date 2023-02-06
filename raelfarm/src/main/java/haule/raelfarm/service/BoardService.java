package haule.raelfarm.service;

import java.util.List;

import org.springframework.stereotype.Component;

import haule.raelfarm.dto.BoardMediaFileInsertDTO;
import haule.raelfarm.dto.BoardNumSelectDTO;
import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.dto.ViewBoardDTO;
import haule.raelfarm.dto.ViewBoardsDTO;

@Component
public interface BoardService {
	public List<MainSelectDTO> SelectMainDatas();
	public void ViewCount(String iboardnum);
	public List<ViewBoardsDTO> SelectBoards(int category_num, int Starting, int Ending);
	public List<ViewBoardsDTO> SelectPreviousNextBoards(int category_num, int board_num,String iboardnum,int Starting, int Ending);
	public String ViewCategoryName(int category_num);
	public List<String> ViewCategorysName(int category_num_st, int category_num_ed);
	public List<BoardNumSelectDTO> SelectBoardNumMAX();
	public ViewBoardDTO SelectBoard(int categorynum, int boardnum);
	public int InsertBoardData(String iboardnum);
	public int InsertBoardMedia(BoardMediaFileInsertDTO media);
	public int InsertBoard(int categorynum, int boardnum, String title, String writer, String existimgfile, String content);
	public void InvokeBoard(List<BoardMediaFileInsertDTO> media, String iboardnum, int categorynum, int boardnum, String title, String writer, String existimgfile, String content);
}
