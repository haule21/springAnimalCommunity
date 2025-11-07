package haule.raelfarm.config;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

// HikariConfig, HikariDataSource는 더 이상 필요하지 않습니다.
// Spring Boot가 application.yml에서 자동으로 DataSource를 생성합니다.

@Configuration
@ComponentScan(basePackages={"haule.raelfarm"})
@MapperScan(basePackages={"haule.raelfarm.mapper"})
public class RootConfig {
	
	@Autowired
    private ApplicationContext applicationContext;

	
	// DataSource는 Spring Boot가 application.yml과 application-local.properties에서 자동으로 생성합니다.
	// 하드코딩된 DB 정보를 제거하고 Spring Boot의 자동 설정을 사용합니다.
	
	@Autowired
	private DataSource dataSource; // Spring Boot가 자동으로 생성한 DataSource 주입
	
	@Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml");
        sessionFactoryBean.setMapperLocations(res);
        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
        return sessionFactoryBean.getObject();
    }
	
	
}