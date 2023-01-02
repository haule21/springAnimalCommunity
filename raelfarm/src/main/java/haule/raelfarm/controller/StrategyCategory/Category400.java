package haule.raelfarm.controller.StrategyCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.service.BoardService;

public class Category400 implements CategoryStrategy{
	@Autowired
	BoardService boardService;
	
	int StartCategoryNum = 401;
	int EndCategoryNum = 499;

	public void WriteBoard(int category_num, BoardService boardService) {
		
	}
	
	public List<ViewBoardsDTO> ViewBoard(int category_num, BoardService boardService) {
		return boardService.SelectBoards(category_num, StartCategoryNum, EndCategoryNum); 
	}
}
