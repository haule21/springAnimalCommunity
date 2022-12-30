package haule.raelfarm.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MainSelectDTO {	
	private int categorynum;
	private String boardnum;
	private String title;
	private String imgpath;
}
