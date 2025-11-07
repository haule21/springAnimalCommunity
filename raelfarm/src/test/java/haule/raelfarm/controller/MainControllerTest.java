package haule.raelfarm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import haule.raelfarm.config.SecurityConfig;
import haule.raelfarm.service.BoardService;
import haule.raelfarm.service.UsersService;
import haule.raelfarm.singleton.messageAPI;

@WebMvcTest(controllers = MainController.class, excludeAutoConfiguration = SecurityConfig.class)
@DisplayName("MainController 통합 테스트")
class MainControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UsersService usersService;
	
	@MockBean
	private BoardService boardService;
	
	@MockBean
	private messageAPI messageAPI;
	
	@BeforeEach
	void setUp() {
		// 테스트 전 초기화 작업
	}
	
	@Test
	@DisplayName("메인 페이지 접근 테스트")
	void testMain() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("content/main/main"));
	}
	
	@Test
	@DisplayName("로그인 페이지 접근 테스트")
	void testLogin() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("content/login/login"));
	}
	
	@Test
	@DisplayName("회원가입 동의 페이지 접근 테스트")
	void testRegisterAgree() throws Exception {
		mockMvc.perform(get("/register_agree"))
			.andExpect(status().isOk())
			.andExpect(view().name("content/login/register_agree"));
	}
}

