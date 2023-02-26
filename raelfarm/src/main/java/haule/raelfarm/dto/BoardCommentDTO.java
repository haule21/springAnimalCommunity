package haule.raelfarm.dto;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class BoardCommentDTO {	
	String iboardnum;
	int commentno;
	int parentcommentno;
	String parentwriter;
	int level;
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
				int parentcommentno,
				String parentcommentwriter,
				String registereddate,
				String writer,
				String content,
				String modifieddate,
				int recommendcount,
				int norecommendcount,
				String DELETED){
		this.iboardnum = iboardnum; 
		this.commentno = commentno;
		this.parentcommentno = commentno;
		this.parentwriter = parentcommentwriter;
		this.registereddate = registereddate;
		this.writer = writer;
		this.content = content;
		this.modifieddate = modifieddate;
		this.recommendcount = recommendcount;
		this.norecommendcount = norecommendcount;
		this.deleted = DELETED;
	}
	
	public void ChangeDate() {
		this.registereddate = ChangeDateString(registereddate);
		this.modifieddate = ChangeDateString(modifieddate);
	}
	
	private String ChangeDateString(String data){
		final Date date = new Date(System.currentTimeMillis());
		String result;

		try {			
			java.util.Date format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data);
			long diffSec = (date.getTime() - format.getTime()) / 1000;
			long diffDays = diffSec / (24*60*60); //일자수 차이
			long diffHours = diffSec / (60*60); //일자수 차이
			long diffMinutes = diffSec / (60); //일자수 차이
			
			if(diffHours < 24) {
					
				if(diffMinutes < 60) {
					
					if(diffSec < 60) {
						result = diffSec + "초 전";
						return result;
					}
					result = diffMinutes + "분 전";
					return result;
				}
				result = diffHours + "시간 전";
				return result;
			}
			else if(diffHours > 23 && diffDays < 8){
				result = diffDays + "일 전";
				return result;
			}
			else {
				return data.substring(0, 10);
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
