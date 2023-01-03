package haule.raelfarm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.mapper.BoardMapper;
import haule.raelfarm.repository.BoardDataRepository;
import haule.raelfarm.repository.CategoryRepository;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardMapper boardMapper;
	
	@Autowired 
	BoardDataRepository boardDataRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public List<MainSelectDTO> SelectMainDatas() {
		List<MainSelectDTO> datas = boardMapper.SelectMainDatas();
		return datas; 
	}
	
	@Override
	public void ViewCount(String iboardnum) {
		boardDataRepository.updateViewCount(iboardnum);
	}
	
	@Override
	public List<ViewBoardsDTO> SelectBoards(int category_num, int Starting, int Ending){
		return boardMapper.SelectBoards(category_num, Starting, Ending);
	}
	
	@Override
	public List<ViewBoardsDTO> SelectPreviousNextBoards(int category_num, int board_num, String iboardnum, int Starting, int Ending){
		return boardMapper.SelectPreviousNextBoards(category_num, board_num, iboardnum, Starting, Ending);
	}
	
	@Override
	public String ViewCategoryName(int category_num) {
		return categoryRepository.ViewCategoryName(category_num);
	}
	
}
