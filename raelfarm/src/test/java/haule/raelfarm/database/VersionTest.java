package haule.raelfarm.database;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class VersionTest {
//	@Test
//	public void testVersion() throws Exception{
//
//		String version = org.springframework.core.SpringVersion.getVersion();
//		System.out.println("version : "+version);
//		
//	}
//	
//	@Test
//	public void testParseInt() {
//		System.out.println((int)(Integer.valueOf("000123")/100));
//	}
	
	@Test
	public void SetRegisterDate() {
		Date date = new Date(System.currentTimeMillis());
		String register_date = "2023-01-02 13:15:02";
		SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm");
		
        
		try {
			java.util.Date format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(register_date);
			long diffSec = (date.getTime() - format2.getTime()) / 1000;
			long diffDays = diffSec / (24*60*60); //일자수 차이
			
			if(diffDays < 1) {
				register_date = newFormat.format(format2);
				System.out.println(register_date);
			}
			else {
				
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
