package haule.raelfarm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import haule.raelfarm.dto.UserInsertDTO;
import haule.raelfarm.mapper.UsersMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsersServiceimpl 단위 테스트")
class UsersServiceimplTest {
	
	@Mock
	private UsersMapper usersMapper;
	
	@InjectMocks
	private UsersServiceimpl usersService;
	
	@BeforeEach
	void setUp() {
		// 테스트 전 초기화 작업
	}
	
	@Test
	@DisplayName("아이디 중복 체크 - 중복된 경우")
	void testIdCheck_Duplicate() {
		// given
		String userid = "testuser";
		when(usersMapper.idCheck(userid)).thenReturn(1);
		
		// when
		int result = usersService.idCheck(userid);
		
		// then
		assertEquals(1, result);
		verify(usersMapper, times(1)).idCheck(userid);
	}
	
	@Test
	@DisplayName("아이디 중복 체크 - 사용 가능한 경우")
	void testIdCheck_Available() {
		// given
		String userid = "newuser";
		when(usersMapper.idCheck(userid)).thenReturn(0);
		
		// when
		int result = usersService.idCheck(userid);
		
		// then
		assertEquals(0, result);
		verify(usersMapper, times(1)).idCheck(userid);
	}
	
	@Test
	@DisplayName("닉네임 중복 체크")
	void testNicknameCheck() {
		// given
		String nickname = "testnick";
		when(usersMapper.nicknameCheck(nickname)).thenReturn(1);
		
		// when
		int result = usersService.nicknameCheck(nickname);
		
		// then
		assertEquals(1, result);
		verify(usersMapper, times(1)).nicknameCheck(nickname);
	}
	
	@Test
	@DisplayName("전화번호 중복 체크")
	void testPhoneCheck() {
		// given
		String phonenumber = "01012345678";
		when(usersMapper.phoneCheck(phonenumber)).thenReturn(1);
		
		// when
		int result = usersService.phoneCheck(phonenumber);
		
		// then
		assertEquals(1, result);
		verify(usersMapper, times(1)).phoneCheck(phonenumber);
	}
	
	@Test
	@DisplayName("회원가입 성공")
	void testCreateUser_Success() {
		// given
		UserInsertDTO userDTO = UserInsertDTO.builder()
				.userid("testuser")
				.password("encodedPassword")
				.nickname("testnick")
				.name("테스트")
				.email("test@test.com")
				.phone_number("01012345678")
				.address("서울시 강남구")
				.build();
		
		when(usersMapper.createUser(any(UserInsertDTO.class))).thenReturn(1);
		
		// when
		int result = usersService.createUser(userDTO);
		
		// then
		assertEquals(1, result);
		verify(usersMapper, times(1)).createUser(userDTO);
	}
	
	@Test
	@DisplayName("회원가입 실패")
	void testCreateUser_Failure() {
		// given
		UserInsertDTO userDTO = UserInsertDTO.builder()
				.userid("testuser")
				.password("encodedPassword")
				.nickname("testnick")
				.name("테스트")
				.email("test@test.com")
				.phone_number("01012345678")
				.address("서울시 강남구")
				.build();
		
		when(usersMapper.createUser(any(UserInsertDTO.class))).thenReturn(0);
		
		// when
		int result = usersService.createUser(userDTO);
		
		// then
		assertEquals(0, result);
		verify(usersMapper, times(1)).createUser(userDTO);
	}
}

