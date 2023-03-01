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
	private int commentcount;
	private String existimgfile;
	
	public MainSelectDTO imageTitleSet(){
		if(this.title.length() > 7) {
			this.title = this.title.substring(0, 8) + "...";
		}
		return this;
	}
}
