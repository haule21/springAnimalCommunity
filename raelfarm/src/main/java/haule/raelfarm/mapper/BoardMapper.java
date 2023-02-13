package haule.raelfarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import haule.raelfarm.dto.BoardCommentDTO;
import haule.raelfarm.dto.BoardMediaFileInsertDTO;
import haule.raelfarm.dto.BoardNumSelectDTO;
import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.dto.ViewBoardDTO;
import haule.raelfarm.dto.ViewBoardsDTO;

@Mapper
@Component
public interface BoardMapper {
	List<MainSelectDTO> SelectMainDatas();
	
	List<ViewBoardsDTO> SelectBoards(
			@Param("category_num") int category_num, 
			@Param("category_num_st") int Starting, 
			@Param("category_num_ed") int Ending);
	
	List<ViewBoardsDTO> SelectPreviousNextBoards(
			@Param("category_num") int category_num, 
			@Param("board_num") int board_num,
			@Param("iboardnum") String iboardnum,
			@Param("category_num_st") int Starting, 
			@Param("category_num_ed") int Ending);
	
	List<BoardNumSelectDTO> SelectBoardNum();
	ViewBoardDTO SelectBoard(int categorynum, int boardnum);
	int SelectBoardRecommendCount(String iboardnum);
	List<String> SelectBoardMediaData(String iboardnum);	
	List<BoardCommentDTO> SelectBoardComments(String iboardnum, int commentno);
	int SelectCheckBoardCommentRecommendHistory(String ireplynum, String userid);
	int SelectBoardCommentMAXCommentNo(String iboardnum);
	int SelectBoardCommentMAXSeq(String iboardnum, int commentno);
	List<BoardCommentDTO> SelectBoardRecomments(String iboardnum, int commentno);
	
	
	int InsertBoardData(String iboardnum);
	int InsertBoardMedia(BoardMediaFileInsertDTO media);
	int InsertBoard(int categorynum, int boardnum, String title, String writer, String existimgfile, String content);
	int InsertBoardCommentRecommendHistory(String ireplynum, String userid, String recommend);
	
	int InsertBoardPreviousContent(String iboardnum, int seq, String title,String content);
	
	int InsertBoardComment(String iboardnum,int comment_no,int seq,String writer,String content);
	
	int UpdateBoardTitleContent(int categorynum, int boardnum,String title, String content);
	int UpdateIncreaseBoardCommentRecommend(String iboardnum, int commentno, int seq, String recommend);
}
