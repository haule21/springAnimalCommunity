package haule.raelfarm.singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import haule.raelfarm.dto.BoardNumSelectDTO;
import haule.raelfarm.service.BoardService;

public class BoardInfo {
	
	private static BoardInfo instance;
	private Map<Integer, Integer> board_numbers = new HashMap<Integer, Integer>();
	
	private BoardInfo(BoardService boardService) {

		List<BoardNumSelectDTO> datas = boardService.SelectBoardNumMAX();
		
		for(BoardNumSelectDTO temp : datas) {
			board_numbers.put(temp.getCategorynum(), temp.getBoardnum());
		}
	}
	
	public static BoardInfo getInstance(BoardService boardService) {
		
		if (instance == null) {
			instance = new BoardInfo(boardService);
		}
		return instance;
	}
	
	public int getBoardNumber(int categorynum) {

		board_numbers.put(categorynum, board_numbers.get(categorynum) + 1);
		return board_numbers.get(categorynum);
	}
}
