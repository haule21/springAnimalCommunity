package haule.raelfarm.controller.StrategyCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.service.BoardService;

public class Category300 implements CategoryStrategy{
	@Autowired
	BoardService boardService;
	
	int StartCategoryNum = 301;
	int EndCategoryNum = 399;

	public void WriteBoard(int category_num, BoardService boardService) {
		
	}
	
	public List<ViewBoardsDTO> ViewBoard(int category_num, BoardService boardService) {
		return boardService.SelectBoards(category_num, StartCategoryNum, EndCategoryNum); 
	}
	public List<ViewBoardsDTO> ViewPreviousNextBoards(int category_num, int board_num, String iboardnum, BoardService boardService){
		return boardService.SelectPreviousNextBoards(category_num, board_num, iboardnum, category_num, category_num);
	}
}
