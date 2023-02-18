package haule.raelfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import haule.raelfarm.dto.BoardCommentDTO;
import haule.raelfarm.dto.BoardMediaFileInsertDTO;
import haule.raelfarm.dto.BoardNumSelectDTO;
import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.dto.SearchDTO;
import haule.raelfarm.dto.ViewBoardDTO;
import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.pagination.PagingResponse;

@Mapper
@Component
public interface BoardMapper {
	List<MainSelectDTO> SelectMainDatas();
	
	List<ViewBoardsDTO> SelectBoardsAll(@Param("params") SearchDTO searchDTO);
	int SelectBoardsAll_CNT(@Param("params") SearchDTO searchDTO);
	List<ViewBoardsDTO> SelectBoards(
			@Param("category_num") int category_num, 
			@Param("category_num_st") int Starting, 
			@Param("category_num_ed") int Ending,
			@Param("params") SearchDTO searchDTO);
	int SelectBoards_CNT(
			@Param("category_num") int category_num, 
			@Param("category_num_st") int Starting, 
			@Param("category_num_ed") int Ending,
			@Param("params") SearchDTO searchDTO);
	
	List<ViewBoardsDTO> SelectPreviousNextBoards(
			@Param("category_num") int category_num, 
			@Param("board_num") int board_num,
			@Param("iboardnum") String iboardnum,
			@Param("category_num_st") int Starting, 
			@Param("category_num_ed") int Ending,
			@Param("params") SearchDTO searchDTO);
	List<BoardCommentDTO> SelectBoardComments(@Param("iboardnum") String iboardnum, @Param("params") SearchDTO searchDTO);
	int SelectBoardComments_CNT(@Param("iboardnum") String iboardnum);
	
	List<BoardNumSelectDTO> SelectBoardNum();
	ViewBoardDTO SelectBoard(int categorynum, int boardnum);
	int SelectBoardRecommendCount(String iboardnum);
	List<String> SelectBoardMediaData(String iboardnum);	
	
	int SelectCheckBoardCommentRecommendHistory(int commentno, String userid);
	int SelectBoardCommentMAXCommentNo();
	
	
	int InsertBoardData(String iboardnum);
	int InsertBoardMedia(BoardMediaFileInsertDTO media);
	int InsertBoard(int categorynum, int boardnum, String title, String writer, String existimgfile, String content);
	int InsertBoardCommentRecommendHistory(int commentno, String userid, String recommend);
	
	int InsertBoardPreviousContent(String iboardnum, int seq, String title,String content);
	
	int InsertBoardComment(String iboardnum,int commentno, int parentcommentno, String writer,String content);
	
	int UpdateBoardTitleContent(int categorynum, int boardnum,String title, String content);
	int UpdateIncreaseBoardCommentRecommend(String iboardnum, int commentno, String recommend);
}
