package haule.raelfarm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class BoardMediaFileInsertDTO {
	String iboardnum;
	int seq;
	String contenttype;
	String filename;
	String filepath;
	
	@Builder
	public BoardMediaFileInsertDTO(String iboardnum, int seq,String contenttype, String filename, String filepath) {
		this.iboardnum = iboardnum;
		this.seq = seq;
		this.contenttype = contenttype;
		this.filename = filename;
		this.filepath = filepath;
	}
}
