package haule.raelfarm.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * 전역 예외 처리기
 * 모든 컨트롤러에서 발생하는 예외를 일관되게 처리합니다.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * 일반적인 예외 처리
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleException(Exception e) {
		log.error("예외 발생: ", e);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("error", "서버 오류가 발생했습니다.");
		mv.addObject("message", e.getMessage());
		mv.setViewName("error/500");
		return mv;
	}
	
	/**
	 * IllegalArgumentException 처리 (잘못된 파라미터 등)
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
		log.warn("잘못된 요청: {}", e.getMessage());
		
		Map<String, Object> response = new HashMap<>();
		response.put("error", "BAD_REQUEST");
		response.put("message", e.getMessage());
		
		return ResponseEntity.badRequest().body(response);
	}
	
	/**
	 * NullPointerException 처리
	 */
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException e) {
		log.error("NullPointerException 발생: ", e);
		
		Map<String, Object> response = new HashMap<>();
		response.put("error", "INTERNAL_SERVER_ERROR");
		response.put("message", "필수 값이 누락되었습니다.");
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
	/**
	 * RuntimeException 처리
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException e) {
		log.error("RuntimeException 발생: ", e);
		
		Map<String, Object> response = new HashMap<>();
		response.put("error", "INTERNAL_SERVER_ERROR");
		response.put("message", e.getMessage() != null ? e.getMessage() : "서버 오류가 발생했습니다.");
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}

