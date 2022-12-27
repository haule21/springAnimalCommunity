package haule.raelfarm.database;

import org.junit.jupiter.api.Test;

public class VersionTest {
	@Test
	public void testVersion() throws Exception{

		String version = org.springframework.core.SpringVersion.getVersion();
		System.out.println("version : "+version);
		
	}
}
