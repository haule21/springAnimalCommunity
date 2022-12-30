package haule.raelfarm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import haule.raelfarm.controller.StrategyCategory.Category100;
import haule.raelfarm.controller.StrategyCategory.Category200;
import haule.raelfarm.controller.StrategyCategory.CategoryStrategy;

@RequestMapping("/board")
public class BoardController {
	
	private static final CategoryStrategy[] categoryStrategyList = new CategoryStrategy[] {
			null, new Category100(), new Category200()
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
	@RequestMapping("/{categorynum}")
	public ModelAndView View_Boards( @PathVariable String categorynum ) {
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	public ModelAndView Write_Board( String categorynum, String title, String writer, String exist_imgfile, String content) {
		
		ModelAndView mv = new ModelAndView();
		
		// For Procedure
		CategoryStrategyWriteBoard(categoryStrategyList[(int)(Integer.valueOf(categorynum) / 100)]);
		
		
		
		return mv;
	}
	
	public ModelAndView View_Board( String board_num ) {
		ModelAndView mv = new ModelAndView();
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
	
	public void CategoryStrategyWriteBoard(CategoryStrategy categoryStrategy) {
		categoryStrategy.WriteBoard();
	}
}
