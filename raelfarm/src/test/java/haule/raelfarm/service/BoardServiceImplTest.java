package haule.raelfarm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import haule.raelfarm.dto.BoardMediaFileInsertDTO;
import haule.raelfarm.dto.MainSelectDTO;
import haule.raelfarm.dto.SearchDTO;
import haule.raelfarm.dto.ViewBoardDTO;
import haule.raelfarm.mapper.BoardMapper;
import haule.raelfarm.repository.BoardDataRepository;
import haule.raelfarm.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("BoardServiceImpl 단위 테스트")
class BoardServiceImplTest {
	
	@Mock
	private BoardMapper boardMapper;
	
	@Mock
	private BoardDataRepository boardDataRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private BoardServiceImpl boardService;
	
	@BeforeEach
	void setUp() {
		// 테스트 전 초기화 작업
	}
	
	@Test
	@DisplayName("게시글 조회 수 증가")
	void testViewCount() {
		// given
		String iboardnum = "2001001";
		doNothing().when(boardDataRepository).updateViewCount(iboardnum);
		
		// when
		boardService.ViewCount(iboardnum);
		
		// then
		verify(boardDataRepository, times(1)).updateViewCount(iboardnum);
	}
	
	@Test
	@DisplayName("카테고리 이름 조회")
	void testViewCategoryName() {
		// given
		int categoryNum = 200;
		String expectedName = "강아지";
		when(categoryRepository.ViewCategoryName(categoryNum)).thenReturn(expectedName);
		
		// when
		String result = boardService.ViewCategoryName(categoryNum);
		
		// then
		assertEquals(expectedName, result);
		verify(categoryRepository, times(1)).ViewCategoryName(categoryNum);
	}
	
	@Test
	@DisplayName("메인 데이터 조회")
	void testSelectMainDatas() {
		// given
		List<MainSelectDTO> expectedList = new ArrayList<>();
		MainSelectDTO dto = new MainSelectDTO();
		expectedList.add(dto);
		when(boardMapper.SelectMainDatas()).thenReturn(expectedList);
		
		// when
		List<MainSelectDTO> result = boardService.SelectMainDatas();
		
		// then
		assertNotNull(result);
		assertEquals(expectedList.size(), result.size());
		verify(boardMapper, times(1)).SelectMainDatas();
	}
	
	@Test
	@DisplayName("게시글 조회")
	void testSelectBoard() {
		// given
		int categorynum = 200;
		int boardnum = 1;
		ViewBoardDTO expectedDTO = new ViewBoardDTO();
		when(boardMapper.SelectBoard(categorynum, boardnum)).thenReturn(expectedDTO);
		
		// when
		ViewBoardDTO result = boardService.SelectBoard(categorynum, boardnum);
		
		// then
		assertNotNull(result);
		assertEquals(expectedDTO, result);
		verify(boardMapper, times(1)).SelectBoard(categorynum, boardnum);
	}
	
	@Test
	@DisplayName("게시글 작성 - InvokeBoard")
	void testInvokeBoard() {
		// given
		String iboardnum = "2001001";
		int categorynum = 200;
		int boardnum = 1;
		String title = "테스트 제목";
		String writer = "testuser";
		String existimgfile = "Y";
		String content = "테스트 내용";
		List<BoardMediaFileInsertDTO> media = new ArrayList<>();
		
		when(boardMapper.InsertBoard(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString()))
			.thenReturn(1);
		when(boardMapper.InsertBoardData(anyString())).thenReturn(1);
		
		// when
		boardService.InvokeBoard(media, iboardnum, categorynum, boardnum, title, writer, existimgfile, content);
		
		// then
		verify(boardMapper, times(1)).InsertBoard(categorynum, boardnum, title, writer, existimgfile, content);
		verify(boardMapper, times(1)).InsertBoardData(iboardnum);
	}
}

