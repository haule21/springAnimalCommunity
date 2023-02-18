package haule.raelfarm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.dto.UserInsertDTO;
import haule.raelfarm.service.BoardService;
import haule.raelfarm.service.UsersService;
import haule.raelfarm.singleton.messageAPI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.nurigo.sdk.message.service.DefaultMessageService;

@RestController
public class MainController {
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	BoardService boardService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	final DefaultMessageService messageService = messageAPI.getInstance().getMessageService();
	
	@RequestMapping("/")
	public ModelAndView Main(ModelAndView mv) {
		
		List<MainSelectDTO> datas = boardService.SelectMainDatas();
		
		List<MainSelectDTO> dataList1 = new ArrayList<MainSelectDTO>();
		List<MainSelectDTO> dataList2 = new ArrayList<MainSelectDTO>();
		List<MainSelectDTO> dataList3 = new ArrayList<MainSelectDTO>();
		List<MainSelectDTO> dataList4 = new ArrayList<MainSelectDTO>();
		List<MainSelectDTO> dataList5 = new ArrayList<MainSelectDTO>();
		
		for(MainSelectDTO data : datas) {
			switch((int)(data.getCategorynum()/100)) {
				case 1:
					dataList1.add(data);
					break;
				case 2:
					dataList2.add(data);
					break;
				case 3:
					dataList3.add(data);
					break;
				case 4:
					dataList4.add(data);
					break;
				case 5:
					dataList5.add(data);
					break;
			}
		}
		
		System.out.println("dataList1 :" + dataList1);
		System.out.println("dataList2 :" + dataList2);
		System.out.println("dataList3 :" + dataList3);
		System.out.println("dataList4 :" + dataList4);
		System.out.println("dataList5 :" + dataList5);
		
		mv.addObject("dataList1", dataList1);
		mv.addObject("dataList2", dataList2);
		mv.addObject("dataList3", dataList3);
		mv.addObject("dataList4", dataList4);
		mv.addObject("dataList5", dataList5);
		mv.setViewName("content/main/main");
		return mv;
	}

	
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView mv) {

		mv.setViewName("content/login/login");
		return mv;
	}
	
	@RequestMapping("/register_agree")
	public ModelAndView register_agree(ModelAndView mv) {
		mv.setViewName("content/login/register_agree");
		return mv;
	}
	
	@RequestMapping("/register_page")
	public ModelAndView register_page(ModelAndView mv, HttpServletRequest request) {
		
		// agree1 이용약관 동의 
		// agree2 개인정보 수집 및 동의
		if(request.getParameterValues("agree1") == null ? false : true 
				&& request.getParameterValues("agree2") == null ? false : true) {
			
			mv.setViewName("content/login/register_page");
			return mv;
			
		}
		else {
			mv.addObject("agree", "false");
			mv.setViewName("redirect:/register_agree");
			return mv;
		}		
	}
	

	
	@RequestMapping(value = "/register_page/idcheck", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> idcheck(@RequestBody String userid) {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		System.out.println("userid : " + userid + " result : " + usersService.idCheck(userid));
		map.put("cnt", usersService.idCheck(userid));
		
		return map;
	}
	
	@RequestMapping(value = "/register_page/nicknamecheck", method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> nicknamecheck(@RequestBody String nickname) {
		
		Map<Object, Object> map = new HashMap<Object, Object>(); 
		map.put("cnt", usersService.nicknameCheck(nickname));
		
		return map;
	}
	
	/**
     * 단일 메시지 발송 예제
     */
    @RequestMapping(value ="/register_page/send-one", method = RequestMethod.POST)
    public @ResponseBody Map<Object,Object> sendOne(@RequestBody String tophonenum, HttpSession session) {

        
        if(tophonenum.length() != 11 || tophonenum == null) {
        	return null;
        }
        else if(usersService.phoneCheck(tophonenum) == 1) {
        	Map<Object,Object> map = new HashMap<Object,Object>();
            map.put("duplicate", "duplicate");
            return map;
        }
//        Message message = new Message();
        
        // 인증번호 생성
        String authentication_number = excuteGenerate();
        
//        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
//        message.setFrom("from_phonenumber");
//        message.setTo(to_phonenum);
//        message.setText("[라엘이의 동물농장]\n인증번호 "+ authentication_number + "를 입력해 주세요.");
//
//        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
//        System.out.println(response);
        
        Map<Object,Object> map = new HashMap<Object,Object>();
        map.put("authentication_number", authentication_number);

        return map;
    }
    
    @RequestMapping(value="/register_page/register", method = RequestMethod.POST)
    public ModelAndView CreateUser(
    		RedirectAttributes re,
    		@RequestParam(value="userid") String userid,
    		@RequestParam(value="password") String password,
    		@RequestParam(value="nickname") String nickname,
    		@RequestParam(value="name") String name,
    		@RequestParam(value="email") String email,
    		@RequestParam(value="phonenumber") String phonenumber,
    		@RequestParam(value="address") String address,
    		@RequestParam(value="address_detail1") String address_detail1,
    		@RequestParam(value="address_detail2") String address_detail2) throws Exception {
    	ModelAndView mv = new ModelAndView();
    	
    	address = address != null ? address + " "+ address_detail1 + " " + address_detail2 : "";
    	
    	UserInsertDTO user = UserInsertDTO.builder()
    							.userid(userid)
    							.password(passwordEncoder.encode(password))
    							.nickname(nickname)
    							.name(name)
    							.email(email)
    							.phone_number(phonenumber)
    							.address(address).build();
    	
    	int result = usersService.createUser(user);
    	re.addFlashAttribute("result", result >  0 ? 1 : 0);
    	mv.setViewName("redirect:/login");
    	return mv;
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        
        mv.setViewName("redirect:login");
        return mv;
    }
    
    public String excuteGenerate() {
        Random random = new Random(System.currentTimeMillis());
        
        int range = (int)Math.pow(10, 6);
        int trim = (int)Math.pow(10, 5);
        int result = random.nextInt(range)+trim;
         
        if(result>range){
            result = result - trim;
        }
        
        System.out.print("result : " + result);
        return String.valueOf(result);
    }
	
}
