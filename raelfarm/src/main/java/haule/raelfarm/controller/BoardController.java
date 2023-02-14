package haule.raelfarm.controller;

import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import haule.raelfarm.controller.StrategyCategory.Category100;
import haule.raelfarm.controller.StrategyCategory.Category200;
import haule.raelfarm.controller.StrategyCategory.Category300;
import haule.raelfarm.controller.StrategyCategory.Category400;
import haule.raelfarm.controller.StrategyCategory.Category500;
import haule.raelfarm.controller.StrategyCategory.Category600;
import haule.raelfarm.controller.StrategyCategory.CategoryStrategy;
import haule.raelfarm.dto.BoardCommentDTO;
import haule.raelfarm.dto.BoardMediaFileInsertDTO;
import haule.raelfarm.dto.CategorySelectDTO;
import haule.raelfarm.dto.ViewBoardDTO;
import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.jpa.BoardMediaFile;
import haule.raelfarm.jpa.BoardRecommendHistory;
import haule.raelfarm.jpa.PK.BoardMediaFile_PK;
import haule.raelfarm.jpa.PK.BoardRecommendHistory_PK;
import haule.raelfarm.repository.BoardDataRepository;
import haule.raelfarm.repository.BoardMediaFileRepository;
import haule.raelfarm.repository.BoardPreviousRepository;
import haule.raelfarm.repository.BoardRecommendHistoryRepository;
import haule.raelfarm.repository.BoardRepository;
import haule.raelfarm.service.BoardService;
import haule.raelfarm.singleton.BoardInfo;
import haule.raelfarm.singleton.CommentInfo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardMediaFileRepository boardMediaFileRepo;
	
	@Autowired
	BoardPreviousRepository boardPreviousRepo;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	BoardDataRepository boardDataRepository;
	
	@Autowired
	BoardRecommendHistoryRepository boardRecommendHistoryRepository;
	
	private static final CategoryStrategy[] categoryStrategyList = new CategoryStrategy[] {
			null, new Category100(), new Category200(), new Category300(), new Category400(), new Category500(), new Category600()
	};
	
	/* 
	 	category_num, 
	 	- board_num -> insert category_(category_num) (And procedure insert category_(category_num)(null) before insert board  )  
	 	- register_date default,
	 	title,
	 	writer,
	 	- modified_dated default,
	 	- deleted default,
	 	exist_imgfile Y, N
	 	content
	 */
	@RequestMapping("/board/c{categorynum}")
	public ModelAndView View_Boards( @PathVariable("categorynum") String categorynum ) {
		ModelAndView mv = new ModelAndView();
		
		List<ViewBoardsDTO> data = CategoryStrategyViewBoard(categoryStrategyList[(int)(Integer.valueOf(categorynum) / 100)], Integer.valueOf(categorynum));
		for(ViewBoardsDTO data_ : data) {
			data_.SetRegisterDate();
		}
		
		mv.addObject("boardList", data);
		mv.addObject("category_name", boardService.ViewCategoryName(Integer.parseInt(categorynum)));
		mv.addObject("category_num", categorynum);
		mv.setViewName("content/main/board/view_boards");
		
		return mv;
	}
	
	@RequestMapping("/board/c{categorynum}/write")
	public ModelAndView Write_Board( @PathVariable("categorynum") String categorynum ) {
		ModelAndView mv = new ModelAndView();
		List<String> datas = CategoryStrategyViewCategorysData(categoryStrategyList[(int)(Integer.valueOf(categorynum) / 100)]);
		List<CategorySelectDTO> ctn_datas = new ArrayList<CategorySelectDTO>();
		for(String a : datas) {
			String[] temp = a.split(";");
			ctn_datas.add(
					CategorySelectDTO.builder().categorynum(Integer.parseInt(temp[0])).categoryname(temp[1]).build()
			);
		}
		mv.addObject("ctdatas", ctn_datas);
		mv.setViewName("content/main/board/write_board");
		return mv;
	}
	
	
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public ModelAndView Write_Board_Submit( 
			Principal principal,
			@RequestParam(value="summernote_categorynum") int summernote_categorynum, 
			@RequestParam(value="summernote_title") String summernote_title, 
			@RequestParam(value="summernote_content") String summernote_content,
			@RequestParam(value="uploaded_images", required = false) List<String> summernote_images ) {
		
		ModelAndView mv = new ModelAndView();
		
		// 이미지 필수 기입 ( check-board 에도 설정 해 놓았음 )
		if((int)(summernote_categorynum/100) == 4) {
			if(summernote_images == null) {
				mv.setViewName("redirect:/");
				return mv;
			}
		}
		
		String existImage = "N";
		List<BoardMediaFileInsertDTO> media = new ArrayList<BoardMediaFileInsertDTO>();
		
		// Writer 
		String userid = principal.getName();
		
		int boardnum = BoardInfo.getInstance(boardService).getBoardNumber(summernote_categorynum);
		String iboardnum = String.format("%05d", summernote_categorynum) + boardnum;

		if(summernote_images != null && summernote_images.size() > 0) {
			existImage = "Y";
			int count = 0;
			for(String temp : summernote_images) {
				
				// /summerImage/년/월/일/파일명.확장자
				String[] mediadatas = temp.split("/");
				// 년도 / 월 / 일 / 파일
				String filepath = mediadatas[2] + "/" + mediadatas[3] + "/" + mediadatas[4];
				String filename = mediadatas[5];
				String contenttype = CheckImageType(mediadatas[5]);
				
				System.out.println(mediadatas + contenttype);

				
				media.add(
					BoardMediaFileInsertDTO.builder()
						.iboardnum(iboardnum)
						.seq(count)
						.contenttype(contenttype)
						.filepath(filepath)
						.filename(filename)
						.build()
					);
				count ++;
			}
		}
		if(existImage == "N") {
			media = null;
		}
		
		boardService.InvokeBoard(media, iboardnum, summernote_categorynum, boardnum, summernote_title, userid, existImage, summernote_content);		
		mv.setViewName("redirect:/board/c"+summernote_categorynum);
		return mv;
	}
	
	@RequestMapping("/board/c{categorynum}/b{boardnum}")
	public ModelAndView View_Board( @PathVariable(value ="categorynum") String categorynum, @PathVariable(value ="boardnum") String boardnum,  
									@ModelAttribute("previouscategorynum") String previous_cn,
									HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mv = new ModelAndView();
		String iboardnum = String.format("%05d",Integer.valueOf(categorynum)) + boardnum; 
		ViewCountUp(iboardnum, req, res);
		
		List<ViewBoardsDTO> pndatas = CategoryStrategyViewPreviousNextBoards(
				categoryStrategyList[(int)(Integer.valueOf(previous_cn) / 100)], 
				Integer.valueOf(categorynum),
				Integer.valueOf(boardnum), 
				iboardnum);
		
		for(ViewBoardsDTO tempdata : pndatas) {
			switch(tempdata.getSeqtext()) {
				case "CURRENT":
					mv.addObject("data" , tempdata);
				break;
				case "NEXT":
					mv.addObject("nboard" , tempdata);
				break;
				case "PREVIOUS":
					mv.addObject("pboard" , tempdata);
				break;
			}
		}
		
		List<BoardCommentDTO> comment = boardService.SelectBoardComments(iboardnum);
		if(comment != null) {
			comment.forEach(a -> a.ChangeDate());
		}
		
		mv.addObject("recommendcount", boardService.SelectBoardRecommendCount(iboardnum));
		mv.addObject("commentList", comment);
		mv.addObject("category_num", previous_cn);
		mv.addObject("iboardnum", iboardnum);
		mv.setViewName("content/main/board/view_board");
		return mv;
	}
	 
	@RequestMapping("/board/c{categorynum}/b{boardnum}/modify")
	public ModelAndView Modify_Board(	Principal principal, HttpServletResponse response,
										@PathVariable(value ="categorynum") String categorynum, 
										@PathVariable(value ="boardnum") String boardnum) {
		ModelAndView mv = new ModelAndView();
		
		String userid = principal.getName();
		String writer = boardRepository.SelectWriter(Integer.parseInt(categorynum), Integer.parseInt(boardnum));
		if(userid.equals(writer)) {
			String iboardnum = String.format("%05d", Integer.parseInt(categorynum)) + boardnum;
			List<String> mediavalues = boardMediaFileRepo.SelectBoardMediaData(iboardnum);
			ViewBoardDTO data = boardService.SelectBoard(Integer.parseInt(categorynum), Integer.parseInt(boardnum));
			
			mv.addObject("mediaList", mediavalues);
			mv.addObject("data", data);
			mv.setViewName("content/main/board/modify_board");
			return mv;
		}
		else {
			alert(response, "수정할 수 없습니다.");
			mv.setViewName("content/main/board");
			return mv;
		}
		
		
	}
	
	@RequestMapping("/board/modify")
	public ModelAndView Modify_Board_Submit(
			Principal principal,
			@RequestParam(value="summernote_categorynum") int summernote_categorynum,
			@RequestParam(value="summernote_boardnum") int summernote_boardnum, 
			@RequestParam(value="summernote_title") String summernote_title, 
			@RequestParam(value="summernote_content") String summernote_content,
			@RequestParam(value="uploaded_images", required = false) List<String> summernote_images) {
		ModelAndView mv = new ModelAndView();
		
		String iboardnum = String.format("%05d", summernote_categorynum) + summernote_boardnum ;
		ViewBoardDTO boarddata = boardService.SelectBoard(summernote_categorynum, summernote_boardnum);
		if(!boarddata.getTitle().equals(summernote_title) || !boarddata.getContent().equals(summernote_content)) {

			boardService.InsertBoardPreviousContent(iboardnum,  boardPreviousRepo.SelectSEQNumber(iboardnum) + 1, boarddata.getTitle(), boarddata.getContent());
			
			//update 문
			boardService.UpdateBoardTitleContent(summernote_categorynum, summernote_boardnum, summernote_title, summernote_content);
		}
		else {
			mv.setViewName("redirect:/board/c"+summernote_categorynum+"/b"+summernote_boardnum+"?previouscategorynum="+summernote_categorynum);
			return mv;
		}
		
		if(summernote_images != null) {
			// media 변경 된 이미지 추가 
			List<String> mediavalues = boardMediaFileRepo.SelectBoardMediaData(String.format("%05d", summernote_categorynum) + summernote_boardnum);
			
			// 이미지 추가 되거나 삭제 된거 있으면 mediadata 테이블에 갱신 해주어야 함

			for(String mediadata : mediavalues) {
				if(summernote_images.contains(mediadata)) {
					summernote_images.remove(mediadata);
				}
			}
			
			if(summernote_images.size() > 0) {
				List<BoardMediaFile> savedatas1 = new ArrayList<>();
				int seq = boardMediaFileRepo.SelectSEQNumber(iboardnum);
				int count = 1;
				for(String modefieddata : summernote_images) {
					// SEQ/summernoteImage/년/월/일/파일명.확장자
					String[] temp = modefieddata.split("/");
					// 수정된 파일들
					savedatas1.add(BoardMediaFile.builder().pk(new BoardMediaFile_PK(iboardnum,seq+count)).contenttype(CheckImageType(temp[5])).filename(temp[5]).filepath(temp[2]+"/"+temp[3]+"/"+temp[4]).build());
					count ++;
				}
				boardMediaFileRepo.saveAll(savedatas1);
			}
		}
		
		
		
		////////////////////////////////////////////////////////////////////////////////////
		
		mv.setViewName("redirect:/board/c"+summernote_categorynum+"/b"+summernote_boardnum+"?previouscategorynum="+summernote_categorynum);
		return mv;
	}
	/*
	 * check exist board_recommended_history where board_num = and userid =  
	 */
	@RequestMapping(value="/board/recommend", method=RequestMethod.POST)
	@ResponseBody 
	public Map<String, String> Recommend_Board(
				Principal principal,
				@RequestParam(value="iboardnum") String iboardnum,
				@RequestParam(value="recommend") String recommend
	){
		
		Map<String, String> map = new HashMap<String, String>();
		
		BoardRecommendHistory_PK boardRecommendHistoryPK = new BoardRecommendHistory_PK(iboardnum, principal.getName());
		BoardRecommendHistory data = boardRecommendHistoryRepository.findById(boardRecommendHistoryPK).orElse(null);
		
		System.out.println("boardRecommendHistoryPK : "+ boardRecommendHistoryPK);
		System.out.println("data : "+ data);
		
		if(data == null && (recommend.equals("Y") || recommend.equals("N"))) {
				BoardRecommendHistory boardRecommendHistory_data = BoardRecommendHistory.builder().pk(boardRecommendHistoryPK).RECOMMEND(recommend).build();
				System.out.println("boardRecommendHistory_data : "+ boardRecommendHistory_data);
				if(recommend.equals("Y")) boardDataRepository.updateRecommendCount(iboardnum);
				else boardDataRepository.updateNoRecommendCount(iboardnum);
				boardRecommendHistoryRepository.save(boardRecommendHistory_data);
				map.put("responseCode", "success");
		}
		else {
			map.put("responseCode", "error");
		}
		
		return map;	
	}
	
	@RequestMapping(value="/board/view/recommend")
	public ModelAndView View_Board_Recommend(
					ModelAndView mv,
					@RequestParam(value="iboardnum") String iboardnum) {
		
		int recommendcount = boardService.SelectBoardRecommendCount(iboardnum);
		mv.addObject("recommendcount", recommendcount);
		mv.setViewName("fragments/board_recommend_fragment");
		return mv;
	}
	
	
	/*
	 * board_num -> category_num + board_num
	 * comment_no -> ifnull(select comment_no from board_comment where board_num =, 1)
	 * seq - 0  
	 */
	@RequestMapping(value="/comment/write", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> Write_Comment_Board(
				Principal principal,
				@RequestParam(value="iboardnum") String iboardnum,
				@RequestParam(value="content") String content
	) {
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			int commentno = CommentInfo.getInstance(boardService).getCommentNumber();
			boardService.InsertBoardComment(
					iboardnum,
					commentno,
					0, 
					principal.getName(), 
					content);
			map.put("commentno", String.valueOf(commentno));
			map.put("responseCode", "success");
		}
		catch(Exception e) {
			map.put("responseCode", "error");
		}
			
		
		return map;
	}
	
	@RequestMapping(value="/comment/view")
	public ModelAndView View_Comment(
			ModelAndView mv,
			@RequestParam(value="iboardnum") String iboardnum) {
		
		List<BoardCommentDTO> comment = boardService.SelectBoardComments(iboardnum);
		if(comment != null) {
			comment.forEach(a -> a.ChangeDate());
		}
		mv.addObject("commentList", comment);		
		mv.setViewName("fragments/comment_fragment");
		return mv;
	}
	
	/*
	 * seq -> select max(seq) + 1 from board_comment where board_num = and comment_num = 
	 */
	@RequestMapping(value="/comment/recomment/write", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> Write_Recomment_Comment(
			Principal principal,
			@RequestParam(value="iboardnum") String iboardnum,
			@RequestParam(value="parentcommentno") int parentcommentno,
			@RequestParam(value="content") String content
	) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			int commentno = CommentInfo.getInstance(boardService).getCommentNumber();
			int result = boardService.InsertBoardComment(
					iboardnum,
					commentno,
					parentcommentno, 
					principal.getName(), 
					content);
			map.put("commentno", String.valueOf(commentno));
			map.put("reponseCode","success");
		}
		catch (Exception e) {
			map.put("reponseCode","error");
		}
		
		return map;
	}
	
	/*
	 * check exist board_comment_recommended_history where board_num = and comment_no = and userid = ( seq = )
	 */
	@RequestMapping(value="/comment/recommend", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> Recommend_Comment(
			@RequestParam(value="iboardnum") String iboardnum,
			@RequestParam(value="commentno") int commentno,
			@RequestParam(value="recommend") String recommend,
			Principal principal
			
	) {
		// reply_num : categorynum(00101)boardnum(..):commentno:seq
		Map<String, String> map = new HashMap<String, String>();
		
		// 1: exist , 0: not-exist
		int result = boardService.SelectCheckBoardCommentRecommendHistory(commentno, principal.getName());
		try {
			if(result == 0) {
				boardService.InsertBoardCommentRecommendHistory(commentno, principal.getName(), recommend);
				boardService.UpdateIncreaseBoardCommentRecommend(iboardnum, commentno, recommend);
				map.put("result", "0");
			}
			else {
				map.put("result", "1");
			}
			map.put("responseCode", "success");
		}
		catch(Exception e) {
			map.put("responseCode", "error");
		}
		
		
		
		return map;	
	}
	
	public List<String> CategoryStrategyViewCategorysData(CategoryStrategy categoryStrategy) {
		return categoryStrategy.ViewCategorysData(boardService);
	}
	public List<ViewBoardsDTO> CategoryStrategyViewBoard(CategoryStrategy categoryStrategy, int category_num) {
		return categoryStrategy.ViewBoard(category_num, boardService);
	}
	public List<ViewBoardsDTO> CategoryStrategyViewPreviousNextBoards(CategoryStrategy categoryStrategy, int category_num, int board_num, String iboardnum) {
		return categoryStrategy.ViewPreviousNextBoards(category_num, board_num, iboardnum, boardService);
	}
	
	private void ViewCountUp(String id, HttpServletRequest req, HttpServletResponse res) {

			Cookie oldCookie = null;

			Cookie[] cookies = req.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if (cookie.getName().equals("boardView")) {
	                    oldCookie = cookie;
	                }
	            }
	        }

	        if (oldCookie != null) {
	            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
	                boardService.ViewCount(id);
	                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
	                oldCookie.setPath("/");
	                oldCookie.setMaxAge(60 * 60 * 24);
	                res.addCookie(oldCookie);
	            }
	        } else {
	            boardService.ViewCount(id);
	            Cookie newCookie = new Cookie("boardView","[" + id + "]");
	            newCookie.setPath("/");
	            newCookie.setMaxAge(60 * 60 * 24);
	            res.addCookie(newCookie);
	        }
	    }
	
	private String CheckImageType(String temp) {
		if(temp.indexOf("PNG") > 0 || temp.indexOf("png") > 0) {
			return "PNG";
		}
		else if(temp.indexOf("JPEG") > 0 || temp.indexOf("JPG") > 0 || temp.indexOf("jpeg") > 0 || temp.indexOf("jpg") > 0){
			return "JPEG";
		}
		else if(temp.indexOf("GIF") > 0 || temp.indexOf("gif") > 0) {
			return "GIF";
		}
		else if(temp.indexOf("SVG") > 0 || temp.indexOf("svg") > 0) {
			return "SVG";
		}
		else {
			return null;
		}
	}
	public static void alert(HttpServletResponse response, String msg) {
	    try {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('"+msg+"');</script>");
			w.flush();
			w.close();
	    } catch(Exception e) {
			e.printStackTrace();
	    }
	}
}
