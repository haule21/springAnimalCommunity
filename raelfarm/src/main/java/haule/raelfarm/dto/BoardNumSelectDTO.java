package haule.raelfarm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class BoardNumSelectDTO {
	int categorynum;
	int boardnum;
	
	@Builder
	public BoardNumSelectDTO(int categorynum, int boardnum){
		this.categorynum = categorynum;
		this.boardnum = boardnum;
	}
}
