package haule.raelfarm.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ViewBoardDTO {
	int categorynum;
	String categoryname;
	int boardnum;
	String title;
	String content;
}
