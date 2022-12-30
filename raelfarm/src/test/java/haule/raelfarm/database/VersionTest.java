package haule.raelfarm.database;

import org.junit.jupiter.api.Test;

public class VersionTest {
	@Test
	public void testVersion() throws Exception{

		String version = org.springframework.core.SpringVersion.getVersion();
		System.out.println("version : "+version);
		
	}
<<<<<<< HEAD
	
	@Test
	public void testParseInt() {
		System.out.println((int)(Integer.valueOf("000123")/100));
	}
=======
>>>>>>> 6e30bc7c347545c3b7b44a224ef2ac7745e8f12f
}
