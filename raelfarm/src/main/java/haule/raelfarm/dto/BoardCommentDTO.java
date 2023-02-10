package haule.raelfarm.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class BoardCommentDTO {
	String iboardnum;
	int commentno;
	int seq;
	String registereddate;
	String writer;
	String content;
	String modifieddate;
	int recommendcount;
	int norecommendcount;
	String deleted;
	
	@Builder
	BoardCommentDTO(
				String iboardnum,
				int commentno,
				int seq,
				String registereddate,
				String writer,
				String content,
				String modifieddate,
				int recommendcount,
				int norecommendcount,
				String DELETED){
		this.iboardnum = iboardnum; 
		this.commentno = commentno;
		this.seq = seq;
		this.registereddate = registereddate;
		this.writer = writer;
		this.content = content;
		this.modifieddate = modifieddate;
		this.recommendcount = recommendcount;
		this.norecommendcount = norecommendcount;
		this.deleted = DELETED;
		
	}
}
