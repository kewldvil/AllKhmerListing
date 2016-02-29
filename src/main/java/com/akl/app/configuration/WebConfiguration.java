package com.akl.app.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
/*
 * indicates that the class can be used by the Spring IoC container as a source
 * of bean definitions.
 */
@EnableWebMvc /*
				 * activates annotation-driven controller request mapping and
				 * replaces <mvc:annotation-driven>.
				 */
@ComponentScan(basePackages = "com.memo.app")
public class WebConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	Environment environment;

	@Autowired
	private DataSource dataSource;
	/*
	 * @Bean annotation tells Spring that a method annotated with @Bean will
	 * return an object that should be registered as a bean in the Spring
	 * application context.
	 */

	// @Bean
	// public DataSource getDataSource(){
	// DriverManagerDataSource dataSource = new DriverManagerDataSource();
	// dataSource.setDriverClassName("org.postgresql.Driver");
	// dataSource.setUrl("jdbc:postgresql://ec2-54-83-53-120.compute-1.amazonaws.com:5432/d7tak2uasi7ro3?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
	// dataSource.setUsername("mceyhqrmfkstin");
	// dataSource.setPassword("sSaiW1jcEC76w8VsMDLwA3PPNN");
	// return dataSource;
	// }
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/memodb");
		dataSource.setUsername("postgres");
		dataSource.setPassword("12345");
		return dataSource;
	}

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public ViewResolver viewRsolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*");
	}

	// Upload File
	@Bean
	public MultipartResolver multipartResolver() {
		org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(2097152);
		return multipartResolver;
	}

	@Bean
	public String urlMemo() {
		return environment.getProperty("MEMO.URL");
	}

}
