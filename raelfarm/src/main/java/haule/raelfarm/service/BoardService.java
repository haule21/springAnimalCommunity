package haule.raelfarm.service;

import java.util.List;

import org.springframework.stereotype.Component;

import haule.raelfarm.dto.BoardCommentDTO;
import haule.raelfarm.dto.BoardMediaFileInsertDTO;
import haule.raelfarm.dto.BoardNumSelectDTO;
import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.dto.SearchDTO;
import haule.raelfarm.dto.ViewBoardDTO;
import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.pagination.PagingResponse;

@Component
public interface BoardService {
	public PagingResponse<ViewBoardsDTO> SelectBoardsAll(SearchDTO searchDTO);
	public int SelectBoardsAll_CNT(SearchDTO searchDTO);
	public PagingResponse<ViewBoardsDTO> SelectBoards(int category_num, int Starting, int Ending, SearchDTO searchDTO);
	public int SelectBoards_CNT(int category_num, int Starting, int Ending, SearchDTO searchDTO);
	public List<ViewBoardsDTO> SelectPreviousNextBoards(int category_num, int board_num,String iboardnum,int Starting, int Ending, SearchDTO searchDTO);
	public PagingResponse<BoardCommentDTO> SelectBoardComments(String iboardnum, SearchDTO searchDTO);
	public int SelectBoardComments_CNT(String iboardnum);
	
	public List<MainSelectDTO> SelectMainDatas();
	public void ViewCount(String iboardnum);
	public String ViewCategoryName(int category_num);
	public List<String> ViewCategorysName(int category_num_st, int category_num_ed);
	public List<BoardNumSelectDTO> SelectBoardNumMAX();
	public ViewBoardDTO SelectBoard(int categorynum, int boardnum);
	public int SelectBoardRecommendCount(String iboardnum);
	int SelectBoardCommentMAXCommentNo();
	int SelectCheckBoardCommentRecommendHistory(int commentno, String userid);
	public int InsertBoardData(String iboardnum);
	public int InsertBoardMedia(BoardMediaFileInsertDTO media);
	public int InsertBoard(int categorynum, int boardnum, String title, String writer, String existimgfile, String content);
	public void InvokeBoard(List<BoardMediaFileInsertDTO> media, String iboardnum, int categorynum, int boardnum, String title, String writer, String existimgfile, String content);
	
	int InsertBoardPreviousContent(String iboardnum, int seq, String title,String content);
	int InsertBoardComment(String iboardnum,int commentno, int parentcommentno, String writer,String content);
	int InsertBoardCommentRecommendHistory(int commentno, String userid, String recommend);
	
	int UpdateBoardTitleContent(int categorynum, int boardnum,String title, String content);
	int UpdateIncreaseBoardCommentRecommend(String iboardnum, int commentno, String recommend);
}
