package haule.raelfarm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import haule.raelfarm.dto.BoardCommentDTO;
import haule.raelfarm.dto.BoardMediaFileInsertDTO;
import haule.raelfarm.dto.BoardNumSelectDTO;
import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.dto.SearchDTO;
import haule.raelfarm.dto.ViewBoardDTO;
import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.mapper.BoardMapper;
import haule.raelfarm.pagination.Pagination;
import haule.raelfarm.pagination.PagingResponse;
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
	public int SelectBoardPage(int prectn,int category_num, int board_num, int Starting, int Ending) {
		return boardMapper.SelectBoardPage(prectn, category_num, board_num, Starting, Ending);
	}
	@Override
	public int SelectBoardsAll_CNT(SearchDTO searchDTO) {
		return boardMapper.SelectBoardsAll_CNT(searchDTO);
	}
	
	@Override
	public PagingResponse<ViewBoardsDTO> SelectBoardsAll(SearchDTO searchDTO){
		int count = boardMapper.SelectBoardsAll_CNT(searchDTO);
		Pagination pagination = new Pagination(count, searchDTO);
		searchDTO.setPagination(pagination);
		
		List<ViewBoardsDTO> list = boardMapper.SelectBoardsAll(searchDTO);
		for(ViewBoardsDTO data : list) {
			data.SetRegisterDate();
		}
		
		return new PagingResponse<>(list , pagination);
	}
	@Override
	public int SelectBoards_CNT(int category_num, int Starting, int Ending, SearchDTO searchDTO){
		return boardMapper.SelectBoards_CNT(category_num, Starting, Ending, searchDTO);
	}
	
	@Override
	public PagingResponse<ViewBoardsDTO> SelectBoards(int category_num, int Starting, int Ending, SearchDTO searchDTO){
		int count = boardMapper.SelectBoards_CNT(category_num, Starting, Ending, searchDTO);
		Pagination pagination = new Pagination(count, searchDTO);
		searchDTO.setPagination(pagination);
		List<ViewBoardsDTO> list = boardMapper.SelectBoards(category_num, Starting, Ending, searchDTO);
		for(ViewBoardsDTO data : list) {
			data.SetRegisterDate();
		}
		return new PagingResponse<>(list, pagination);
	}
	@Override
	public PagingResponse<BoardCommentDTO> SelectBoardComments(String iboardnum, SearchDTO searchDTO){
		int count = boardMapper.SelectBoardComments_CNT(iboardnum);
		Pagination pagination = new Pagination(count, searchDTO);
		searchDTO.setPagination(pagination);
		
		List<BoardCommentDTO> list = boardMapper.SelectBoardComments(iboardnum, searchDTO);
		if(list != null) {
			list.forEach(a -> a.ChangeDate());
		}
		
		return new PagingResponse<>(list, pagination);
	}
	@Override
	public int SelectBoardComments_CNT(String iboardnum){
		return boardMapper.SelectBoardComments_CNT(iboardnum);
	}
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
	public PagingResponse<ViewBoardsDTO> SelectPreviousNextBoards(int category_num, int board_num, String iboardnum, int Starting, int Ending, SearchDTO searchDTO, int page){
		searchDTO.setPage(page);
		int count = boardMapper.SelectBoards_CNT(category_num, Starting, Ending, searchDTO);
		Pagination pagination = new Pagination(count, searchDTO);
		searchDTO.setPagination(pagination);
		List<ViewBoardsDTO> list = boardMapper.SelectPreviousNextBoards(category_num, board_num, iboardnum, Starting, Ending, searchDTO);
		for(ViewBoardsDTO data : list) {
			data.SetRegisterDate();
		}
		return new PagingResponse<>(list, pagination);
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
	public int SelectBoardRecommendCount(String iboardnum) {
		return boardMapper.SelectBoardRecommendCount(iboardnum);
	}
	
	@Override
	public int SelectBoardCommentMAXCommentNo() {
		return boardMapper.SelectBoardCommentMAXCommentNo();
	}
	@Override
	public int SelectCheckBoardCommentRecommendHistory(int commentno, String userid) {
		return boardMapper.SelectCheckBoardCommentRecommendHistory(commentno, userid);
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
	
	@Override
	public int InsertBoardPreviousContent(String iboardnum, int seq, String title, String content) {
		return boardMapper.InsertBoardPreviousContent(iboardnum, seq, title, content);
	}
	
	@Override
	public int InsertBoardComment(String iboardnum,int commentno, int parentcommentno, String writer,String content) {
		return boardMapper.InsertBoardComment(iboardnum, commentno, parentcommentno, writer, content);
	}
	
	@Override
	public int InsertBoardCommentRecommendHistory(int commentno, String userid, String recommend) {
		return boardMapper.InsertBoardCommentRecommendHistory(commentno, userid, recommend);
	}
	
	@Override
	public int UpdateBoardTitleContent(int categorynum, int boardnum,String title, String content) {
		return boardMapper.UpdateBoardTitleContent(categorynum, boardnum, title, content);
	}
	@Override
	public int UpdateIncreaseBoardCommentRecommend(String iboardnum, int commentno, String recommend) {
		return boardMapper.UpdateIncreaseBoardCommentRecommend(iboardnum, commentno, recommend);
	}
	
}
