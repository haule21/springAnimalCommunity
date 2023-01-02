package haule.raelfarm.controller.StrategyCategory;

import java.util.List;

import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.service.BoardService;

public interface CategoryStrategy {
	void WriteBoard(int category_num, BoardService boardService);
	List<ViewBoardsDTO> ViewBoard(int category_num, BoardService boardService);
}
