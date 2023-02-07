package haule.raelfarm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class BoardMediaFileInsertDTO {
	String I_BOARD_NUM;
	int SEQ;
	String CONTENT_TYPE;
	String FILE_NAME;
	String FILE_PATH;
	
	@Builder
	public BoardMediaFileInsertDTO(String I_BOARD_NUM, int SEQ,String CONTENT_TYPE, String FILE_NAME, String FILE_PATH) {
		this.I_BOARD_NUM = I_BOARD_NUM;
		this.SEQ = SEQ;
		this.CONTENT_TYPE = CONTENT_TYPE;
		this.FILE_NAME = FILE_NAME;
		this.FILE_PATH = FILE_PATH;
	}
}
