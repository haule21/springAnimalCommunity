package haule.raelfarm.controller;

import java.awt.PageAttributes.MediaType;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import haule.raelfarm.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class MainController {
	
	@Autowired
	UsersService usersService;
	
	@RequestMapping("/")
	public ModelAndView Main(ModelAndView mv) {
		mv.setViewName("test");
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView mv) {
		mv.setViewName("login");
		return mv;
	}
	
	@RequestMapping("/register_agree")
	public ModelAndView register_agree(ModelAndView mv) {
		mv.setViewName("login/register_agree");
		return mv;
	}
	
	@RequestMapping("/register_page")
	public ModelAndView register_page(ModelAndView mv, HttpServletRequest request) {
		
		// agree1 이용약관 동의 
		// agree2 개인정보 수집 및 동의
		if(request.getParameterValues("agree1") == null ? false : true 
				&& request.getParameterValues("agree2") == null ? false : true) {
			
			mv.setViewName("login/register_page");
			return mv;
			
		}
		else {
			mv.addObject("agree", "false");
			mv.setViewName("redirect:/register_agree");
			return mv;
		}		
	}
	
	@RequestMapping(value = "/register_page/register", method = RequestMethod.POST)
	public ModelAndView register_page_post(ModelAndView mv) {
		return mv;
	}
	
	@RequestMapping(value = "/register_page/idcheck", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> idcheck(@RequestBody String userid) {
		
		Map<Object, Object> map = new HashMap<Object, Object>(); 
		map.put("cnt", usersService.idCheck(userid));
		
		return map;
	}
	
}
