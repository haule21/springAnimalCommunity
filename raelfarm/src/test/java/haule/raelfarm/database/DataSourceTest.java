package haule.raelfarm.database;

import java.sql.Connection;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("DataSource 통합 테스트")
class DataSourceTest {
    
	@Autowired
	DataSource ds;
	
	@Test
	@DisplayName("데이터베이스 연결 테스트")
	void testConn() throws Exception{
		Connection con = ds.getConnection();
		assertNotNull(con);
		assertFalse(con.isClosed());
		con.close();
		assertTrue(con.isClosed());
	}
}