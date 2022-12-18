package haule.raelfarm.database;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import haule.raelfarm.config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RootConfig.class})
public class DataSourceTest {
    
	@Autowired
	DataSource ds;
	
	@Test
	public void testConn() throws Exception{
		
		Connection con = ds.getConnection();
		con.close();
	}
}