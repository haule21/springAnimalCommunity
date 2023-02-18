package haule.raelfarm.controller.StrategyCategory;

import java.util.List;

import haule.raelfarm.dto.SearchDTO;
import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.pagination.PagingResponse;
import haule.raelfarm.service.BoardService;

public class Category100 implements CategoryStrategy{
	
	int StartCategoryNum = 101;
	int EndCategoryNum = 199;

	public List<String> ViewCategorysData(BoardService boardService) {
		return boardService.ViewCategorysName(StartCategoryNum, EndCategoryNum);
	}
	
	public PagingResponse<ViewBoardsDTO> ViewBoard(int category_num, SearchDTO param, BoardService boardService) {
		return boardService.SelectBoards(category_num, StartCategoryNum, EndCategoryNum, param); 
	}
	
	public List<ViewBoardsDTO> ViewPreviousNextBoards(int category_num, int board_num, String iboardnum, SearchDTO param, BoardService boardService){
		return boardService.SelectPreviousNextBoards(category_num, board_num, iboardnum, category_num, category_num, param);
	}
}
