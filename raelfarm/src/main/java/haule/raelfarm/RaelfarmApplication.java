package haule.raelfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;


@SpringBootApplication
public class RaelfarmApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(RaelfarmApplication.class, args);
	}

	@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 파라미터 인코딩 설정
        FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        filter.setInitParameter("encoding", "UTF-8");
        filter.addMappingForServletNames(null, false, "dispatcher");
    }

}
