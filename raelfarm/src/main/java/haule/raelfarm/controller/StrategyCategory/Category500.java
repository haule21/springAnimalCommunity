package haule.raelfarm.controller.StrategyCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import haule.raelfarm.dto.SearchDTO;
import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.pagination.PagingResponse;
import haule.raelfarm.service.BoardService;

public class Category500 implements CategoryStrategy{
	int StartCategoryNum = 501;
	int EndCategoryNum = 599;

	public List<String> ViewCategorysData(BoardService boardService) {
		return boardService.ViewCategorysName(StartCategoryNum, EndCategoryNum);
	}
	
	public PagingResponse<ViewBoardsDTO> ViewBoard(int category_num, SearchDTO param, BoardService boardService) {
		return boardService.SelectBoards(category_num, StartCategoryNum, EndCategoryNum, param); 
	}
	
	public PagingResponse<ViewBoardsDTO> ViewPreviousNextBoards(int category_num, int board_num, String iboardnum, SearchDTO param, int page,BoardService boardService){
		return boardService.SelectPreviousNextBoards(category_num, board_num, iboardnum, StartCategoryNum, EndCategoryNum, param, page);
	}
	public int ViewSelectBoardPage(int prectn,int category_num, int board_num, BoardService boardService) {
		return boardService.SelectBoardPage(prectn, category_num, board_num, StartCategoryNum, EndCategoryNum);
	}
}
