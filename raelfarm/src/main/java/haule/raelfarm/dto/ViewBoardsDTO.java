package haule.raelfarm.dto;



import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ViewBoardsDTO {
	String categoryname;
	String title;
	String existimgfile;
	String writer;
	// "year-month-day hour:minutes:second"
	String registerdate;
	int viewcount;
	int recommendcount;
	
	public void SetRegisterDate() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat elseFormat = new SimpleDateFormat("MM-dd");
		
        
		try {
			java.util.Date format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(registerdate);
			long diffSec = (date.getTime() - format2.getTime()) / 1000;
			long diffDays = diffSec / (24*60*60); //일자수 차이
			
			if(diffDays < 1) {
				registerdate = newFormat.format(format2);
			}
			else {
				registerdate = elseFormat.format(format2);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
