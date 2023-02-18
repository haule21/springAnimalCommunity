package haule.raelfarm.controller.StrategyCategory;

import java.util.List;

import haule.raelfarm.dto.SearchDTO;
import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.pagination.PagingResponse;
import haule.raelfarm.service.BoardService;

public interface CategoryStrategy {
	List<String> ViewCategorysData(BoardService boardService);
	PagingResponse<ViewBoardsDTO> ViewBoard(int category_num, SearchDTO param, BoardService boardService);
	List<ViewBoardsDTO> ViewPreviousNextBoards(int category_num, int board_num, String iboardnum, SearchDTO param,BoardService boardService);
}
