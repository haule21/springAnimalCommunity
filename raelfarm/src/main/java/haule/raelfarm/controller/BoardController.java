package haule.raelfarm.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import haule.raelfarm.controller.StrategyCategory.Category100;
import haule.raelfarm.controller.StrategyCategory.Category200;
import haule.raelfarm.controller.StrategyCategory.Category300;
import haule.raelfarm.controller.StrategyCategory.Category400;
import haule.raelfarm.controller.StrategyCategory.Category500;
import haule.raelfarm.controller.StrategyCategory.CategoryStrategy;
import haule.raelfarm.dto.BoardMediaFileInsertDTO;
import haule.raelfarm.dto.CategorySelectDTO;
import haule.raelfarm.dto.ViewBoardsDTO;
import haule.raelfarm.service.BoardService;
import haule.raelfarm.singleton.BoardInfo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	private static final CategoryStrategy[] categoryStrategyList = new CategoryStrategy[] {
			null, new Category100(), new Category200(), new Category300(), new Category400(), new Category500()
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
				System.out.println("mediadatas[5] : "+mediadatas[5]);
				System.out.println("iboardnum : "+iboardnum);
				System.out.println("contenttype : "+contenttype);
				
				media.add(
					BoardMediaFileInsertDTO.builder()
						.I_BOARD_NUM(iboardnum)
						.SEQ(count)
						.CONTENT_TYPE(contenttype)
						.FILE_PATH(filepath)
						.FILE_NAME(filename)
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
		System.out.println(iboardnum);
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
		
		mv.addObject("category_num", previous_cn);
		mv.setViewName("content/main/board/view_board");
		return mv;
	}
	
	/*
	 * check exist board_recommended_history where board_num = and userid =  
	 */
	public Map<Object, Object> Recommend_Board(
				String board_num,
				String userid,
				String yn
	){
		Map<Object, Object> mv = new HashMap<Object, Object>();
		return mv;	
	}
	
	/*
	 * board_num -> category_num + board_num
	 * comment_no -> ifnull(select comment_no from board_comment where board_num =, 1)
	 * seq - 0  
	 */
	public ModelAndView Write_Comment_Board(
				String board_num 
	) {
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	/*
	 * seq -> select max(seq) + 1 from board_comment where board_num = and comment_num = 
	 */
	public ModelAndView Write_Recomment_Comment(
				String board_num,
				int comment_no
	) {
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	/*
	 * check exist board_comment_recommended_history where board_num = and comment_no = and userid = ( seq = )
	 */
	public Map<Object, Object> Recommend_Comment(
			String reply_num,
			String userid,
			String yn
	) {
		Map<Object, Object> mv = new HashMap<Object, Object>();
		return mv;	
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
		System.out.println("temp : "+ temp);
		System.out.println("temp : "+ temp.indexOf("PNG"));
		System.out.println("temp : "+ temp.indexOf("JPEG"));
		System.out.println("temp : "+ temp.indexOf("GIF"));
		System.out.println("temp : "+ temp.indexOf("SVG"));
		if(temp.indexOf("PNG") > 0) {
			return "PNG";
		}
		else if(temp.indexOf("JPEG") > 0 || temp.indexOf("JPG") > 0) {
			return "JPEG";
		}
		else if(temp.indexOf("GIF") > 0) {
			return "GIF";
		}
		else if(temp.indexOf("SVG") > 0) {
			return "SVG";
		}
		else {
			return null;
		}
	}
}
