package haule.raelfarm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import haule.raelfarm.dto.BoardMediaFileInsertDTO;
import haule.raelfarm.dto.BoardNumSelectDTO;
import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.dto.ViewBoardDTO;
import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.mapper.BoardMapper;
import haule.raelfarm.repository.BoardDataRepository;
import haule.raelfarm.repository.CategoryRepository;
import jakarta.transaction.Transactional;

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
	
	@Override
	public List<String> ViewCategorysName(int ctn_st, int ctn_ed){
		return categoryRepository.ViewCategorysName(ctn_st, ctn_ed);
	}
	
	@Override
	public List<BoardNumSelectDTO> SelectBoardNumMAX(){
		return boardMapper.SelectBoardNum();
	}
	@Override
	public ViewBoardDTO SelectBoard(int categorynum, int boardnum) {
		return boardMapper.SelectBoard(categorynum, boardnum);
	}
	@Override
	public int InsertBoardData(String iboardnum) {
		return boardMapper.InsertBoardData(iboardnum);
	}
	@Override
	public int InsertBoardMedia(BoardMediaFileInsertDTO media){
		return boardMapper.InsertBoardMedia(media);
	}
	@Override
	public int InsertBoard(int categorynum, int boardnum, String title, String writer, String existimgfile, String content){
		return boardMapper.InsertBoard(categorynum, boardnum, title, writer, existimgfile, content);
	}
	
	@Override
	@Transactional
	public void InvokeBoard(List<BoardMediaFileInsertDTO> media, String iboardnum, int categorynum, int boardnum, String title, String writer, String existimgfile, String content){
		System.out.print("================InvokeBoard Start=================");
		
		InsertBoard(categorynum, boardnum, title, writer, existimgfile, content);
		InsertBoardData(iboardnum);
		if(media != null) {
			for(BoardMediaFileInsertDTO temp : media) {
				InsertBoardMedia(temp);
			}
		}
		
		System.out.print("================InvokeBoard End=================");
	}
	
	
}
