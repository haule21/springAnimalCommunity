package haule.raelfarm.database;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

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
	
//	@Test
//	public void SetRegisterDate() {
//		Date date = new Date(System.currentTimeMillis());
//		String register_date = "2023-01-02 13:15:02";
//		SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm");
//		
//        
//		try {
//			java.util.Date format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(register_date);
//			long diffSec = (date.getTime() - format2.getTime()) / 1000;
//			long diffDays = diffSec / (24*60*60); //일자수 차이
//			
//			if(diffDays < 1) {
//				register_date = newFormat.format(format2);
//				System.out.println(register_date);
//			}
//			else {
//				
//			}
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void checking_string() {
		List<String> a = new ArrayList<>();
		a.add("abc");
		a.add("bcd");
		
		List<String> b = new ArrayList<>();
		b.add("abc");
		b.add("cde");
		
		Queue<String> mediadata = new LinkedList<>(a);
		Queue<String> modifieddata = new LinkedList<>(b);
		

			
	}
	
	@Test
	public void List_Test() {
		List<String> a = new ArrayList<>();
		
		a.add("aaa");
		a.add("bbb");
		a.add("ccc");
		
		for(String b : a) {
			b = "ggg";
		}
		

	}
	
	
	@Test
	public void stringtest() {
		String a = "/a/b/c";
		String[] b = a.split("/");
		
		for(String c : b) {
			if(c.equals("")) {
				System.out.println("c : ");
			}
			else if(c == null) {
				System.out.println("c is null");
			}
		}
				
	}

}
