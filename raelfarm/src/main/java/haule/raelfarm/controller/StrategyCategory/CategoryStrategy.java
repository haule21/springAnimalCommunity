package haule.raelfarm.controller.StrategyCategory;

import java.util.List;

import haule.raelfarm.dto.CategorySelectDTO;
import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.service.BoardService;

public interface CategoryStrategy {
	List<String> ViewCategorysData(BoardService boardService);
	List<ViewBoardsDTO> ViewBoard(int category_num, BoardService boardService);
	List<ViewBoardsDTO> ViewPreviousNextBoards(int category_num, int board_num, String iboardnum, BoardService boardService);
}
